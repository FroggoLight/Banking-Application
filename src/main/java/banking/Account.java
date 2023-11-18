package banking;

public abstract class Account {

	private String identificationNumber;
	private double apr;
	private double balance;
	private String accountType;

	public Account(double APR, String identificationNumber, double balance, String accountType) {
		this.apr = APR;
		this.identificationNumber = identificationNumber;
		this.balance = balance;
		this.accountType = accountType;
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

	public String getAccountType() {
		return accountType;
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
