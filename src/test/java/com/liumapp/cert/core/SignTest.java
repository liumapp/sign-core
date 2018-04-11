package com.liumapp.cert.core;

import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.liumapp.cert.core.entity.Sign;
import com.liumapp.cert.core.entity.SignatureInfo;
import com.liumapp.cert.core.pattern.PdfSignByAliasPattern;
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
            String tmpPdfOut = "final_" + pdfSignByAliasPattern.getTmpPdf() ;

            KeyStore ks = KeyStore.getInstance("jks");
            ks.load(new FileInputStream(pdfSignByAliasPattern.getKeystore()) , pdfSignByAliasPattern.getKeystorePd().toCharArray());

            PrivateKey pk = (PrivateKey) ks.getKey(pdfSignByAliasPattern.getAlias() , pdfSignByAliasPattern.getCertPd().toCharArray());
            Certificate[] chain = ks.getCertificateChain(pdfSignByAliasPattern.getAlias());

            SignatureInfo signatureInfo = new SignatureInfo();
            signatureInfo.setReason("this is reason");
            signatureInfo.setLocation("this is location");
            signatureInfo.setPk(pk);
            signatureInfo.setChain(chain);
            signatureInfo.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
            signatureInfo.setDigestAlgorithm(DigestAlgorithms.SHA1);
            signatureInfo.setFieldName(pdfSignByAliasPattern.getSignatureField());
            signatureInfo.setImagePath(pdfSignByAliasPattern.getTmpImg());
            signatureInfo.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);

            Sign.sign(pdfSignByAliasPattern.getTmpPdf() ,
                    tmpPdfOut , signatureInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private PdfSignByAliasPattern initPdfSignByAliasPattern () {
        PdfSignByAliasPattern pdfSignByAliasPattern = new PdfSignByAliasPattern();
//        pdfSignByAliasPattern.setAlias("4bbdc076d4493b52209b17643fb85b00baa58522");
        pdfSignByAliasPattern.setAlias("first-certificate");
        pdfSignByAliasPattern.setKeystore("/usr/local/tomcat/project/sign-core/ks/demo.ks");
        pdfSignByAliasPattern.setKeystorePd("123456");
        pdfSignByAliasPattern.setCertPd("123123");
        pdfSignByAliasPattern.setSignatureField("certA");
        pdfSignByAliasPattern.setTmpImg("/usr/local/tomcat/project/sign-core/pic/pic.jpg");
        pdfSignByAliasPattern.setTmpPdf("/usr/local/tomcat/project/sign-core/pdf/with_sign_area_test.pdf");
        return pdfSignByAliasPattern;
    }

}
