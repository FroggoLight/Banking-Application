public class CommandValidator {

	private Bank bank;
	private CreateCommandValidator createCommandValidator;
	private DepositCommandValidator depositCommandValidator;

	public CommandValidator(Bank bank) {
		this.bank = bank;
		this.createCommandValidator = new CreateCommandValidator(this.bank);
		this.depositCommandValidator = new DepositCommandValidator(this.bank);
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
			return depositCommandValidator.validate(command);
		default:
			return false;
		}
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

	public boolean checkValidTransactionBoundary(double amount, double upperBound) {
		if ((amount >= 0) && (amount <= upperBound)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean canConvertTransactionAmountToDouble(String amount) {
		try {
			double sampleValue = Double.parseDouble(amount);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
