package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandValidatorTest {
	Bank bank;
	Account savings;
	Account checking;
	Account certificateOfDeposit;
	WithdrawCommandValidator withdrawCommandValidator;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		savings = new Savings(0.4, "12345678");
		savings.modifyBalance(2000, "deposit");
		checking = new Checking(1.5, "87654321");
		certificateOfDeposit = new CertificateOfDeposit(1.2, "43215678", 1500);
		bank.addAccount(savings.getIdentificationNumber(), savings);
		bank.addAccount(checking.getIdentificationNumber(), checking);
		bank.addAccount(certificateOfDeposit.getIdentificationNumber(), certificateOfDeposit);
		withdrawCommandValidator = new WithdrawCommandValidator(bank);
	}

	@Test
	void can_withdraw_from_savings_normally() {
		boolean actual = withdrawCommandValidator.validate("withdraw 12345678 750");
		assertTrue(actual);
	}

	@Test
	void can_withdraw_maximum_from_savings_at_once() {
		boolean actual = withdrawCommandValidator.validate("withdraw 12345678 1000");
		assertTrue(actual);
	}

	@Test
	void can_withdraw_zero_from_savings() {
		boolean actual = withdrawCommandValidator.validate("withdraw 12345678 0");
		assertTrue(actual);
	}

	@Test
	void cannot_withdraw_more_than_one_thousand_from_savings() {
		boolean actual = withdrawCommandValidator.validate("withdraw 12345678 1001");
		assertFalse(actual);
	}

	@Test
	void can_withdraw_from_checking_normally() {
		boolean actual = withdrawCommandValidator.validate("withdraw 87654321 350");
		assertTrue(actual);
	}

	@Test
	void can_withdraw_maximum_from_checking() {
		boolean actual = withdrawCommandValidator.validate("withdraw 87654321 400");
		assertTrue(actual);
	}

	@Test
	void can_withdraw_zero_from_checking() {
		boolean actual = withdrawCommandValidator.validate("withdraw 87654321 0");
		assertTrue(actual);
	}

	@Test
	void cannot_withdraw_more_than_four_hundred_from_checking() {
		boolean actual = withdrawCommandValidator.validate("withdraw 87654321 450");
		assertFalse(actual);
	}

	@Test
	void cannot_withdraw_from_cd_without_reaching_twelve_months() {
		certificateOfDeposit.incrementPassedMonths(10);
		boolean actual = withdrawCommandValidator.validate("withdraw 43215678 1500");
		assertFalse(actual);
	}

	@Test
	void cannot_withdraw_from_cd_without_full_balance() {
		certificateOfDeposit.incrementPassedMonths(12);
		boolean actual = withdrawCommandValidator.validate("withdraw 43215678 1350");
		assertFalse(actual);
	}

	@Test
	void can_withdraw_from_cd_after_reaching_twelve_months_and_withdraws_full_balance() {
		certificateOfDeposit.incrementPassedMonths(12);
		boolean actual = withdrawCommandValidator.validate("withdraw 43215678 1500");
		assertTrue(actual);
	}

	@Test
	void can_withdraw_from_cd_after_reaching_twelve_months_and_amount_over_the_balance() {
		certificateOfDeposit.incrementPassedMonths(12);
		boolean actual = withdrawCommandValidator.validate("withdraw 43215678 15000");
		assertTrue(actual);
	}

	@Test
	void cannot_withdraw_zero_from_cd() {
		boolean actual = withdrawCommandValidator.validate("withdraw 43215678 0");
		assertFalse(actual);
	}

	@Test
	void cannot_withdraw_from_savings_twice_in_a_month() {
		boolean initial = withdrawCommandValidator.validate("withdraw 12345678 750");
		assertTrue(initial);
		savings.modifyBalance(750, "withdraw");
		boolean second = withdrawCommandValidator.validate("withdraw 12345678 500");
		assertFalse(second);
	}

	@Test
	void can_withdraw_from_savings_twice_in_different_months() {
		boolean initial = withdrawCommandValidator.validate("withdraw 12345678 750");
		assertTrue(initial);
		savings.modifyBalance(750, "withdraw");
		boolean second = withdrawCommandValidator.validate("withdraw 12345678 500");
		assertFalse(second);
		savings.incrementPassedMonths(1);
		boolean third = withdrawCommandValidator.validate("withdraw 12345678 500");
		assertTrue(third);
	}

	@Test
	void cannot_withdraw_negative_balance() {
		boolean savingCase = withdrawCommandValidator.validate("withdraw 12345678 -20");
		boolean checkingCase = withdrawCommandValidator.validate("withdraw 87654321 -20");
		boolean cdCase = withdrawCommandValidator.validate("withdraw 43215678 -20");
		assertFalse(savingCase);
		assertFalse(checkingCase);
		assertFalse(cdCase);
	}

	@Test
	void cannot_withdraw_without_specifying_accountID() {
		boolean actual = withdrawCommandValidator.validate("withdraw 250");
		assertFalse(actual);
	}

	@Test
	void cannot_withdraw_without_specifying_withdraw_amount() {
		boolean actual = withdrawCommandValidator.validate("withdraw 12345678");
		assertFalse(actual);
	}

	@Test
	void cannot_withdraw_non_numerical_amount() {
		boolean actual = withdrawCommandValidator.validate("withdraw 12345678 two-hundred");
		assertFalse(actual);
	}

	@Test
	void cannot_withdraw_amount_with_unknown_symbols() {
		boolean actual = withdrawCommandValidator.validate("withdraw 12345678 $65");
		assertFalse(actual);
	}

	@Test
	void can_withdraw_from_account_with_decimal_values() {
		boolean actual = withdrawCommandValidator.validate("withdraw 12345678 30.50");
		assertTrue(actual);
	}

	@Test
	void cannot_withdraw_amount_with_more_than_two_decimals() {
		boolean actual = withdrawCommandValidator.validate("withdraw 12345678 30.50.322");
		assertFalse(actual);
	}

	@Test
	void cannot_withdraw_with_swapped_accountID_and_amount() {
		boolean actual = withdrawCommandValidator.validate("withdraw 300 12345678");
		assertFalse(actual);
	}

	@Test
	void cannot_withdraw_with_no_provided_arguments() {
		boolean actual = withdrawCommandValidator.validate("withdraw");
		assertFalse(actual);
	}

}
