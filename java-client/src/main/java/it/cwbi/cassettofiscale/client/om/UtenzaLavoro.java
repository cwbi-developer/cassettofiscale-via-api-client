package it.cwbi.cassettofiscale.client.om;

public class UtenzaLavoro {

	private static final long serialVersionUID = 8710128320079150226L;

	private String codiceFiscale;
	private String partitaIva;
	
	
	public UtenzaLavoro() {
		super();
	}

	public UtenzaLavoro(
			String codiceFiscale,
			String partitaIva 						
		) {
		super();
		this.codiceFiscale 		= codiceFiscale;
		this.partitaIva 		= partitaIva;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
			
}