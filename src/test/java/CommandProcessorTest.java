import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	Bank bank;
	CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void can_process_account_creation_for_savings() {
		commandProcessor.process("create savings 12345678 0.4");
		assertTrue(bank.accountExistsByQuickId("12345678"));
	}

	@Test
	void can_process_account_creation_with_another_existing_account() {
		commandProcessor.process("create savings 12345678 0.4");
		commandProcessor.process("create checking 87654321 0.6");
		assertTrue(bank.accountExistsByQuickId("12345678"));
		assertTrue(bank.accountExistsByQuickId("87654321"));
	}

	@Test
	void can_process_account_creation_for_checking() {
		commandProcessor.process("create checking 87654321 0.6");
		assertTrue(bank.accountExistsByQuickId("87654321"));
	}

	@Test
	void can_process_account_creation_for_cd() {
		commandProcessor.process("create cd 43215678 2.0 1000");
		assertTrue(bank.accountExistsByQuickId("43215678"));
	}

}
