package banking;

public class PassTimeCommandValidator extends CommandValidator {

	@Override
	public boolean validate(String command) {
		String[] commandFragment = lower(command).split(" ");
		boolean validArgumentNumber = checkValidArgumentNumbers(commandFragment);
		if (!validArgumentNumber) {
			return false;
		} else {
			if (!isNumericalIntegerOnly(commandFragment[1])) {
				return false;
			}
			int months = Integer.parseInt(commandFragment[1]);
			if (months >= 1 && months <= 60) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean checkValidArgumentNumbers(String[] commandFragment) {
		if (commandFragment.length == 2) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isNumericalIntegerOnly(String months) {
		if (months.matches("[0-9]+")) {
			return true;
		} else {
			return false;
		}
	}

}
