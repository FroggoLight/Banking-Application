package banking;

import static org.junit.jupiter.api.Assertions.*;

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
		commandProcessor.process("create checking 87654321 0.6");
		commandProcessor.process("create cd 43215678 2.0 1000");

	}

	@Test
	void can_process_account_creation_for_savings() {
		assertTrue(bank.accountExistsByQuickId("12345678"));
	}

	@Test
	void can_process_account_creation_with_another_existing_account() {
		assertTrue(bank.accountExistsByQuickId("12345678"));
		assertTrue(bank.accountExistsByQuickId("87654321"));
		assertTrue(bank.accountExistsByQuickId("43215678"));
	}

	@Test
	void can_process_account_creation_for_checking() {
		assertTrue(bank.accountExistsByQuickId("87654321"));
	}

	@Test
	void can_process_account_creation_for_cd() {
		assertTrue(bank.accountExistsByQuickId("43215678"));
	}

	@Test
	void can_deposit_into_savings() {
		commandProcessor.process("deposit 12345678 300");
		assertEquals(300, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void can_deposit_into_checking() {
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
		bank.modifyAccountBalance("87654321", 200, "deposit");
		commandProcessor.process("deposit 87654321 400");
		assertEquals(600, bank.retrieveAccount("87654321").getBalance());
	}

	@Test
	void can_withdraw_from_savings() {
		commandProcessor.process("deposit 12345678 400");
		commandProcessor.process("withdraw 12345678 300");
		assertEquals(100, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void can_withdraw_from_checking() {
		commandProcessor.process("deposit 87654321 400");
		commandProcessor.process("withdraw 87654321 100");
		assertEquals(300, bank.retrieveAccount("87654321").getBalance());
	}

	@Test
	void can_withdraw_from_cd() {
		commandProcessor.process("withdraw 43215678 1000");
		assertEquals(0, bank.retrieveAccount("43215678").getBalance());
	}

	@Test
	void can_transfer_between_accounts() {
		commandProcessor.process("deposit 12345678 400");
		commandProcessor.process("transfer 12345678 87654321 300");
		assertEquals(100, bank.retrieveAccount("12345678").getBalance());
		assertEquals(300, bank.retrieveAccount("87654321").getBalance());
	}

	@Test
	void can_pass_time_of_one_month_and_update_account_balance() {
		commandProcessor.process("deposit 12345678 100");
		commandProcessor.process("deposit 87654321 150");
		commandProcessor.process("pass 1");

		assertEquals(100.03, bank.retrieveAccount("12345678").getBalance());
		assertEquals(150.07, bank.retrieveAccount("87654321").getBalance());
		assertEquals(1006.68, bank.retrieveAccount("43215678").getBalance());
	}

	@Test
	void can_pass_time_and_process_penalty_for_account_below_one_hundred() {
		commandProcessor.process("deposit 12345678 75");
		commandProcessor.process("pass 1");

		assertEquals(50.01, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	void can_pass_time_and_process_account_removal_with_balance_of_zero() {
		commandProcessor.process("deposit 12345678 50");
		commandProcessor.process("pass 4");

		assertFalse(bank.accountExistsByQuickId("12345678"));
	}

}
