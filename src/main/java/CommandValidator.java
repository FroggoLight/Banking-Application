public class CommandValidator {

	private Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] commandString = lower(command).split(" ");
		String accountAction = commandString[0];
		switch (accountAction) {
		case "create":
			if (bank.accountExistsByQuickId(commandString[2])) {
				return false;
			} else {
				return true;
			}
		case "deposit":

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

	public String lower(String command) {
		return command.toLowerCase();
	}

}
