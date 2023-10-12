import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

	Account savings;

	@BeforeEach
	public void setUp() {
		savings = new Savings(4, "46382243");
		savings.modifyBalance(450, "deposit");
	}

	@Test
	public void can_create_general_bank_account_with_specified_apr() {
		assertEquals(4, savings.getAPR());
	}

	@Test
	public void can_deposit_into_general_bank_account() {
		assertEquals(450, savings.getBalance());
	}

	@Test
	public void can_withdrawal_from_general_bank_account() {
		savings.modifyBalance(270.55, "withdraw");
		assertEquals(179.45, savings.getBalance());
	}

	@Test
	public void cannot_withdrawal_more_than_balance_from_general_bank_account() {
		savings.modifyBalance(675.75, "withdraw");
		assertEquals(0, savings.getBalance());
	}

	@Test
	public void can_deposit_twice_into_general_bank_account() {
		savings.modifyBalance(50.53, "deposit");
		savings.modifyBalance(72.46, "deposit");
		assertEquals(572.99, savings.getBalance());
	}

	@Test
	public void can_withdrawal_twice_from_general_bank_account() {
		savings.modifyBalance(50.53, "withdraw");
		savings.modifyBalance(72.46, "withdraw");
		assertEquals(327.01, savings.getBalance());
	}
}