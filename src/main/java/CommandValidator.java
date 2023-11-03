public class CommandValidator {

	private Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] commandString = lower(command).split(" ");
		String accountAction = commandString[0];
		boolean validIdentificationNumber;
		switch (accountAction) {
		case "create":
			validIdentificationNumber = checkValidIdentificationNumber(commandString[2]);
			if (!validIdentificationNumber) {
				return false;
			}
			if (bank.accountExistsByQuickId(commandString[2])) {
				return false;
			} else {
				return true;
			}
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

	public String lower(String command) {
		return command.toLowerCase();
	}

}
