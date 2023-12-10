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
		bank.modifyAccountBalance(fromAccountId, amount, "withdraw");
		bank.modifyAccountBalance(toAccountId, amount, "deposit");
	}
}
