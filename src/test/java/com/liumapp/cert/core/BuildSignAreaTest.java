package com.liumapp.cert.core;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.liumapp.sign.core.constant.SignConstant;
import com.liumapp.sign.core.pattern.SignatureAreaPattern;
import com.liumapp.sign.core.property.Properties;
import com.liumapp.sign.core.utils.FileUtil;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.security.Signature;

/**
 * @author liumapp
 * @file BuildSignAreaTest.java
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 4/10/18
 */
public class BuildSignAreaTest extends TestCase {

    protected Properties properties;

    protected FileUtil fileUtil;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        properties = new Properties("config/sign.properties");

        fileUtil = new FileUtil();
    }

    @Ignore
    @Test
    public void testMakeArea () {
        SignatureAreaPattern signatureAreaPattern = initSignatureAreaPattern();
        try {
            String fileResultName = "with_sign_area_" + signatureAreaPattern.getTmpFile();

            PdfReader pdfReader = new PdfReader(properties.getString(SignConstant.save_PdfPath) + signatureAreaPattern.getTmpFile());
            FileOutputStream out = new FileOutputStream(properties.getString(SignConstant.save_PdfPath) + fileResultName);
            PdfStamper pdfStamper = new PdfStamper(pdfReader , out);
            pdfStamper.addSignature(signatureAreaPattern.getName() , signatureAreaPattern.getPageNumber() , signatureAreaPattern.getFirstX().floatValue() , signatureAreaPattern.getFirstY().floatValue() , signatureAreaPattern.getSecondX().floatValue() , signatureAreaPattern.getSecondY().floatValue());
            pdfStamper.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SignatureAreaPattern initSignatureAreaPattern () {
        SignatureAreaPattern signatureAreaPattern = new SignatureAreaPattern();
        signatureAreaPattern.setName("certA");
        signatureAreaPattern.setFirstX(new BigDecimal(40));
        signatureAreaPattern.setFirstY(new BigDecimal(40));
        signatureAreaPattern.setSecondX(new BigDecimal(80));
        signatureAreaPattern.setSecondY(new BigDecimal(80));
        signatureAreaPattern.setFileKey("testfile001");
        signatureAreaPattern.setPageNumber(1);
        signatureAreaPattern.setTmpFile("test.pdf");
        return signatureAreaPattern;
    }
}
