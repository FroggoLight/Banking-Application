package banking;

public class TransferBalanceProcessor {

	private Bank bank;

	public TransferBalanceProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] commandFragments) {
		double amount = Double.parseDouble(commandFragments[3]);
		String fromAccountId = commandFragments[1];
		String toAccountId = commandFragments[2];
		double rollover = bank.modifyAccountBalance(fromAccountId, amount, "withdraw");
		amount = amount - rollover;
		bank.modifyAccountBalance(toAccountId, amount, "deposit");
	}
}
