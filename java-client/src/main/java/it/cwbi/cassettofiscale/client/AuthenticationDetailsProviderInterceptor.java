package it.cwbi.cassettofiscale.client;

import com.squareup.okhttp.*;
import it.cwbi.cassettofiscale.client.utils.CassettoFiscaleUtilities;
import okio.Buffer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;

public class AuthenticationDetailsProviderInterceptor implements Interceptor {

    private final CassettoFiscaleConfiguration cassettoFiscaleConfiguration;

    public AuthenticationDetailsProviderInterceptor(CassettoFiscaleConfiguration cassettoFiscaleConfiguration) {
        this.cassettoFiscaleConfiguration = cassettoFiscaleConfiguration;
    }

    public static class Builder {
        private CassettoFiscaleConfiguration cassettoFiscaleConfiguration;

        public Builder cassettoFiscaleConfiguration(CassettoFiscaleConfiguration cassettoFiscaleConfiguration) {
            this.cassettoFiscaleConfiguration = cassettoFiscaleConfiguration;
            return this;
        }

        public AuthenticationDetailsProviderInterceptor build() {
            return new AuthenticationDetailsProviderInterceptor(cassettoFiscaleConfiguration);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest          = chain.request();
        RequestBody body            = oldRequest.body();
        String requestBody;
        String signedRequestBody;

        try (Buffer buffer = new Buffer()) {
            body.writeTo(buffer);
            requestBody = IOUtils.toString(buffer.inputStream());
        }

        String thumbprint;

        try {
            signedRequestBody = CassettoFiscaleUtilities.computeSignature(cassettoFiscaleConfiguration.getPrivateKey(), requestBody);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException exception) {
            throw new AuthenticationDetailsProviderException("Could not sign request body", exception);
        }

        Request newRequest;

        try {
            thumbprint = CassettoFiscaleUtilities.getThumbprint(cassettoFiscaleConfiguration.getCertificate());

            newRequest = oldRequest.newBuilder()
                    .addHeader("X-Thumbprint", thumbprint)
                    .addHeader("X-Signature-Type", "SHA256withRSA")
                    .addHeader("X-Institute", cassettoFiscaleConfiguration.getInstitute())
                    .addHeader("X-Signature", signedRequestBody)
                    .post(RequestBody.create(MediaType.parse("application/json"), requestBody))
                .build();
        } catch (CertificateEncodingException | NoSuchAlgorithmException exception) {
            throw new AuthenticationDetailsProviderException(exception);
        }

        return chain.proceed(newRequest);
    }

    private static class AuthenticationDetailsProviderException extends RuntimeException {

        public AuthenticationDetailsProviderException(String message, Exception parentException) {
            super(message, parentException);
        }

        public AuthenticationDetailsProviderException(Exception parentException) {
            super(parentException);
        }

    }

}