import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	Bank bank;
	Account savings;
	Account checking;
	CommandValidator commandValidator;

	@BeforeEach
	void setUP() {
		bank = new Bank();
		savings = new Savings(0.4, "12345678");
		checking = new Checking(1.5, "87654321");
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void can_create_with_command() {
		boolean actual = commandValidator.validate("Create Savings 12345678 0.4");
		assertTrue(actual);
	}

	@Test
	void cannot_create_account_with_existing_id() {
		bank.addAccount(savings.getIdentificationNumber(), savings);
		boolean actual = commandValidator.validate("Create Savings 12345678 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_different_account_type_with_same_id() {
		bank.addAccount(checking.getIdentificationNumber(), checking);
		boolean actual = commandValidator.validate("Create Savings 87654321 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_account_with_id_less_than_eight_digits() {
		boolean actual = commandValidator.validate("Create Savings 1234567 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_account_with_id_more_than_eight_digits() {
		boolean actual = commandValidator.validate("Create Savings 123456789 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_account_with_id_containing_non_numeric_value() {
		boolean actual = commandValidator.validate("Create Savings a482efgh 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_account_with_negative_apr() {
		boolean actual = commandValidator.validate("Create Savings 12345678 -0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_account_with_apr_higher_than_ten() {
		boolean actual = commandValidator.validate("Create Savings 12345678 12.5");
		assertFalse(actual);
	}

	@Test
	void cannot_create_account_with_apr_with_characters() {
		boolean actual = commandValidator.validate("Create Savings 12345678 letter");
		assertFalse(actual);
	}

}
