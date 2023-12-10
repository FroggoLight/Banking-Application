package banking;

public class CreateProcessor {

	private Bank bank;

	public CreateProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] commandFragments) {
		String accountType = commandFragments[1];
		String accountId = commandFragments[2];
		double aprValue = Double.parseDouble(commandFragments[3]);
		switch (accountType) {
		case ("savings"):
			processCreateSavings(aprValue, accountId);
			break;
		case ("checking"):
			processCreateChecking(aprValue, accountId);
			break;
		case ("cd"):
			double initialBalance = Double.parseDouble(commandFragments[4]);
			processCreateCD(aprValue, accountId, initialBalance);
			break;
		default:
			break;
		}
	}

	public void processCreateSavings(double aprValue, String accountId) {
		Account savings = new Savings(aprValue, accountId);
		bank.addAccount(accountId, savings);
	}

	public void processCreateChecking(double aprValue, String accountId) {
		Account checking = new Checking(aprValue, accountId);
		bank.addAccount(accountId, checking);
	}

	public void processCreateCD(double aprValue, String accountId, double initialBalance) {
		Account cd = new CertificateOfDeposit(aprValue, accountId, initialBalance);
		bank.addAccount(accountId, cd);
	}

	// we can now break down this to make it less complex because we hate cognitive
	// complexity (I can not even swear here)
}
