package com.liumapp.cert.core;

import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.liumapp.cert.core.constant.SignConstant;
import com.liumapp.cert.core.pattern.PdfSignByAliasPattern;
import com.liumapp.cert.core.property.Properties;
import com.liumapp.sign.core.constant.SignConstant;
import com.liumapp.sign.core.pattern.PdfSignByAliasPattern;
import com.liumapp.sign.core.pattern.PdfSignBySerialNumPattern;
import com.liumapp.sign.core.pdf.Sign;
import com.liumapp.sign.core.pdf.SignatureInfo;
import com.liumapp.sign.core.property.Properties;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 * @author liumapp
 * @file SignTest.java
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 4/10/18
 */
public class SignTest extends TestCase {

    @Ignore
    @Test
    public void testSign () {
        PdfSignByAliasPattern pdfSignByAliasPattern = initPdfSignByAliasPattern();

        try {
            String tmpPdfOut = pdfSignByAliasPattern.getTmpPdf() + "final";

            KeyStore ks = KeyStore.getInstance("jks");
            ks.load(new FileInputStream(properties.getString(SignConstant.save_KeyStorePath)
                    + pdfSignByAliasPattern.getKeystore()) ,
                    properties.getString(SignConstant.keyStore_password).toCharArray());

            PrivateKey pk = (PrivateKey) ks.getKey(pdfSignByAliasPattern.getAlias() , pdfSignByAliasPattern.getCertPd().toCharArray());
            Certificate[] chain = ks.getCertificateChain(pdfSignByAliasPattern.getAlias());

            SignatureInfo signatureInfo = new SignatureInfo();
            signatureInfo.setReason("this is reason");
            signatureInfo.setLocation("this is location");
            signatureInfo.setPk(pk);
            signatureInfo.setChain(chain);
            signatureInfo.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
            signatureInfo.setDigestAlgorithm(DigestAlgorithms.SHA256);
            signatureInfo.setFieldName(pdfSignByAliasPattern.getSignatureField());
            signatureInfo.setImagePath(properties.getString(SignConstant.save_PicPath) + pdfSignByAliasPattern.getTmpImg());
            signatureInfo.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);

            Sign.sign(properties.getString(SignConstant.save_PdfPath) + pdfSignByAliasPattern.getTmpPdf() ,
                    properties.getString(SignConstant.save_PdfPath) + tmpPdfOut , signatureInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private PdfSignBySerialNumPattern initPdfSignBySerialNumPattern () {
        PdfSignBySerialNumPattern pdfSignBySerialNumPattern = new PdfSignBySerialNumPattern();
        pdfSignBySerialNumPattern.setSerialNum("2c165439cd0bb006baa1ed28715af2e3f1a7eb25");
        pdfSignBySerialNumPattern.setKeystore("demo.ks");
        pdfSignBySerialNumPattern.setCertPd("password");
        pdfSignBySerialNumPattern.setSignatureField("certA");
        pdfSignBySerialNumPattern.setTmpImg("pic.jpg");
        pdfSignBySerialNumPattern.setTmpPdf("with_sign_area_test.pdf");
        return pdfSignBySerialNumPattern;
    }

    private PdfSignByAliasPattern initPdfSignByAliasPattern () {
        PdfSignByAliasPattern pdfSignByAliasPattern = new PdfSignByAliasPattern();
//        pdfSignByAliasPattern.setAlias("4bbdc076d4493b52209b17643fb85b00baa58522");
        pdfSignByAliasPattern.setAlias("first-certificate");
        pdfSignByAliasPattern.setKeystore("demo.ks");
        pdfSignByAliasPattern.setKeystorePd("123456");
        pdfSignByAliasPattern.setCertPd("123123");
        pdfSignByAliasPattern.setSignatureField("certA");
        pdfSignByAliasPattern.setTmpImg("pic.jpg");
        pdfSignByAliasPattern.setTmpPdf("with_sign_area_test.pdf");
        return pdfSignByAliasPattern;
    }

}
