public class CommandProcessor {
	private Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		String[] commandFragments = command.toLowerCase().split(" ");
		String commandAction = commandFragments[0];
		switch (commandAction) {
		case ("create"):
			processCreate(commandFragments);
		}
	}

	public void processCreate(String[] commandFragments) {
		String accountType = commandFragments[1];
		String accountId = commandFragments[2];
		double aprValue = Double.parseDouble(commandFragments[3]);
		switch (accountType) {
		case ("savings"):
			Account savings = new Savings(aprValue, accountId);
			bank.addAccount(savings.getIdentificationNumber(), savings);
			break;
		case ("checking"):
			Account checking = new Checking(aprValue, accountId);
			bank.addAccount(checking.getIdentificationNumber(), checking);
			break;
		case ("cd"):
			double initialBalance = Double.parseDouble(commandFragments[4]);
			Account cd = new CertificateOfDeposit(aprValue, accountId, initialBalance);
			bank.addAccount(cd.getIdentificationNumber(), cd);
			break;
		default:
			System.out.println("something went wrong.");
		}
	}

}
