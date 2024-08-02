package it.cwbi;


import io.swagger.client.ApiException;
import io.swagger.client.api.V202301FolderRequestApiApi;
import it.cwbi.cassettofiscale.client.AuthenticationDetailsProviderInterceptor;
import it.cwbi.cassettofiscale.client.CassettoFiscaleConfiguration;
import it.cwbi.cassettofiscale.client.utils.CassettoFiscaleUtilities;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.temporal.ChronoUnit;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.LinkedList;

/**
 * Example class which calls the REST endpoint. Note that all the parameters must be passed as command line arguments
 * As the certificate and private key are required for authentication, if they are not valid the api will return an error.
 */
public class Main {

    public static void main(String[] args) throws GeneralSecurityException, IOException {

        String clientCertificatePath = null;
        String clientPrivateKeyPath = null;
        String basePath = null;
        String institute = null;

        if (args.length != 4) {
            throw new IllegalStateException("the arguments required are 4, with a key=value formatting.\nThey are:" +
                    "\n\t+ certificatePath=<path of client certificate in the classpath (which contains the public key)>" +
                    "\n\t+ privateKeyPath=<path of client private key in the classpath>" +
                    "\n\t+ basePath=<base path of REST API>" +
                    "\n\t+ institute=<institute id>");
        }

        for (String entry : args) {

            String[] tokens = entry.split("=");
            String key = tokens[0];
            String value = tokens[1];

            if ("certificatePath".equals(key)) {
                clientCertificatePath = value;
            } else if ("privateKeyPath".equals(key)) {
                clientPrivateKeyPath = value;
            } else if ("basePath".equals(key)) {
                basePath = value;
            } else if ("institute".equals(key)) {
                institute = value;
            } else {
                throw new IllegalArgumentException(String.format("Invalid argument: %s", key));
            }
        }

        if (clientCertificatePath == null) {
            throw new IllegalArgumentException("certificatePath not set");
        } else if (clientPrivateKeyPath == null) {
            throw new IllegalArgumentException("privateKeyPath not set");
        } else if (basePath == null) {
            throw new IllegalArgumentException("basePath not set");
        } else if (institute == null) {
            throw new IllegalArgumentException("institute not set");
        }

        try (InputStream clientCertificate   = CassettoFiscaleUtilities.class.getResourceAsStream(clientCertificatePath);
             InputStream clientPrivateKey    = CassettoFiscaleUtilities.class.getResourceAsStream(clientPrivateKeyPath)) {

            V202301FolderRequestApiApi v202301FolderRequestApiApi = new V202301FolderRequestApiApi();
            v202301FolderRequestApiApi.getApiClient().setBasePath(basePath);
            v202301FolderRequestApiApi.getApiClient().getHttpClient().networkInterceptors().add(AuthenticationDetailsProviderInterceptor
                    .builder()
                    .cassettoFiscaleConfiguration(CassettoFiscaleConfiguration.builder()
                            .certificate(CassettoFiscaleUtilities.loadX509Certificate(clientCertificate))
                            .privateKey(CassettoFiscaleUtilities.loadPrivateKey(clientPrivateKey))
                            .institute(institute)
                        .build())
                .build());

            io.swagger.client.model.FolderRequestCreateRequest folderRequestCreateRequest = new io.swagger.client.model.FolderRequestCreateRequest();

            folderRequestCreateRequest.setCompanyType("PF");
            folderRequestCreateRequest.setDescrizione("descrizione");
            folderRequestCreateRequest.setExpirationDateTime(OffsetDateTime.now().plus(Duration.of(30, ChronoUnit.DAYS)));
            folderRequestCreateRequest.setFinalita("finalita");
            folderRequestCreateRequest.setFolderID("pratica 296");
            folderRequestCreateRequest.setInstitute("2272");
            folderRequestCreateRequest.setItems(new LinkedList<>());

            folderRequestCreateRequest.getItems().add(new io.swagger.client.model.FolderRequestCreateRequestItem()
                    .fromDate(LocalDate.of(2020, 1, 1))
                    .toDate(LocalDate.now())
                    .type("FATTURA_EMESSA_ITA")
                    .includeBase64Content("1"));

            folderRequestCreateRequest.setUtenteEmail("esempio@gmail.com");
            folderRequestCreateRequest.setUtenteLanguage("it");

            folderRequestCreateRequest.setUtenzaLavoro(new io.swagger.client.model.UtenzaLavoroAdE());
            folderRequestCreateRequest.getUtenzaLavoro().setCodiceFiscale("03505120984");
            folderRequestCreateRequest.getUtenzaLavoro().setPartitaIva("03505120984");

            try {
                io.swagger.client.model.FolderRequestCreateResponse folderRequestCreateResponse = v202301FolderRequestApiApi.folderRequestCreate1(
                        new io.swagger.client.model.RequestWrapperOfFolderRequestCreateRequest().input(folderRequestCreateRequest)).getOutput().getBody();
                System.out.println(folderRequestCreateResponse);
            } catch (ApiException e) {
                int code = e.getCode();
                if (code == 403) {
                    throw new SecurityException("Invalid public/private keys");
                }
                e.printStackTrace();
            }

        }
    }
}
