package it.cwbi.cassettofiscale.client;

import it.cwbi.cassettofiscale.client.exception.GenericException;
import it.cwbi.cassettofiscale.client.folder.create.CreateFolderRequest;
import it.cwbi.cassettofiscale.client.folder.create.CreateFolderResponse;

public interface CassettoFiscaleClient {

    CreateFolderResponse createFolder(CreateFolderRequest request) throws GenericException;
}
