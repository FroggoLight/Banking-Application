import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	Bank bank;
	CommandValidator commandValidator;

	@BeforeEach
	void setUP() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void can_create_with_command() {
		boolean actual = commandValidator.validate("Create Savings 12345678 0.4");
		assertTrue(actual);
	}

}
