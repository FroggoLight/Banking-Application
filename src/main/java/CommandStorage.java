import java.util.ArrayList;

public class CommandStorage {

	ArrayList<String> invalidCommands;

	public CommandStorage() {
		invalidCommands = new ArrayList<String>();
	}

	public void addInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public ArrayList<String> getInvalidCommands() {
		return invalidCommands;
	}
}
