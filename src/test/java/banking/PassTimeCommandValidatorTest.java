package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeCommandValidatorTest {

	PassTimeCommandValidator passTimeCommandValidator;

	@BeforeEach
	void setUp() {
		passTimeCommandValidator = new PassTimeCommandValidator();
	}

	@Test
	void can_pass_one_month() {
		boolean actual = passTimeCommandValidator.validate("pass 1");
		assertTrue(actual);
	}

	@Test
	void can_pass_sixty_months() {
		boolean actual = passTimeCommandValidator.validate("pass 60");
		assertTrue(actual);
	}

	@Test
	void can_pass_numerical_month_within_range() {
		boolean actual = passTimeCommandValidator.validate("pass 34");
		assertTrue(actual);
	}

	@Test
	void cannot_pass_zero_months() {
		boolean actual = passTimeCommandValidator.validate("pass 0");
		assertFalse(actual);
	}

	@Test
	void cannot_pass_sixty_one_months() {
		boolean actual = passTimeCommandValidator.validate("pass 61");
		assertFalse(actual);
	}

	@Test
	void cannot_pass_negative_months() {
		boolean actual = passTimeCommandValidator.validate("pass -2");
		assertFalse(actual);
	}

	@Test
	void cannot_pass_non_numerical_months() {
		boolean actual = passTimeCommandValidator.validate("pass ten");
		assertFalse(actual);
	}

	@Test
	void cannot_pass_decimal_months() {
		boolean actual = passTimeCommandValidator.validate("pass 6.0");
		assertFalse(actual);
	}

	@Test
	void cannot_pass_with_no_specified_months() {
		boolean actual = passTimeCommandValidator.validate("pass");
		assertFalse(actual);
	}
}
