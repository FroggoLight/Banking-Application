package banking;

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
	void setUp() {
		bank = new Bank();
		savings = new Savings(0.4, "12345678");
		checking = new Checking(1.5, "87654321");
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void can_not_read_unrecognizable_initial_argument() {
		boolean actual = commandValidator.validate("Frog savings 12345678 0.4");
		assertFalse(actual);
	}

	@Test
	void can_read_command_with_inconsistent_upper_and_lower_cases() {
		boolean actual = commandValidator.validate("CReATe CHEckinG 87654321 0.4");
		assertTrue(actual);
	}

	@Test
	void test_apr_function_works() {
		boolean testCharacterString = commandValidator.checkValidAprValue("@@#$");
		boolean testHighApr = commandValidator.checkValidAprValue("12.5");
		boolean testLowApr = commandValidator.checkValidAprValue("-3.2");
		boolean testValidApr = commandValidator.checkValidAprValue("2.5");
		assertFalse(testCharacterString);
		assertFalse(testHighApr);
		assertFalse(testLowApr);
		assertTrue(testValidApr);
	}

}
