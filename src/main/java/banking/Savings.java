package banking;

public class Savings extends Account {

	public Savings(double apr, String identificationNumber) {
		super(apr, identificationNumber, 0, "savings");
	}

}
