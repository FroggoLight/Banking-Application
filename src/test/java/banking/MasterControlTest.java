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
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage(),
				new OutputListGenerator(bank));
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

		assertEquals(2, actual.size());
		assertEquals("Checking 87654321 0.00 1.00", actual.get(0));
		assertEquals("create checking 87654321 1.0", actual.get(1));
	}

	@Test
	void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

	@Test
	void sample_test_withdrawing_from_savings_with_pass_months() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Withdraw 12345678 200");
		input.add("Pass 0");
		input.add("Withdraw 12345678 300");
		input.add("Pass 1");
		input.add("Withdraw 12345678 200");
		input.add("Pass 61");

		List<String> actual = masterControl.start(input);

		assertEquals(7, actual.size());
		assertEquals("Savings 12345678 300.25 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Withdraw 12345678 200", actual.get(2));
		assertEquals("Withdraw 12345678 200", actual.get(3));
		assertEquals("Pass 0", actual.get(4));
		assertEquals("Withdraw 12345678 300", actual.get(5));
		assertEquals("Pass 61", actual.get(6));
	}

	@Test
	void sample_test_transferring_with_less_than_account_amount_works_intended() {
		input.add("Create savings 12345678 0.6");
		input.add("Create checking 87654321 0.4");
		input.add("Deposit 12345678 400");
		input.add("Transfer 12345678 87654321 300");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 100.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 400", actual.get(1));
		assertEquals("Transfer 12345678 87654321 300", actual.get(2));
		assertEquals("Checking 87654321 300.00 0.40", actual.get(3));
		assertEquals("Transfer 12345678 87654321 300", actual.get(4));

	}

	@Test
	void sample_test_transferring_with_more_than_account_amount_does_not_provide_more_to_other_account() {
		input.add("Create savings 12345678 0.6");
		input.add("Create checking 87654321 0.4");
		input.add("Deposit 12345678 400");
		input.add("Transfer 12345678 87654321 500");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 0.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 400", actual.get(1));
		assertEquals("Transfer 12345678 87654321 500", actual.get(2));
		assertEquals("Checking 87654321 400.00 0.40", actual.get(3));
		assertEquals("Transfer 12345678 87654321 500", actual.get(4));

	}

	@Test
	void sample_test_cd_when_passing_month_will_correctly_increase_balance() {
		input.add("Create cd 43215678 2.1 2000");
		input.add("Pass 1");

		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("Cd 43215678 2014.03 2.10", actual.get(0));

	}

	@Test
	void sample_test_cd_cannot_be_deposited() {
		input.add("Create cd 43215678 2.1 2000");
		input.add("Deposit 43215678 150");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Cd 43215678 2000.00 2.10", actual.get(0));
		assertEquals("Deposit 43215678 150", actual.get(1));

	}

	@Test
	void sample_test_cd_cannot_be_withdrawn_after_twelve_months_and_at_full_or_more_balance() {
		input.add("Create cd 43215678 2.1 2000");
		input.add("Withdraw 43215678 2000");
		input.add("Pass 6");
		input.add("Withdraw 43215678 3000");
		input.add("Pass 6");
		input.add("Withdraw 43215678 4000");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Cd 43215678 0.00 2.10", actual.get(0));
		assertEquals("Withdraw 43215678 4000", actual.get(1));
		assertEquals("Withdraw 43215678 2000", actual.get(2));
		assertEquals("Withdraw 43215678 3000", actual.get(3));

	}

	@Test
	void sample_test_invalid_and_valid_action_arguments() {
		input.add("Crater savings 12345678 0.6");
		input.add("Create savings 12345678 0.6");
		input.add("Depot 12345678 300");
		input.add("Past 6");
		input.add("DeposIT 12345678 300");
		input.add("I want to be at the bottom");

		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 300.00 0.60", actual.get(0));
		assertEquals("DeposIT 12345678 300", actual.get(1));
		assertEquals("Crater savings 12345678 0.6", actual.get(2));
		assertEquals("Depot 12345678 300", actual.get(3));
		assertEquals("Past 6", actual.get(4));
		assertEquals("I want to be at the bottom", actual.get(5));

	}

}
