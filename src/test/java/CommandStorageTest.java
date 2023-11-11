import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {

	public static final String QUICK_INVALID_CREATE_STRING = "creat savings 12345678 0.4";
	public static final String QUICK_INVALID_DEPOSIT_STRING = "depositt 12345678 300";
	CommandStorage commandStorage;

	@BeforeEach
	void setUp() {
		commandStorage = new CommandStorage();
	}

	@Test
	void can_add_invalid_create_command() {
		commandStorage.addInvalidCommand(QUICK_INVALID_CREATE_STRING);
		ArrayList<String> actual = commandStorage.getInvalidCommands();
		assertEquals(actual.get(0), QUICK_INVALID_CREATE_STRING);
	}

	@Test
	void can_add_invalid_deposit_command() {
		commandStorage.addInvalidCommand(QUICK_INVALID_DEPOSIT_STRING);
		ArrayList<String> actual = commandStorage.getInvalidCommands();
		assertEquals(actual.get(0), QUICK_INVALID_DEPOSIT_STRING);
	}

	@Test
	void can_store_multiple_invalid_commands() {
		commandStorage.addInvalidCommand(QUICK_INVALID_CREATE_STRING);
		commandStorage.addInvalidCommand(QUICK_INVALID_DEPOSIT_STRING);
		ArrayList<String> actual = commandStorage.getInvalidCommands();
		assertEquals(actual.get(0), QUICK_INVALID_CREATE_STRING);
		assertEquals(actual.get(1), QUICK_INVALID_DEPOSIT_STRING);

	}
}
