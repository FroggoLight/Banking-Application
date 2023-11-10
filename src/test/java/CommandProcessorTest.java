import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	Bank bank;
	CommandProcessor commandProcessor;

	@Test
	void can_process_account_creation() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		commandProcessor.process("create savings 12345678 0.4");
		assertTrue(bank.accountExistsByQuickId("12345678"));
	}
}
