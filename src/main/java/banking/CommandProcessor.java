package banking;

import java.util.Collection;
import java.util.Objects;

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
			break;
		case ("deposit"):
			processDeposit(commandFragments);
			break;
		case ("withdraw"):
			processWithdraw(commandFragments);
			break;
		case ("transfer"):
			processTransfer(commandFragments);
			break;
		case ("pass"):
			processPassTime(commandFragments);
			break;
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
			System.out.println("something went wrong. Unable to verify Account Type.");
		}
	}

	public void processDeposit(String[] commandFragments) {
		String accountId = commandFragments[1];
		double amount = Double.parseDouble(commandFragments[2]);
		bank.modifyAccountBalance(accountId, amount, "deposit");
	}

	public void processWithdraw(String[] commandFragments) {
		String accountId = commandFragments[1];
		double amount = Double.parseDouble(commandFragments[2]);
		bank.modifyAccountBalance(accountId, amount, "withdraw");
	}

	public void processTransfer(String[] commandFragments) {
		double amount = Double.parseDouble(commandFragments[3]);
		String fromAccountId = commandFragments[1];
		String toAccountId = commandFragments[2];
		bank.modifyAccountBalance(fromAccountId, amount, "withdraw");
		bank.modifyAccountBalance(toAccountId, amount, "deposit");
	}

	public void processPassTime(String[] commandFragments) {
		int monthsToPass = Integer.parseInt(commandFragments[1]);
		Collection<Account> allAccount = bank.getOpenedAccounts().values();
		for (int i = 0; i < monthsToPass; i++) {
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
		bank.clearEmptyAccounts();
	}

}
