package banking;

import java.util.Objects;

public class TransferCommandValidator extends CommandValidator {

	private Bank bank;
	private DepositCommandValidator depositCommandValidator;
	private WithdrawCommandValidator withdrawCommandValidator;

	public TransferCommandValidator(Bank bank, DepositCommandValidator depositCommandValidator,
			WithdrawCommandValidator withdrawCommandValidator) {
		this.bank = bank;
		this.depositCommandValidator = depositCommandValidator;
		this.withdrawCommandValidator = withdrawCommandValidator;
	}

	@Override
	public boolean validate(String command) {
		String[] commandFragment = lower(command).split(" ");
		boolean validArgumentNumber = checkValidArgumentNumbers(commandFragment);
		if (!validArgumentNumber) {
			return false;
		} else {
			if ((!canConvertTransactionAmountToDouble(commandFragment[3]))
					|| (Objects.equals(commandFragment[1], commandFragment[2]))) {
				return false;
			}
			if (Objects.equals(bank.getAccountType(commandFragment[1]), "cd")
					|| Objects.equals(bank.getAccountType(commandFragment[2]), "cd")) {
				return false;
			}
			String pseudoWithdrawCommand = "withdraw " + commandFragment[1] + " " + commandFragment[3];
			String pseudoDepositCommand = "deposit " + commandFragment[2] + " " + commandFragment[3];
			boolean fromAccountCheck = withdrawCommandValidator.validate(pseudoWithdrawCommand);
			boolean toAccountCheck = depositCommandValidator.validate(pseudoDepositCommand);
			return (fromAccountCheck && toAccountCheck);

		}
	}

	public boolean checkValidArgumentNumbers(String[] commandString) {
		if (commandString.length == 4) {
			return true;
		} else {
			return false;
		}
	}

}
