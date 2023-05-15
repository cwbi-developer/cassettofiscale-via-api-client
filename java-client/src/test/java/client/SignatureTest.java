package client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.cwbi.cassettofiscale.client.utils.CassettoFiscaleUtilities;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

class SignatureTest {

    @Test
    void testPrivateKeySignature() throws IOException, GeneralSecurityException {

        PrivateKey privateKey = CassettoFiscaleUtilities.loadPrivateKey(SignatureTest.class.getResourceAsStream("/key.pkcs8"));
        String request = "{\"input\":{\"institute\":\"2272\",\"amount\":10}}";

        String signed = CassettoFiscaleUtilities.computeSignature(privateKey, request);

        Assertions.assertEquals("TYsbXMXbbHm/QiIvIFtLlnVXP5ZXHjQLQcySJaGwd4/IEoeteAsFiEbAqVg9fAoR4PRBLIwLyTPztyznua3YLoI/O4mJTNe0SzV8meZ5qkaYBUZlfKD7PChTQbkWRp54BiL+Fq0tIJgCgecVyvE1icWVN+hRfn/bQU126e4vmD8PWI5jVHUo/mshdelZ3o+Xrsnur8/oJQhcGAMobIsJvG9qvbyVPiBUQz4mtFh1BcdcuWChtMvSJoN7icvlZK+KJJuDrJelzomTfYrBnrsU2iMrirlC4s+MHBYC3dBAg/2OkcKKF2Tfk7ZpxIWR+cO3p/VOgyuIjk/tlFzFIr+w+w==", signed);
    }

    @Test
    void testCertificateThumbprint() throws CertificateException, NoSuchAlgorithmException {

        X509Certificate certificate = CassettoFiscaleUtilities.loadX509Certificate(SignatureTest.class.getResourceAsStream("/certificate.cer"));

        Assertions.assertEquals("0KW2hOSSEmoTweUYhFnijYmGLj9gx7uC3B/f9WFjOto=", CassettoFiscaleUtilities.getThumbprint(certificate));
    }

}
