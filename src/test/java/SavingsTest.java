import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SavingsTest {

	Savings savings;

	@Test
	public void can_create_savings_account_with_zero_balance() {
		savings = new Savings(1, "12345678");
		assertEquals(0, savings.getBalance());
	}

}
