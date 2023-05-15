package it.cwbi.cassettofiscale.client;

import com.squareup.okhttp.*;
import it.cwbi.cassettofiscale.client.utils.CassettoFiscaleUtilities;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okio.Buffer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;

@Builder
@Getter
@Slf4j
public class AuthenticationDetailsProviderInterceptor implements Interceptor {

    private static final String POST_METHOD_NAME = "POST";
    private final CassettoFiscaleConfiguration cassettoFiscaleConfiguration;

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest  = chain.request();
        String method       = oldRequest.method();

        if (!POST_METHOD_NAME.equals(method)) {
            return chain.proceed(oldRequest);
        }

        RequestBody body            = oldRequest.body();
        String requestBody;
        String signedRequestBody    = null;

        try (Buffer buffer = new Buffer()) {
            body.writeTo(buffer);
            requestBody = IOUtils.toString(buffer.inputStream());
        }

        String thumbprint = null;

        try {
            signedRequestBody = CassettoFiscaleUtilities.computeSignature(cassettoFiscaleConfiguration.getPrivateKey(), requestBody);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException exception) {
            log.error("Could not sign request body", exception);
        }

        Request newRequest = null;
        try {

            thumbprint = CassettoFiscaleUtilities.getThumbprint(cassettoFiscaleConfiguration.getCertificate());

            newRequest = oldRequest.newBuilder()
                    .addHeader("X-Thumbprint", thumbprint)
                    .addHeader("X-Signature-Type", "SHA256withRSA")
                    .addHeader("X-Institute", cassettoFiscaleConfiguration.getInstitute())
                    .addHeader("X-Signature", signedRequestBody)
                    .post(RequestBody.create(MediaType.parse("application/json"), requestBody))
                .build();
        } catch (CertificateEncodingException exception) {
            log.error("Fail", exception);
        }

        log.trace(String.format("%nrequestBody: %s%nsignedRequestBody: %s%nthumbprint: %s", requestBody, signedRequestBody, thumbprint));

        return chain.proceed(newRequest);
    }


}