package it.cwbi.cassettofiscale.client.om;

public class CredenzialiAccesso {

	private static final long serialVersionUID = 5813526708640235389L;

	
	private String type; // FISCONLINE, SPID/ARUBA, CIE

	private String login;

	private String password1;

	private String password2;
	
	public CredenzialiAccesso() {
		super();
	}

	public CredenzialiAccesso(
			String type,
			String login, 
			String password1,
			String password2			
		) {
		super();
		this.type 		= type;
		this.login 		= login;
		this.password1 	= password1;
		this.password2 	= password2;
	}


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
			
}