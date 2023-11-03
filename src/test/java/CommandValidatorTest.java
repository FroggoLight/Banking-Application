import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	Bank bank;
	Account savings;
	CommandValidator commandValidator;

	@BeforeEach
	void setUP() {
		bank = new Bank();
		savings = new Savings(0.4, "12345678");
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void can_create_with_command() {
		boolean actual = commandValidator.validate("Create Savings 12345678 0.4");
		assertTrue(actual);
	}

	@Test
	void id_can_not_be_less_than_eight_digits() {
		bank.addAccount(savings.getIdentificationNumber(), savings);
		boolean actual = commandValidator.validate("Create Savings 12345678 0.4");
		assertFalse(actual);
	}

}
