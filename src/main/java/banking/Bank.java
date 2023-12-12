package banking;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Bank {
	private HashMap<String, Account> openedAccounts;

	public Bank() {
		openedAccounts = new HashMap<>();
	}

	public void addAccount(String accountID, Account account) {
		openedAccounts.put(accountID, account);
	}

	public HashMap<String, Account> getOpenedAccounts() {
		return openedAccounts;
	}

	public Account retrieveAccount(String identificationNumber) {
		return openedAccounts.get(identificationNumber);
	}

	public String getAccountType(String identificationNumber) {
		if (accountExistsByQuickId(identificationNumber)) {
			return retrieveAccount(identificationNumber).getAccountType();
		} else {
			return "unidentifiable";
		}
	}

	public double modifyAccountBalance(String identificationNumber, double amount, String operation) {
		Account account = retrieveAccount(identificationNumber);
		return account.modifyBalance(amount, operation);
	}

	public boolean accountExistsByQuickId(String identificationNumber) {
		if (openedAccounts.get(identificationNumber) != null) {
			return true;
		} else {
			return false;
		}
	}

	public void clearEmptyAccounts() {
		Iterator<Map.Entry<String, Account>> iterator = openedAccounts.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Account> entry = iterator.next();
			if (entry.getValue().getTrueBalance() == 0) {
				iterator.remove();
			}
		}
	}
}