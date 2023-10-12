import java.util.ArrayList;

public class Bank {
	private ArrayList<Account> openedAccounts = new ArrayList<Account>();

	public void addAccount(Account account) {
		openedAccounts.add(account);
	}

	public ArrayList<Account> getOpenedAccounts() {
		return openedAccounts;
	}

	public Account retrieveAccount(String identificationNumber) {
		for (int i = 0; i < openedAccounts.size(); i++) {
			if (openedAccounts.get(i).getIdentificationNumber() == identificationNumber) {
				return openedAccounts.get(i);
			}
		}
		return null;
	}

	public void modifyAccountBalance(String identificationNumber, double amount, String operation) {
		Account account = retrieveAccount(identificationNumber);
		if (account == null) {
			System.out.println("account not found");
		} else {
			account.modifyBalance(amount, operation);
		}
	}

}