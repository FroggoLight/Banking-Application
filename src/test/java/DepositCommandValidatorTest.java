import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandValidatorTest {

	Bank bank;
	Account savings;
	Account checking;
	Account certificateOfDeposit;
	DepositCommandValidator depositCommandValidator;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		savings = new Savings(0.4, "12345678");
		checking = new Checking(1.5, "87654321");
		certificateOfDeposit = new CertificateOfDeposit(1.2, "43215678", 1500);
		bank.addAccount(savings.getIdentificationNumber(), savings);
		bank.addAccount(checking.getIdentificationNumber(), checking);
		bank.addAccount(certificateOfDeposit.getIdentificationNumber(), certificateOfDeposit);
		depositCommandValidator = new DepositCommandValidator(bank);
	}

	@Test
	void can_deposit_into_savings_normally() {
		boolean actual = depositCommandValidator.validate("Deposit 12345678 750");
		assertTrue(actual);
	}

	@Test
	void can_deposit_zero_into_savings_normally() {
		boolean actual = depositCommandValidator.validate("Deposit 12345678 0");
		assertTrue(actual);
	}

	@Test
	void can_deposit_into_savings_with_decimal_amount() {
		boolean actual = depositCommandValidator.validate("Deposit 12345678 325.50");
		assertTrue(actual);
	}

	@Test
	void cannot_deposit_into_savings_with_negative_amount() {
		boolean actual = depositCommandValidator.validate("Deposit 12345678 -1");
		assertFalse(actual);
	}

	@Test
	void cannot_deposit_into_savings_with_more_than_2500() {
		boolean actual = depositCommandValidator.validate("Deposit 12345678 4500");
		assertFalse(actual);
	}

	@Test
	void cannot_deposit_into_savings_with_dollar_sign() {
		boolean actual = depositCommandValidator.validate("Deposit 12345678 $2000");
		assertFalse(actual);
	}

	@Test
	void cannot_deposit_into_savings_normally_with_non_numeric_character() {
		boolean actual = depositCommandValidator.validate("Deposit 12345678 five-hundred");
		assertFalse(actual);
	}

	@Test
	void cannot_deposit_into_savings_without_specified_amount() {
		boolean actual = depositCommandValidator.validate("Deposit 12345678");
		assertFalse(actual);
	}

}
