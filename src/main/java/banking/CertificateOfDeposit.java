package banking;

public class CertificateOfDeposit extends Account {

	public CertificateOfDeposit(double apr, String identificationNumber, double balance) {
		super(apr, identificationNumber, balance, "cd");
	}

}
