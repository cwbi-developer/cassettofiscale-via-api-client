package it.cwbi.cassettofiscale.client.om;

public class UtenzaLavoroAdE extends UtenzaLavoro {

	private static final long serialVersionUID = 9197511074493416280L;

	private String codiceFiscaleIncaricante;
	
	public UtenzaLavoroAdE() {
		super();
	}

	public UtenzaLavoroAdE(
			String codiceFiscaleIncaricante,
			
			String codiceFiscale,
			String partitaIva
			
		) {
		super(codiceFiscale, partitaIva);
				
		this.codiceFiscaleIncaricante = codiceFiscaleIncaricante;
	}

	public String getCodiceFiscaleIncaricante() {
		return codiceFiscaleIncaricante;
	}

	public void setCodiceFiscaleIncaricante(String codiceFiscaleIncaricante) {
		this.codiceFiscaleIncaricante = codiceFiscaleIncaricante;
	}
			
}