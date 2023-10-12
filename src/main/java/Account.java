public abstract class Account {
	// private static int currentIDValue = 0; < assuming the code should not allow
	// user to specify ID
	private String identificationNumber;
	private double apr;
	private double balance;

	public Account(double APR, String identificationNumber, double balance) {
		this.apr = APR;
		// currentIDValue += 1;
		// this.identificationNumber = String.format("%08d", currentIDValue);
		this.identificationNumber = identificationNumber;
		this.balance = balance;
	}

	public double getAPR() {
		return apr;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public double getBalance() {
		return Math.round(balance * 100.0) / 100.0;
	}

	public void modifyBalance(double amount, String operation) {

		if (amount < 0) {
			balance += 0;
		} else {
			if (operation == "withdraw") {
				amount *= -1;
			}

			balance += amount;
			if (balance < 0) {
				balance = 0;
			}
		}

	}

}
