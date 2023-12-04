package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {

	Bank bank;
	List<String> input;
	MasterControl masterControl;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage());
	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@Test
	void typo_in_create_command_is_invalid() {
		input.add("creat checking 87654321 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 87654321 1.0", actual);
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		input.add("depositt 87654321 100");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("depositt 87654321 100", actual);
	}

	@Test
	void two_typo_commands_both_invalid() {
		input.add("creat checking 87654321 1.0");
		input.add("depositt 87654321 100");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("creat checking 87654321 1.0", actual.get(0));
		assertEquals("depositt 87654321 100", actual.get(1));
	}

	@Test
	void invalid_to_create_accounts_with_same_ID() {
		input.add("create checking 87654321 1.0");
		input.add("create checking 87654321 1.0");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("create checking 87654321 1.0", actual);
	}

}