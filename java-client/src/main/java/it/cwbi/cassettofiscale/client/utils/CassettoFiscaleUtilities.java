package it.cwbi.cassettofiscale.client.utils;

import it.codiceweb.common.util.CWConstants;
import it.codiceweb.common.util.CWSignatureUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class CassettoFiscaleUtilities {

    private CassettoFiscaleUtilities() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    public static PrivateKey loadPrivateKey(InputStream inputStream) throws IOException, GeneralSecurityException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(IOUtils.toByteArray(inputStream));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    public static X509Certificate loadX509Certificate(InputStream inputStream) throws CertificateException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
        return (X509Certificate) certificateFactory.generateCertificate(inputStream);
    }

    public static String getThumbprint(X509Certificate cert) throws CertificateEncodingException {
        return CWSignatureUtils.getThumbprint(cert.getEncoded(), CWConstants.ENCODING.BASE64, CWConstants.HASH.SHA256);
    }

    public static String computeSignature(PrivateKey privateKey, String serializedRequest) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, UnsupportedEncodingException {
        byte[] signedBytes = signWithPrivateKey(privateKey, serializedRequest.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signedBytes);
    }

    public static byte[] signWithPrivateKey(PrivateKey privateKey, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature     = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }


}
