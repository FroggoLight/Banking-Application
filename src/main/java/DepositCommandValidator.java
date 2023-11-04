public class DepositCommandValidator extends CommandValidator {

	private Bank bank;

	public DepositCommandValidator(Bank bank) {
		this.bank = bank;
	}

	@Override
	public boolean validate(String command) {
		String[] commandString = lower(command).split(" ");
		boolean validArgumentNumber = checkValidArgumentNumbers(commandString);
		if (!validArgumentNumber) {
			return false;
		} else {
			if (!canConvertTransactionAmountToDouble(commandString[2])) {
				return false;
			}
			double amount = Double.parseDouble(commandString[2]);
			String accountType = bank.getAccountType(commandString[1]);
			boolean validDepositAmount = checkValidDepositAmount(accountType, amount);
			if (validDepositAmount) {
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

	public boolean checkValidDepositAmount(String accountType, double depositAmount) {
		switch (accountType) {
		case ("savings"):
			return checkValidTransactionBoundary(depositAmount, 2500);
		case ("checking"):
			return checkValidTransactionBoundary(depositAmount, 1000);
		default:
			return false;
		}
	}

}
