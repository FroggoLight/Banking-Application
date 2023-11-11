import static org.junit.jupiter.api.Assertions.assertEquals;
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
		commandProcessor.process("create savings 12345678 0.4");
	}

	@Test
	void can_process_account_creation_for_savings() {
		assertTrue(bank.accountExistsByQuickId("12345678"));
	}

	@Test
	void can_process_account_creation_with_another_existing_account() {
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

	@Test
	void can_deposit_into_savings() {
		commandProcessor.process("deposit 12345678 300");
		assertEquals(300, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void can_deposit_into_checking() {
		commandProcessor.process("create checking 87654321 0.6");
		commandProcessor.process("deposit 87654321 200");
		assertEquals(200, bank.retrieveAccount("87654321").getBalance());
	}

	@Test
	void can_deposit_four_hundred_into_savings_with_balance_of_two_hundred() {
		bank.modifyAccountBalance("12345678", 200, "deposit");
		commandProcessor.process("deposit 12345678 400");
		assertEquals(600, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void can_deposit_four_hundred_into_checking_with_balance_of_two_hundred() {
		commandProcessor.process("create checking 87654321 0.6");
		bank.modifyAccountBalance("87654321", 200, "deposit");
		commandProcessor.process("deposit 87654321 400");
		assertEquals(600, bank.retrieveAccount("87654321").getBalance());
	}

}
