package it.cwbi.cassettofiscale.client.folder.create;

import it.cwbi.cassettofiscale.client.om.UtenzaLavoroAdE;
import lombok.Builder;

import java.util.Date;

@Builder
public class CreateFolderRequest {

    private final String institute;

    private final UtenzaLavoroAdE utenzaLavoroAdE;

    private final String utenteDescrizione;
    private final String utenteEmail;
    private final String utenteLanguage;

    private final String companyType;
    private final String folderID;

    private final String finalita;
    private final String descrizione;
    private final String notificationURL;
    private final Date expirationDateTime;
}
