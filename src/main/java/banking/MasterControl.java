package banking;

import java.util.List;

public class MasterControl {

	private CommandValidator commandValidator;
	private CommandProcessor commandProcessor;
	private CommandStorage commandStorage;
	private OutputListGenerator outputListGenerator;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage, OutputListGenerator outputListGenerator) {
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
		this.outputListGenerator = outputListGenerator;
	}

	public List<String> start(List<String> input) {

		for (String command : input) {
			if (commandValidator.validate(command)) {
				commandProcessor.process(command);
				commandStorage.addValidCommand(command);
			} else {
				commandStorage.addInvalidCommand(command);
			}
		}

		List<String> validCommands = commandStorage.getValidCommands();
		List<String> invalidCommands = commandStorage.getInvalidCommands();

		return outputListGenerator.generateOutput(validCommands, invalidCommands);
	}

	public void testDoNotImplement() {
		List<String> testingNoUse = commandStorage.getValidCommands();
	}
}
