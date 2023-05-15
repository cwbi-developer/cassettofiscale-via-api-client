package it.cwbi.cassettofiscale.client;

import lombok.Builder;
import lombok.Getter;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

@Builder
@Getter
public class CassettoFiscaleConfiguration {

    private final X509Certificate certificate;
    private final PrivateKey privateKey;
    private final String institute;

}
