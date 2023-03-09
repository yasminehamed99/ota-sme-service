package com.example.otasmeservice.service;

import com.example.otasmeservice.exception.InvoiceAlreadyExists;
import com.example.otasmeservice.model.data.Buyer;
import com.example.otasmeservice.model.data.IRNSequence;
import com.example.otasmeservice.model.data.Invoice;
import com.example.otasmeservice.model.dto.*;
import com.example.otasmeservice.model.enums.InvoiceStatus;
import com.example.otasmeservice.model.enums.InvoiceStatusEnum;
import com.example.otasmeservice.model.enums.InvoiceTypeEnum;
import com.example.otasmeservice.model.enums.NoteType;
import com.example.otasmeservice.repository.BuyerRepository;
import com.example.otasmeservice.repository.IRNSequenceRepo;
import com.example.otasmeservice.repository.InvoiceRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class SMEService {
    @Autowired
    private InvoiceIdGenerator invoiceIdGenerator;
    @Autowired
    private XmlGenerationService xmlGenerationService;
    @Autowired
    private QrGeneratorService qrGeneratorService;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private IRNSequenceRepo irnSequenceRepo;
    public SMEResult submitInvoice(InvoiceDTO invoiceDto) throws XPathExpressionException, ParserConfigurationException, NoSuchAlgorithmException, IOException, TransformerException, SAXException {


        String invoiceNumber=invoiceIdGenerator.incrementIRN();
        System.out.println(invoiceNumber);
        invoiceDto.setInvoiceNumber(invoiceNumber);

        String invoiceUUID = invoiceIdGenerator.generateInvoiceUUID();
        invoiceDto.setInvoiceUniqueIdentifier(invoiceUUID);
        String invoiceXML = xmlGenerationService.generateXML(invoiceDto);
        String invoiceQR=qrGeneratorService.generateQr(invoiceXML);
        System.out.println(invoiceQR);
        invoiceDto.setXml(invoiceXML);
        invoiceDto.setQrCode(invoiceQR);
        return submitInvoice(invoiceDto, invoiceDto.getQrCode(), invoiceDto.getXml());



    }
    public SMEResult submitInvoice(InvoiceDTO invoiceDTO, String qrCode, String submittedInvoice) {
        SMEResult smeResult=new SMEResult();

        BigDecimal totalAmountExcludingTaxes = new BigDecimal(0);
        BigDecimal totalDiscountsAmount = new BigDecimal(0);
        BigDecimal totalGeneralTaxesAmount = new BigDecimal(0);
        BigDecimal totalSpecialTaxesAmount = new BigDecimal(0);
        BigDecimal totalPayableAmount = new BigDecimal(0);

        for (InvoiceItemDTO invoiceItemDTO : invoiceDTO.getInvoiceItemDTOList()) {
            totalAmountExcludingTaxes = totalAmountExcludingTaxes.add(invoiceItemDTO.getSubtotalAmount());
            totalDiscountsAmount = totalDiscountsAmount.add(invoiceItemDTO.getDiscountAmount());
            totalGeneralTaxesAmount = totalGeneralTaxesAmount.add(invoiceItemDTO.getGeneralTaxAmount());
            if (invoiceItemDTO.getSpecialTaxAmount() != null) {
                totalSpecialTaxesAmount = totalSpecialTaxesAmount.add(invoiceItemDTO.getSpecialTaxAmount());
            }
            totalPayableAmount = totalPayableAmount.add(invoiceItemDTO.getTotalAmountAfterTaxes());
        }
        invoiceDTO.setTotalAmountExcludingTaxes(totalAmountExcludingTaxes.setScale(3, RoundingMode.HALF_UP));
        invoiceDTO.setTotalDiscountsAmount(totalDiscountsAmount.setScale(3, RoundingMode.HALF_UP));
        invoiceDTO.setTotalGeneralTaxesAmount(totalGeneralTaxesAmount.setScale(3, RoundingMode.HALF_UP));
        invoiceDTO.setTotalSpecialTaxesAmount(totalSpecialTaxesAmount.setScale(3, RoundingMode.HALF_UP));
        invoiceDTO.setTotalPayableAmount(totalPayableAmount.setScale(3, RoundingMode.HALF_UP));

        Invoice originalInvoice = null;

        Invoice invoice = new Invoice();
        invoice.setEditDone(false);
        invoice.setTotalRefundAmount(BigDecimal.ZERO);
        invoice.setInvoiceStatus(InvoiceStatusEnum.ACTIVE);
        System.out.println(invoiceDTO.getInvoiceNumber());
        invoice.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        invoice.setInvoiceTypeCode(invoiceDTO.getInvoiceTypeCode());
        invoice.setBuyerInvoiceNumber(invoiceDTO.getBuyerInvoiceNumber());
        invoice.setQrCode(qrCode);
        invoice.setInvoiceUniqueIdentifier(invoiceDTO.getInvoiceUniqueIdentifier());
        invoice.setNoteType(invoiceDTO.getNoteType());
        invoice.setReasonOfNote(invoiceDTO.getReasonOfNote());
        invoice.setOriginalInvoice(originalInvoice);
        invoice.setIssueDate(Date.valueOf(invoiceDTO.getIssueDate()));
        invoice.setNotes(invoiceDTO.getNotes());
        invoice.setXmlFile(submittedInvoice.getBytes(StandardCharsets.UTF_8));
        invoice.setTotalExcludingTaxes(invoiceDTO.getTotalAmountExcludingTaxes());
        invoice.setTotalDiscountsAmount(invoiceDTO.getTotalDiscountsAmount());
        invoice.setTotalGeneralTaxesAmount(invoiceDTO.getTotalGeneralTaxesAmount());
        invoice.setTotalSpecialTaxesAmount(invoiceDTO.getTotalSpecialTaxesAmount());
        invoice.setTotalPayableAmount(invoiceDTO.getTotalPayableAmount());

        if (invoiceDTO.getBuyerDTO() != null &&
                (!StringUtils.isAllBlank(invoiceDTO.getBuyerDTO().getBuyerName(), invoiceDTO.getBuyerDTO().getPostalCode(),
                        invoiceDTO.getBuyerDTO().getPhoneNumber(), invoiceDTO.getBuyerDTO().getAdditionalBuyerId())
                        || (invoiceDTO.getBuyerDTO().getProvinceDTO() != null && invoiceDTO.getBuyerDTO().getProvinceDTO().getProvinceCode() != null)
                        || (invoiceDTO.getBuyerDTO().getAdditionalBuyerIdType() != null))) {
            BuyerDTO buyerDTO = invoiceDTO.getBuyerDTO();
            Buyer buyer = new Buyer();
            buyer.setBuyerName(buyerDTO.getBuyerName());
            buyer.setAdditionalBuyerId(buyerDTO.getAdditionalBuyerId());
            buyer.setAdditionalBuyerIdType(buyerDTO.getAdditionalBuyerIdType());
            buyer.setPhoneNumber("0101201133");
            buyer.setPostalCode("14466");

            buyerRepository.save(buyer);
            invoice.setBuyer(buyer);
        }

        Invoice invoice1=invoiceRepository.findByUUIDAndNumber(invoice.getInvoiceUniqueIdentifier(),invoice.getInvoiceNumber());
        if(invoice1!=null)
            throw new InvoiceAlreadyExists("invoice Already Exists");
        invoiceRepository.save(invoice);

        smeResult.setSubmittedInvoice(Base64.getEncoder().encodeToString(invoiceDTO.getXml().getBytes()));
        smeResult.setQrCode(invoiceDTO.getQrCode());
        smeResult.setInvoiceUUID(invoice.getInvoiceUniqueIdentifier());
        smeResult.setInvoiceStatus(InvoiceStatus.SUBMITTED);
        smeResult.setInvoiceNumber(invoiceDTO.getInvoiceNumber());



        return smeResult;
    }
    public InvoiceTotalDTO getAllInvoices(){
        List<Invoice> invoiceList=invoiceRepository.findAll();
        InvoiceTotalDTO invoiceTotalDTO=new InvoiceTotalDTO();

        for(Invoice invoice:invoiceList){
            InvoiceListItemDTO invoiceListItemDTO=new InvoiceListItemDTO();
            invoiceListItemDTO.setInvoiceUniqueIdentifier(invoice.getInvoiceUniqueIdentifier());
            invoiceListItemDTO.setInvoiceNumber(invoice.getInvoiceNumber());
            invoiceListItemDTO.setInvoiceTypeCode(invoice.getInvoiceTypeCode());
            invoiceListItemDTO.setUserName(invoice.getBuyer().getBuyerName());
            invoiceListItemDTO.setNoteType(invoice.getNoteType());
            invoiceListItemDTO.setIssueDate(invoice.getIssueDate().toLocalDate());
            invoiceListItemDTO.setTotalPayableAmount(invoice.getTotalPayableAmount());
            invoiceListItemDTO.setInvoiceStatusEnum(InvoiceStatusEnum.ACTIVE);
            invoiceTotalDTO.getInvoiceList().add(invoiceListItemDTO);

        }

       return invoiceTotalDTO;
    }

}
