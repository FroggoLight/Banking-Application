package banking;

public class ModifyBalanceProcessor {
	private Bank bank;

	public ModifyBalanceProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] commandFragments, String action) {
		String accountId = commandFragments[1];
		double amount = Double.parseDouble(commandFragments[2]);
		bank.modifyAccountBalance(accountId, amount, action);
	}
}
