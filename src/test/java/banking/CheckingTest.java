package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CheckingTest {

	Checking checking;

	@Test
	public void can_create_checking_account_with_zero_balance() {
		checking = new Checking(1, "12345642");
		assertEquals(0, checking.getBalance());
	}

}
