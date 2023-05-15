package it.cwbi.cassettofiscale.client;

import lombok.Builder;
import lombok.Getter;

import java.security.KeyPair;

@Builder
@Getter
public class CassettoFiscaleConfiguration {

    private String apiBaseUrl;
    private KeyPair keyPair;

}
