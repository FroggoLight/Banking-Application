import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCommandValidatorTest {
	Bank bank;
	Account savings;
	Account checking;
	CreateCommandValidator createCommandValidator;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		savings = new Savings(0.4, "12345678");
		checking = new Checking(1.5, "87654321");
		createCommandValidator = new CreateCommandValidator(bank);
	}

	@Test
	void can_create_with_command() {
		boolean actual = createCommandValidator.validate("Create Savings 12345678 0.4");
		assertTrue(actual);
	}

	@Test
	void cannot_create_account_with_existing_id() {
		bank.addAccount(savings.getIdentificationNumber(), savings);
		boolean actual = createCommandValidator.validate("Create Savings 12345678 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_different_account_type_with_same_id() {
		bank.addAccount(checking.getIdentificationNumber(), checking);
		boolean actual = createCommandValidator.validate("Create Savings 87654321 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_account_with_id_less_than_eight_digits() {
		boolean actual = createCommandValidator.validate("Create Savings 1234567 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_account_with_id_more_than_eight_digits() {
		boolean actual = createCommandValidator.validate("Create Savings 123456789 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_account_with_id_containing_non_numeric_value() {
		boolean actual = createCommandValidator.validate("Create Savings a482efgh 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_account_with_negative_apr() {
		boolean actual = createCommandValidator.validate("Create Savings 12345678 -0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_account_with_apr_higher_than_ten() {
		boolean actual = createCommandValidator.validate("Create Savings 12345678 12.5");
		assertFalse(actual);
	}

	@Test
	void cannot_create_account_with_apr_with_characters() {
		boolean actual = createCommandValidator.validate("Create Savings 12345678 letter");
		assertFalse(actual);
	}

	@Test
	void cannot_create_with_misspelling_on_savings() {
		boolean actual = createCommandValidator.validate("Create svings 12345678 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_savings_with_more_than_four_arguments() {
		boolean actual = createCommandValidator.validate("Create savings 12345678 0.4 1200");
		assertFalse(actual);
	}

	@Test
	void cannot_create_savings_with_less_than_four_arguments() {
		boolean actual = createCommandValidator.validate("Create savings 12345678");
		assertFalse(actual);
	}

	@Test
	void cannot_create_savings_with_swapped_apr_and_id_argument() {
		boolean actual = createCommandValidator.validate("Create Savings 0.4 12345678");
		assertFalse(actual);
	}

	@Test
	void cannot_create_savings_with_swapped_argument_sequence_variation_one() {
		boolean actual = createCommandValidator.validate("Create 0.4 12345678 Savings");
		assertFalse(actual);
	}

	@Test
	void cannot_create_savings_with_swapped_argument_sequence_variation_two() {
		boolean actual = createCommandValidator.validate("Create 12345678 0.4 Savings");
		assertFalse(actual);
	}

	@Test
	void cannot_create_savings_with_swapped_argument_sequence_variation_three() {
		boolean actual = createCommandValidator.validate("Create 12345678 Savings 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_savings_with_swapped_argument_sequence_variation_four() {
		boolean actual = createCommandValidator.validate("Create 0.4 Savings 12345678");
		assertFalse(actual);
	}

	@Test
	void cannot_create_savings_without_create() {
		boolean actual = createCommandValidator.validate("Savings 12345678 0.4");
		assertFalse(actual);
	}

	@Test
	void cannot_create_savings_with_only_account_type() {
		boolean actual = createCommandValidator.validate("Create Savings");
		assertFalse(actual);
	}

}
