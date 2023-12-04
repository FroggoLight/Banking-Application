package banking;

public class WithdrawCommandValidator extends CommandValidator {

	private Bank bank;

	public WithdrawCommandValidator(Bank bank) {
		this.bank = bank;
	}

	@Override
	public boolean validate(String command) {
		String[] commandFragment = lower(command).split(" ");
		boolean validArgumentNumber = checkValidArgumentNumbers(commandFragment);
		if (!validArgumentNumber) {
			return false;
		} else {
			if (!canConvertTransactionAmountToDouble(commandFragment[2])) {
				return false;
			}
			double amount = Double.parseDouble(commandFragment[2]);
			boolean validWithdrawAmount = checkValidWithdrawAmount(commandFragment[1], amount);
			boolean validMonthConstraint = checkValidMonthsConstraint(commandFragment[1]);
			if (validWithdrawAmount && validMonthConstraint) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean checkValidArgumentNumbers(String[] commandString) {
		if (commandString.length == 3) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkValidWithdrawAmount(String accountID, double withdrawAmount) {
		String test = bank.getAccountType(accountID);
		switch (test) {
		case "savings":
			return checkValidTransactionBoundary(withdrawAmount, 1000);
		case "checking":
			return checkValidTransactionBoundary(withdrawAmount, 400);
		case "cd":
			return (withdrawAmount >= bank.retrieveAccount(accountID).getBalance());
		default:
			return false;
		}
	}

	public boolean checkValidMonthsConstraint(String accountID) {
		switch (bank.getAccountType(accountID)) {
		case "cd":
			return (bank.retrieveAccount(accountID).getMonthsPassed() >= 12);
		case "savings":
			return (bank.retrieveAccount(accountID).getWithdrawStatus());
		case "checking":
			return true;
		default:
			return false;
		}
	}

}
