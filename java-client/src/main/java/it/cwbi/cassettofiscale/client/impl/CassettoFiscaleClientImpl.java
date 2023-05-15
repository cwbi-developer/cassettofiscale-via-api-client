package it.cwbi.cassettofiscale.client.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.cwbi.cassettofiscale.client.CassettoFiscaleClient;
import it.cwbi.cassettofiscale.client.CassettoFiscaleConfiguration;
import it.cwbi.cassettofiscale.client.exception.GenericException;
import it.cwbi.cassettofiscale.client.folder.create.CreateFolderRequest;
import it.cwbi.cassettofiscale.client.folder.create.CreateFolderResponse;
import okhttp3.*;

import java.io.IOException;

public class CassettoFiscaleClientImpl implements CassettoFiscaleClient {

    private final ObjectMapper objectMapper;
    private final CassettoFiscaleConfiguration cassettoFiscaleConfiguration;

    public CassettoFiscaleClientImpl(CassettoFiscaleConfiguration cassettoFiscaleConfiguration) {
        objectMapper = new ObjectMapper();

        // permette all'objectmapper di leggere anche campi private
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        this.cassettoFiscaleConfiguration = cassettoFiscaleConfiguration;
    }

    @Override
    public CreateFolderResponse createFolder(CreateFolderRequest request) throws GenericException {

        try {
            // TODO pre checks

            String requestJSON = objectMapper.writeValueAsString(request);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .build();

            Request httpRequest = new Request.Builder()

                    .post(RequestBody.create(requestJSON, MediaType.parse("application/json")))
                    .url(cassettoFiscaleConfiguration.getApiBaseUrl())

                    .header("X-Signature-Type", "")
                    .header("X-Signature", "")
                    .header("X-Thumbprint", "")
                    .header("X-Institute", "")

                .build();

            Response httpResponse = httpClient.newCall(httpRequest).execute();
            String responseJSON = httpResponse.body().string();

            httpResponse.close();
            CreateFolderResponse createFolderResponse = objectMapper.readValue(responseJSON, CreateFolderResponse.class);

            return createFolderResponse;
        } catch (IOException exception) {
            throw new GenericException(exception);
        }
    }
}
