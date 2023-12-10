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
		return invalidCommands;
	}

	public ArrayList<String> getValidCommands() {

		return validCommands;
	}

}
