package banking;

import java.util.ArrayList;

public class CommandStorage {

	private ArrayList<String> invalidCommands;
	private ArrayList<String> validCommands;

	public CommandStorage() {
		invalidCommands = new ArrayList<>();
		validCommands = new ArrayList<>();
	}

	public void addInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public void addValidCommand(String command) {
		validCommands.add(command);
	}

	public ArrayList<String> getInvalidCommands() {
		/*
		 * int length = invalidCommands.size(); for (int i = 0; i < length; i++) {
		 * System.out.println(invalidCommands.get(i)); }
		 */
		return invalidCommands;
	}

	public ArrayList<String> getValidCommands() {
		/*
		 * int length = validCommands.size(); for (int j = 0; j < length; j++) {
		 * System.out.println(validCommands.get(j)); }
		 */
		return validCommands;
	}

	// create new class that has a list , each new create command moves to creating
	// then use its id to see all transaction history
	// then look for next command, and repeat until no commands are left
}
