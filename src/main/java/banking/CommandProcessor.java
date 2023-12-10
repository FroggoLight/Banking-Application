package banking;

public class CommandProcessor {
	private Bank bank;
	private CreateProcessor createProcessor;
	private ModifyBalanceProcessor modifyBalanceProcessor;
	private TransferBalanceProcessor transferBalanceProcessor;
	private PassTimeProcessor passTimeProcessor;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
		this.createProcessor = new CreateProcessor(this.bank);
		this.modifyBalanceProcessor = new ModifyBalanceProcessor(this.bank);
		this.transferBalanceProcessor = new TransferBalanceProcessor(this.bank);
		this.passTimeProcessor = new PassTimeProcessor(this.bank);
	}

	public void process(String command) {
		String[] commandFragments = command.toLowerCase().split(" ");
		String commandAction = commandFragments[0];
		switch (commandAction) {
		case ("create"):
			createProcessor.process(commandFragments);
			break;
		case ("deposit"):
		case ("withdraw"):
			modifyBalanceProcessor.process(commandFragments, commandAction);
			break;
		case ("transfer"):
			transferBalanceProcessor.process(commandFragments);
			break;
		case ("pass"):
			passTimeProcessor.process(commandFragments);
			break;
		default:
			break;
		}
	}

}
