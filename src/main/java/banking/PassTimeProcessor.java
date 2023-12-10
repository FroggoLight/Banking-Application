package banking;

import java.util.Collection;
import java.util.Objects;

public class PassTimeProcessor {

	private Bank bank;

	public PassTimeProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] commandFragments) {
		int monthsToPass = Integer.parseInt(commandFragments[1]);
		Collection<Account> allAccount = bank.getOpenedAccounts().values();
		for (int i = 0; i < monthsToPass; i++) {
			updateAllAccountBalance(allAccount);
		}
		bank.clearEmptyAccounts();
	}

	public void updateAllAccountBalance(Collection<Account> allAccount) {
		for (Account account : allAccount) {
			account.incrementPassedMonths(1);
			if (account.getBalance() < 100) {
				account.applyMinimumBalancePenalty();
			}
			if (Objects.equals(account.getAccountType(), "cd")) {
				for (int j = 0; j < 4; j++) {
					account.applyAPRToBalance();
				}
			} else {
				account.applyAPRToBalance();
			}
		}
	}
}
