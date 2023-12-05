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
	Account certificateOfDeposit;
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
		certificateOfDeposit = new CertificateOfDeposit(1.2, "43215678", 1500);
		bank.addAccount(savingsA.getIdentificationNumber(), savingsA);
		bank.addAccount(savingsB.getIdentificationNumber(), savingsB);
		bank.addAccount(checkingA.getIdentificationNumber(), checkingA);
		bank.addAccount(checkingB.getIdentificationNumber(), checkingB);
		bank.addAccount(certificateOfDeposit.getIdentificationNumber(), certificateOfDeposit);
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
	void cannot_transfer_between_same_account() {
		boolean actual = transferCommandValidator.validate("transfer 12345678 12345678 20");
		assertFalse(actual);
	}

}
