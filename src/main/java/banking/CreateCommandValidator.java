package banking;

public class CreateCommandValidator extends CommandValidator {

	private Bank bank;

	public CreateCommandValidator(Bank bank) {
		this.bank = bank;
	}

	@Override
	public boolean validate(String command) {
		String[] commandFragment = lower(command).split(" ");
		boolean validArgumentNumber = checkValidArgumentNumbers(commandFragment);
		if (!validArgumentNumber) {
			return false;
		} else {
			boolean validStartingBalance = checkValidStartingBalance(commandFragment);
			boolean validAccountType = checkValidAccountType(commandFragment[1]);
			boolean validApr = checkValidAprValue(commandFragment[3]);
			boolean validIdNumber = checkValidIdentificationNumber(commandFragment[2]);
			boolean accountExistsById = bank.accountExistsByQuickId(commandFragment[2]);

			return ((validAccountType) && (validApr) && (validIdNumber) && (!accountExistsById)
					&& (validStartingBalance));
		}
	}

	public boolean checkValidStartingBalance(String[] commandFragment) {
		try {
			switch (commandFragment[1]) {
			case ("cd"):
				try {
					double cdStartingBalance = Double.parseDouble(commandFragment[4]);
					if ((cdStartingBalance >= 1000) && (cdStartingBalance <= 10000)) {
						return true;
					} else {
						return false;
					}
				} catch (NumberFormatException e) {
					return false;
				}
			case ("checking"):
			case ("savings"):
				return (commandFragment.length == 4);
			default:
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public boolean checkValidArgumentNumbers(String[] commandFragment) {
		try {
			switch (commandFragment[1]) {
			case ("savings"):
			case ("checking"):
				return (commandFragment.length == 4);
			case ("cd"):
				return (commandFragment.length == 5);
			}
			return false;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

}
