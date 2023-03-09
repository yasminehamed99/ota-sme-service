package com.example.otasmeservice.controller;

import com.example.otasmeservice.model.dto.*;
import com.example.otasmeservice.model.request.InvoiceItemDTO;
import com.example.otasmeservice.model.request.SubmitInvoiceDto;
import com.example.otasmeservice.service.SMEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/invoices")
public class SMEController {
@Autowired
private SMEService smeService;
    @PostMapping
    public SMEResult submitInvoice(@RequestBody SubmitInvoiceDto invoiceDto) throws XPathExpressionException, ParserConfigurationException, NoSuchAlgorithmException, IOException, TransformerException, SAXException {
        InvoiceDTO invoiceDTO=new InvoiceDTO();
        invoiceDTO.setInvoiceNumber(invoiceDto.getInvoiceNumber());
        invoiceDTO.setInvoiceTypeCode(invoiceDto.getInvoiceTypeTransaction());
        invoiceDTO.setIssueDate(invoiceDto.getInvoiceIssueDate());
        invoiceDTO.setNoteType(invoiceDto.getNoteType());
        invoiceDTO.setTotalAmountExcludingTaxes(invoiceDto.getTotalAmountExcludingVat());
        invoiceDTO.setTotalPayableAmount(invoiceDto.getTotalPayableAmount());
        invoiceDTO.setTotalDiscountsAmount(invoiceDto.getTotalDiscountsAmount());
        System.out.println(invoiceDto.getTotalVatAmount());
        invoiceDTO.setTotalGeneralTaxesAmount(invoiceDto.getTotalVatAmount());
        BuyerDTO buyerDTO=new BuyerDTO();
        buyerDTO.setBuyerName(invoiceDto.getBuyer().getBuyerEnglishName());
        buyerDTO.setPostalCode(invoiceDto.getBuyer().getBuyerPostalCode());
        buyerDTO.setAdditionalBuyerIdType(invoiceDto.getBuyer().getAdditionalBuyerIdType());
        buyerDTO.setAdditionalBuyerId(invoiceDto.getBuyer().getAdditionalBuyerId());
        ProvinceDTO provinceDTO=new ProvinceDTO();
        provinceDTO.setProvinceCode(invoiceDto.getBuyer().getBuyerCountryCode());
        provinceDTO.setProvinceNameEn(invoiceDto.getBuyer().getBuyerStateEn());
        provinceDTO.setProvinceNameAr(invoiceDto.getBuyer().getBuyerStreetAr());
        buyerDTO.setProvinceDTO(provinceDTO);
         List<com.example.otasmeservice.model.dto.InvoiceItemDTO> invoiceItemDTOList = new ArrayList<>();
        for(InvoiceItemDTO invoiceItemDTO:invoiceDto.getInvoiceItemDTOList()){
            com.example.otasmeservice.model.dto.InvoiceItemDTO invoiceItemDTO1=new com.example.otasmeservice.model.dto.InvoiceItemDTO();
            invoiceItemDTO1.setQuantity(invoiceItemDTO.getQuantity());
            invoiceItemDTO1.setInvoiceItemType(invoiceDto.getInvoiceTypeDescription());
            invoiceItemDTO1.setProductDescription(invoiceItemDTO.getDescriptionEn());
            invoiceItemDTO1.setDiscountAmount(invoiceItemDTO.getDiscountAmount());
            invoiceItemDTO1.setGeneralTaxAmount(invoiceItemDTO.getVatAmount());
            invoiceItemDTO1.setGeneralTaxType(invoiceItemDTO.getVatRateType());
            invoiceItemDTO1.setGeneralTaxPercentage(invoiceItemDTO.getDiscountPercentage());
            invoiceItemDTO1.setDiscountAmount(invoiceItemDTO.getDiscountAmount());
            invoiceItemDTO1.setSubtotalAmount(invoiceItemDTO.getSubtotalAmount());
            invoiceItemDTO1.setUnitPrice(invoiceItemDTO.getUnitPrice());
            invoiceItemDTO1.setTotalAmountAfterTaxes(invoiceItemDTO.getVatRate());
            invoiceItemDTO1.setTotalAmountAfterDiscount(invoiceItemDTO.getTotalAmount());
            invoiceItemDTOList.add(invoiceItemDTO1);
        }
        invoiceDTO.setBuyerDTO(buyerDTO);
        invoiceDTO.setInvoiceItemDTOList(invoiceItemDTOList);
        System.out.println(invoiceDTO);
        return smeService.submitInvoice(invoiceDTO);


    }
    @GetMapping
    public InvoiceTotalDTO getAllInvoices(){
        return smeService.getAllInvoices();
    }
}
