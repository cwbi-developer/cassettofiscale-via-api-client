package it.cwbi;

import it.cwbi.cassettofiscale.client.CassettoFiscaleClient;
import it.cwbi.cassettofiscale.client.CassettoFiscaleConfiguration;
import it.cwbi.cassettofiscale.client.exception.GenericException;
import it.cwbi.cassettofiscale.client.folder.create.CreateFolderRequest;
import it.cwbi.cassettofiscale.client.folder.create.CreateFolderResponse;
import it.cwbi.cassettofiscale.client.impl.CassettoFiscaleClientImpl;
import it.cwbi.cassettofiscale.client.om.UtenzaLavoroAdE;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws GenericException, NoSuchAlgorithmException {

        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();

        CassettoFiscaleConfiguration cassettoFiscaleConfiguration = CassettoFiscaleConfiguration.builder()
                .keyPair(keyPair)
                .apiBaseUrl("https://pd2test.codiceweb.com/cassettofiscale-via-api")
            .build();

        CassettoFiscaleClient client    = new CassettoFiscaleClientImpl(cassettoFiscaleConfiguration);
        CreateFolderResponse response   = client.createFolder(
                CreateFolderRequest.builder()
                        .utenteEmail("utenteEmail")
                        .utenteDescrizione("utenteDescrizione")
                        .utenteLanguage("utenteLanguage")

                        .utenzaLavoroAdE(new UtenzaLavoroAdE())

                        .companyType("companyType")
                        .folderID("folderID")

                        .finalita("finalita")
                        .descrizione("descrizione")
                        .notificationURL("notificationURL")
                        .expirationDateTime(new Date())
                    .build());



    }
}
