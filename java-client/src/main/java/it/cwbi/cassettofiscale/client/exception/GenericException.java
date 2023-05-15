package it.cwbi.cassettofiscale.client.exception;

public class GenericException extends Exception {

    public GenericException(Exception exception) {
        super(exception);
    }
}
