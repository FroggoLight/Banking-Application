package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CertificateOfDepositTest {

	CertificateOfDeposit certificateOfDeposit;

	@Test
	public void can_create_cod_account_with_specified_balance() {
		certificateOfDeposit = new CertificateOfDeposit(0.5, "11223345", 150.34);
		assertEquals(150.34, certificateOfDeposit.getBalance());
	}

}
