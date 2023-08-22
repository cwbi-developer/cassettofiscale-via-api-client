package it.cwbi.cassettofiscale.client;


import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class CassettoFiscaleConfiguration {

    private final X509Certificate certificate;
    private final PrivateKey privateKey;
    private final String institute;

    private CassettoFiscaleConfiguration(X509Certificate certificate, PrivateKey privateKey, String institute) {
        this.certificate = certificate;
        this.privateKey = privateKey;
        this.institute = institute;
    }
    
    public static class Builder {

        private X509Certificate certificate;
        private PrivateKey privateKey;
        private String institute;

        public Builder certificate(X509Certificate certificate) {
            this.certificate = certificate;
            return this;
        }

        public Builder privateKey(PrivateKey privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public Builder institute(String institute) {
            this.institute = institute;
            return this;
        }

        public CassettoFiscaleConfiguration build() {
            return new CassettoFiscaleConfiguration(certificate, privateKey, institute);
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public String getInstitute() {
        return institute;
    }
}
