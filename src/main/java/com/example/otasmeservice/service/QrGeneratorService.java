package com.example.otasmeservice.service;

import com.payneteasy.tlv.BerTag;
import com.payneteasy.tlv.BerTlvBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.List;


@Slf4j
@Service

public class QrGeneratorService implements ProcessUtil {
    private static final String SIGNALGORITHMS = "SHA256withECDSA";
    private static MessageDigest digest = null;

    @Value("${qr.signing.private.key}")
    private String privateKeyEncodedBase64;

    @Value("${qr.generation.service.id}")
    private String qrGenerationServiceId;



    private static final String SELLER_NAME_XPATH = "/Invoice/AccountingSupplierParty/Party/PartyLegalEntity/RegistrationName";
    private static final String TAX_NUMBER_XPATH = "/Invoice/AccountingSupplierParty/Party/PartyTaxScheme/CompanyID";
    private static final String ISSUE_DATE_XPATH = "/Invoice/IssueDate";
    private static final String INVOICE_TOTAL_XPATH = "/Invoice/LegalMonetaryTotal/TaxInclusiveAmount";
    private static final String TAX_TOTAL_XPATH = "/Invoice/TaxTotal/TaxAmount";

    private static final String INVOICE_NUMBER_XPATH = "/Invoice/ID";


    public String generateQr(String InvoiceXml) throws XPathExpressionException, ParserConfigurationException, NoSuchAlgorithmException, IOException, TransformerException, SAXException {

            String modifiedString = InvoiceXml.replaceAll("<\\?xml(.+?)\\?>", "");
            String newQr = generateQRstatically(modifiedString);
           return newQr;
        }

        public String generateSignatureValue (PrivateKey privateKey, String hash) throws IOException {
            return Base64.getEncoder().encodeToString(signECDSA(null, hash));
        }
        public byte[] signECDSA (PrivateKey privateKey, String data){

            try {
                Provider p = new org.bouncycastle.jce.provider.BouncyCastleProvider();
                Security.addProvider(p);
                Signature signature = Signature.getInstance(SIGNALGORITHMS, p);
                signature.initSign(privateKey);
                signature.update(data.getBytes());
                return signature.sign();
            } catch (Exception e) {
                log.error("Error : {}", e.getMessage());
            }
            return new byte[0];
    }

    public String generateQRstatically(String invoiceXml) throws ParserConfigurationException, TransformerException, NoSuchAlgorithmException, IOException, SAXException, XPathExpressionException {
//        String encodedValue = new String(Base64.getEncoder().encode(invoiceXml.getBytes()));
        XPathFactory xPathfactory=null;
        XPath xpath=null;
        Document doc=null;

        try {
            String invoice = invoiceXml;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
             xPathfactory = XPathFactory.newInstance();
             xpath = xPathfactory.newXPath();
            InputSource is = new InputSource(new StringReader(invoice));
            DocumentBuilder db = dbf.newDocumentBuilder();
             doc = db.parse(is);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }


        String invoiceNumber = getNodeContentXpth(xpath, doc, INVOICE_NUMBER_XPATH);
        String issueDate = getNodeContentXpth(xpath, doc, ISSUE_DATE_XPATH);
        String taxTotal = getNodeContentXpth(xpath, doc, TAX_TOTAL_XPATH);

        StringBuilder qrFirstTag = new StringBuilder();
        qrFirstTag.append(qrGenerationServiceId);
//        qrFirstTag.append("?invoice-number=");
//        qrFirstTag.append(invoiceNumber);
//        qrFirstTag.append("&seller-name=");
//        qrFirstTag.append(sellerName);
//        qrFirstTag.append("&tax-number=");
//        qrFirstTag.append(taxNumber);
//        qrFirstTag.append("&invoice-issue-date=");
//        qrFirstTag.append(issueDate);
//        qrFirstTag.append("&invoice-total=");
//        qrFirstTag.append(invoiceTotal);
//        qrFirstTag.append("&tax-total=");
//        qrFirstTag.append(taxTotal);


        String valueString = toValueString(invoiceNumber,issueDate, taxTotal,false);
        System.out.println(valueString);
        if(digest == null) {
            digest = MessageDigest.getInstance("SHA-256");
        }
        byte[] hash = digest.digest(valueString.getBytes(StandardCharsets.UTF_8));
        String hashAsString = Base64.getEncoder().encodeToString(hash);
        System.out.println(hashAsString);

        BerTlvBuilder berTlvBuilder = new BerTlvBuilder();
        String serviceAndParameters = qrFirstTag.toString();
        berTlvBuilder.addText(new BerTag(0x01), serviceAndParameters, StandardCharsets.UTF_8);
        berTlvBuilder.addText(new BerTag(0x02), "{}", StandardCharsets.UTF_8);
        berTlvBuilder.addText(new BerTag(0x03), "false", StandardCharsets.UTF_8);

        berTlvBuilder.addText(new BerTag(0x05), invoiceNumber, StandardCharsets.UTF_8);
        berTlvBuilder.addText(new BerTag(0x06), taxTotal, StandardCharsets.UTF_8);
        berTlvBuilder.addText(new BerTag(0x07), issueDate, StandardCharsets.UTF_8);


        berTlvBuilder.addText(new BerTag(0x0A),  generateSignatureValue(null, hashAsString));
        byte[] tlv = berTlvBuilder.buildArray();
        return Base64.getEncoder().encodeToString(tlv);
    }
    public String toValueString(String invoiceNumber,  String issueDate, String taxTotal, Boolean includeKeysInQR)
            throws ParserConfigurationException, TransformerException {

        StringBuilder values = new StringBuilder();

        values = values.append((Boolean.TRUE.equals(includeKeysInQR)  ? invoiceNumber + "=" : "") + invoiceNumber);
        values = values.append((Boolean.TRUE.equals(includeKeysInQR)  ? taxTotal + "=" : "") + taxTotal);
        values = values.append((Boolean.TRUE.equals(includeKeysInQR) ? issueDate + "=" : "") + issueDate);



        System.out.println(values);

        return values.toString();

    }



}
