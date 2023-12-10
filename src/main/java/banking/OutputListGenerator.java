package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OutputListGenerator {
	private Bank bank;
	private List<String> outputList;

	public OutputListGenerator(Bank bank) {
		this.bank = bank;
		outputList = new ArrayList<>();
	}

	public List<String> generateOutput(List<String> validCommands, List<String> invalidCommands) {
		for (int i = 0; i < validCommands.size(); i++) {
			String[] commandFragments = validCommands.get(i).toLowerCase().split(" ");
			if ((Objects.equals(commandFragments[0], "create")) && (bank.accountExistsByQuickId(commandFragments[2]))) {
				generateAccountDetails(commandFragments[2], validCommands);
			}
		}
		outputList.addAll(invalidCommands);
		return outputList;
	}

	private void generateAccountDetails(String accountID, List<String> validCommands) {
		generateAccountState(accountID);
		generateAccountTransactionHistory(accountID, validCommands);
	}

	public void generateAccountTransactionHistory(String accountID, List<String> validCommands) {
		for (int j = 0; j < validCommands.size(); j++) {
			String[] commandFragments = validCommands.get(j).toLowerCase().split(" ");
			String actionCommand = commandFragments[0];
			switch (actionCommand) {
			case ("deposit"):
			case ("withdraw"):
				if (Objects.equals(commandFragments[1], accountID)) {
					outputList.add(validCommands.get(j));
				}
				break;
			case ("transfer"):
				if (Objects.equals(commandFragments[1], accountID) || Objects.equals(commandFragments[2], accountID)) {
					outputList.add(validCommands.get(j));
				}
				break;
			default:
				break;
			}
		}
	}

	public void generateAccountState(String accountID) {
		Account account = bank.retrieveAccount(accountID);
		String accountType = capitalizeString(account.getAccountType());
		String currentAccountBalance = String.format("%.2f", account.getBalance());
		String accountAPR = String.format("%.2f", account.getAPR());
		String fullAccountDetail = accountType + " " + accountID + " " + currentAccountBalance + " " + accountAPR;
		outputList.add(fullAccountDetail);
	}

	public String capitalizeString(String item) {
		return item.substring(0, 1).toUpperCase() + item.substring(1);
	}
}
