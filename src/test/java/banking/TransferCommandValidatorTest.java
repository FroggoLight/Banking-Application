package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandValidatorTest {

	Bank bank;
	Account savingsA;
	Account savingsB;
	Account checkingA;
	Account checkingB;
	Account certificateOfDepositA;
	Account certificateOfDepositB;
	DepositCommandValidator depositCommandValidator;
	WithdrawCommandValidator withdrawCommandValidator;
	TransferCommandValidator transferCommandValidator;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		savingsA = new Savings(0.4, "12345678");
		savingsB = new Savings(0.5, "22345678");
		savingsA.modifyBalance(2000, "deposit");
		savingsB.modifyBalance(500, "deposit");
		checkingA = new Checking(1.5, "87654321");
		checkingA.modifyBalance(2000, "deposit");
		checkingB = new Checking(1.8, "88765432");
		checkingB.modifyBalance(1000, "deposit");
		certificateOfDepositA = new CertificateOfDeposit(1.2, "43215678", 1500);
		certificateOfDepositB = new CertificateOfDeposit(1.4, "33215678", 2000);
		bank.addAccount(savingsA.getIdentificationNumber(), savingsA);
		bank.addAccount(savingsB.getIdentificationNumber(), savingsB);
		bank.addAccount(checkingA.getIdentificationNumber(), checkingA);
		bank.addAccount(checkingB.getIdentificationNumber(), checkingB);
		bank.addAccount(certificateOfDepositA.getIdentificationNumber(), certificateOfDepositA);
		bank.addAccount(certificateOfDepositB.getIdentificationNumber(), certificateOfDepositB);
		depositCommandValidator = new DepositCommandValidator(bank);
		withdrawCommandValidator = new WithdrawCommandValidator(bank);
		transferCommandValidator = new TransferCommandValidator(bank, depositCommandValidator,
				withdrawCommandValidator);
	}

	@Test
	void can_transfer_between_two_savings_normally() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 22345678 500");
		assertTrue(actual);
	}

	@Test
	void can_transfer_between_two_savings_with_highest_withdraw_bound_of_one_thousand() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 22345678 1000");
		assertTrue(actual);
	}

	@Test
	void cannot_transfer_between_two_savings_with_more_than_one_thousand() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 22345678 1100");
		assertFalse(actual);
	}

	@Test
	void can_transfer_between_two_checking_normally() {
		boolean actual = transferCommandValidator.validate("transfer 87654321 88765432 300");
		assertTrue(actual);
	}

	@Test
	void can_transfer_between_two_checking_with_the_highest_bound_of_four_hundred() {
		boolean actual = transferCommandValidator.validate("transfer 87654321 88765432 400");
		assertTrue(actual);
	}

	@Test
	void cannot_transfer_between_two_checking_with_more_than_four_hundred() {
		boolean actual = transferCommandValidator.validate("transfer 87654321 88765432 700");
		assertFalse(actual);
	}

	@Test
	void can_transfer_from_savings_to_checking_normally() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 87654321 500");
		assertTrue(actual);
	}

	@Test
	void can_transfer_from_savings_to_checking_with_highest_saving_limit_of_one_thousand() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 87654321 1000");
		assertTrue(actual);
	}

	@Test
	void cannot_transfer_from_savings_to_checking_with_more_than_one_thousand() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 87654321 1400");
		assertFalse(actual);
	}

	@Test
	void can_transfer_from_checking_to_savings_normally() {
		boolean actual = transferCommandValidator.validate("transfer 87654321 12345678 200");
		assertTrue(actual);
	}

	@Test
	void can_transfer_from_checking_to_savings_with_highest_checking_limit_of_four_hundred() {
		boolean actual = transferCommandValidator.validate("transfer 87654321 12345678 400");
		assertTrue(actual);
	}

	@Test
	void cannot_transfer_from_checking_to_savings_with_more_than_four_hundred() {
		boolean actual = transferCommandValidator.validate("transfer 87654321 12345678 500");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_between_cd_accounts() {
		boolean actual = transferCommandValidator.validate("transfer 43215678 33215678 300");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_from_cd_to_savings() {
		boolean actual = transferCommandValidator.validate("transfer 43215678 12345678 300");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_from_cd_to_checking() {
		boolean actual = transferCommandValidator.validate("transfer 43215678 87654321 300");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_from_savings_to_cd() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 43215678 300");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_from_checking_to_cd() {
		boolean actual = transferCommandValidator.validate("transfer 87654321 43215678 300");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_between_same_account() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 12345678 20");
		assertFalse(actual);
	}

	@Test
	void command_can_be_case_insensitive() {
		boolean actual = transferCommandValidator.validate("TrAnSFer 12345678 22345678 500");
		assertTrue(actual);
	}

	@Test
	void cannot_only_have_one_account_as_argument_for_transferring() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 500");
		assertFalse(actual);
	}

	@Test
	void cannot_have_zero_accounts_as_argument_for_transferring() {
		boolean actual = transferCommandValidator.validate("transfer 500");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_to_account_without_specifying_ID() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 Savings 500");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_from_account_without_specifying_ID() {
		boolean actual = transferCommandValidator.validate("transfer Savings 22345678 500");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_between_account_without_specifying_ID() {
		boolean actual = transferCommandValidator.validate("transfer Savings Checking 500");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_with_amount_as_first_of_set_of_arguments() {
		boolean actual = transferCommandValidator.validate("transfer 500 12345678 87654321");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_with_amount_as_second_of_set_of_arguments() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 500 87654321");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_between_account_without_specifying_transfer_amount() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 87654321");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_amount_that_are_not_numerical() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 22345678 ninety");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_amount_with_non_numerical_sign() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 22345678 $90");
		assertFalse(actual);
	}

	@Test
	void can_transfer_amount_with_decimal_values() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 22345678 300.50");
		assertTrue(actual);
	}

	@Test
	void cannot_transfer_between_accounts_with_merged_arguments() {
		boolean actual = transferCommandValidator.validate("transfer 12345678->22345678 500");
		assertFalse(actual);
	}

	@Test
	void cannot_transfer_savings_to_savings_twice_in_a_month() {
		boolean actualA = transferCommandValidator.validate("transfer 12345678 22345678 500");
		bank.retrieveAccount("12345678").modifyBalance(500, "withdraw");
		bank.retrieveAccount("22345678").modifyBalance(500, "deposit");
		boolean actualB = transferCommandValidator.validate("transfer 12345678 22345678 500");

		assertTrue(actualA);
		assertFalse(actualB);
	}

	@Test
	void can_transfer_savings_to_savings_once_per_month_in_different_months() {
		boolean actualA = transferCommandValidator.validate("transfer 12345678 22345678 500");
		bank.retrieveAccount("12345678").modifyBalance(500, "withdraw");
		bank.retrieveAccount("22345678").modifyBalance(500, "deposit");
		savingsA.incrementPassedMonths(1);
		savingsB.incrementPassedMonths(1);
		boolean actualB = transferCommandValidator.validate("transfer 12345678 22345678 500");

		assertTrue(actualA);
		assertTrue(actualB);
	}

	@Test
	void cannot_transfer_savings_to_checking_twice_in_a_month() {
		boolean actualA = transferCommandValidator.validate("transfer 12345678 88765432 500");
		bank.retrieveAccount("12345678").modifyBalance(500, "withdraw");
		bank.retrieveAccount("88765432").modifyBalance(500, "deposit");
		boolean actualB = transferCommandValidator.validate("transfer 12345678 88765432 500");

		assertTrue(actualA);
		assertFalse(actualB);
	}

	@Test
	void can_transfer_savings_to_checking_once_per_month_in_different_months() {
		boolean actualA = transferCommandValidator.validate("transfer 12345678 88765432 500");
		bank.retrieveAccount("12345678").modifyBalance(500, "withdraw");
		bank.retrieveAccount("88765432").modifyBalance(500, "deposit");
		savingsA.incrementPassedMonths(1);
		savingsB.incrementPassedMonths(1);
		boolean actualB = transferCommandValidator.validate("transfer 12345678 88765432 500");

		assertTrue(actualA);
		assertTrue(actualB);
	}

	@Test
	void can_transfer_checking_to_checking_twice_in_a_month() {
		boolean actualA = transferCommandValidator.validate("transfer 87654321 88765432 200");
		bank.retrieveAccount("87654321").modifyBalance(200, "withdraw");
		bank.retrieveAccount("88765432").modifyBalance(200, "deposit");
		boolean actualB = transferCommandValidator.validate("transfer 87654321 88765432 200");

		assertTrue(actualA);
		assertTrue(actualB);
	}

	@Test
	void can_transfer_checking_to_savings_twice_in_a_month() {
		boolean actualA = transferCommandValidator.validate("transfer 87654321 12345678 200");
		bank.retrieveAccount("87654321").modifyBalance(200, "withdraw");
		bank.retrieveAccount("12345678").modifyBalance(200, "deposit");
		boolean actualB = transferCommandValidator.validate("transfer 87654321 12345678 200");

		assertTrue(actualA);
		assertTrue(actualB);
	}

}
