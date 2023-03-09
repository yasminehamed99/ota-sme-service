package com.example.otasmeservice.service;


import com.example.otasmeservice.model.dto.InvoiceDTO;
import com.example.otasmeservice.model.dto.InvoiceItemDTO;
import com.example.otasmeservice.model.dto.ProvinceDTO;
import com.example.otasmeservice.model.enums.AdditionalBuyerIdType;
import com.example.otasmeservice.model.enums.GeneralTaxType;
import com.example.otasmeservice.model.enums.NoteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import static com.example.otasmeservice.model.enums.InvoiceTypeEnum.*;

@Service
public class XmlGenerationService {
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private Template freemarkerTemplate;

    public String generateXML(InvoiceDTO invoiceDTO) {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("invoiceDTO", invoiceDTO);
        model.put("invoiceIssueDate", dateFormatter.format(invoiceDTO.getIssueDate()));

        if(invoiceDTO.getNoteType() == null) {
            model.put("invoiceTypeValue", "388");
        } else if (NoteType.CREDIT_INVOICE.equals(invoiceDTO.getNoteType())) {
            model.put("invoiceTypeValue", "381");
        } else if (NoteType.DEBIT_INVOICE.equals(invoiceDTO.getNoteType())) {
            model.put("invoiceTypeValue", "383");
        }

        model.put("invoiceCounter", Integer.valueOf(invoiceDTO.getInvoiceNumber().replace("EIN", "")));

        if(invoiceDTO.getBuyerDTO() != null) {
            if (invoiceDTO.getBuyerDTO().getAdditionalBuyerIdType() != null) {
                model.put("additionalBuyerIdType", invoiceDTO.getBuyerDTO().getAdditionalBuyerIdType().getValue());
            }

            if (AdditionalBuyerIdType.TAXPAYER_NUMBERS.equals(invoiceDTO.getBuyerDTO().getAdditionalBuyerIdType())) {
                model.put("buyerTaxNumber", invoiceDTO.getBuyerDTO().getAdditionalBuyerId());
            }

            ProvinceDTO buyerProvinceDTO = invoiceDTO.getBuyerDTO().getProvinceDTO();

            if (buyerProvinceDTO != null && buyerProvinceDTO.getProvinceCode() != null) {
                model.put("buyerProvinceCode", buyerProvinceDTO.getProvinceCode());
            }
        }

        if(invoiceDTO.getActivityDTO() != null) {
            model.put("activity", invoiceDTO.getActivityDTO().getActivity());
        }

        if(CASH_SPECIAL_TAX.equals(invoiceDTO.getInvoiceTypeCode())
                || RECEIVABLE_SPECIAL_TAX.equals(invoiceDTO.getInvoiceTypeCode())) {
            model.put("isSpecialTax", "true");
        } else {
            model.put("isSpecialTax", "false");
        }

        if(CASH_INCOME.equals(invoiceDTO.getInvoiceTypeCode())
                || CASH_GENERAL_TAX.equals(invoiceDTO.getInvoiceTypeCode())
                || CASH_SPECIAL_TAX.equals(invoiceDTO.getInvoiceTypeCode())) {
            model.put("paymentMethodCode", "10");
        } else {
            model.put("paymentMethodCode", "96");
        }
        Map<GeneralTaxType, InvoiceItemDTO> taxCategoriesMap = new HashMap<>();
        for (InvoiceItemDTO invoiceItemDTO : invoiceDTO.getInvoiceItemDTOList()) {
            if(invoiceItemDTO.getGeneralTaxType() != null) {
                if (taxCategoriesMap.containsKey(invoiceItemDTO.getGeneralTaxType())) {
                    InvoiceItemDTO invoiceVATCategoryDetails = taxCategoriesMap.get(invoiceItemDTO.getGeneralTaxType());
                    invoiceVATCategoryDetails.setTotalAmountAfterDiscount(invoiceVATCategoryDetails.getTotalAmountAfterDiscount().add(invoiceItemDTO.getTotalAmountAfterDiscount()));
                    invoiceVATCategoryDetails.setGeneralTaxAmount(invoiceVATCategoryDetails.getGeneralTaxAmount().add(invoiceItemDTO.getGeneralTaxAmount()));
                } else {
                    InvoiceItemDTO invoiceVATCategoryDetails = new InvoiceItemDTO();
                    invoiceVATCategoryDetails.setTotalAmountAfterDiscount(invoiceItemDTO.getTotalAmountAfterDiscount());
                    invoiceVATCategoryDetails.setGeneralTaxAmount(invoiceItemDTO.getGeneralTaxAmount());
                    taxCategoriesMap.put(invoiceItemDTO.getGeneralTaxType(), invoiceVATCategoryDetails);
                }
            }
        }
        model.put("taxCategoriesMap", taxCategoriesMap);
        try {
            freemarkerTemplate.process(model, stringWriter);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stringWriter.getBuffer().toString();

    }
}
