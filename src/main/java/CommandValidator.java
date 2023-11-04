public class CommandValidator {

	private Bank bank;
	private CreateCommandValidator createCommandValidator;

	public CommandValidator(Bank bank) {
		this.bank = bank;
		this.createCommandValidator = new CreateCommandValidator(this.bank);
	}

	public CommandValidator() {
	}

	public boolean validate(String command) {
		String[] commandString = lower(command).split(" ");
		String accountAction = commandString[0];
		switch (accountAction) {
		case "create":
			return createCommandValidator.validate(command);
		case "deposit":
			return true;
		default:
			return false;
		}
		// if len of cmdString < 4 return false
		// if len of cmdString > 6 return false, 5 if account type is not cd
		// if cmd has random upper and lower case, as long as it follows it returns true
		// if first argument of cmd is non-existent, return false
		// else call child validators for respective
		// return true;
	}

	public boolean checkValidIdentificationNumber(String identificationNumber) {
		if (identificationNumber.matches("[0-9]+")) {
			if (identificationNumber.length() == 8) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean checkValidAprValue(String aprValue) {
		try {
			double apr = Double.parseDouble(aprValue);
			if ((apr > 0) && (apr < 10)) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public String lower(String command) {
		return command.toLowerCase();
	}

	public boolean checkValidAccountType(String accountType) {
		switch (accountType) {
		case ("savings"):
		case ("cd"):
		case ("checking"):
			return true;
		default:
			return false;
		}
	}

}
