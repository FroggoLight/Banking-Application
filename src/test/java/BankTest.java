import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

	public static final String SAVINGS_QUICK_ID = "14561722";
	public static final String CHECKING_QUICK_ID = "10102234";
	public static final String CERTIFICATE_OF_DEPOSIT_QUICK_ID = "19798871";
	public static final String TO_DEPOSIT = "deposit";
	public static final String TO_WITHDRAW = "withdraw";
	Account savings;
	Account checking;
	Account certificateOfDeposit;
	Bank bank;
	ArrayList<Account> testList;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		testList = new ArrayList<Account>();
		savings = new Savings(3, SAVINGS_QUICK_ID);
		checking = new Checking(4, CHECKING_QUICK_ID);
		certificateOfDeposit = new CertificateOfDeposit(3, CERTIFICATE_OF_DEPOSIT_QUICK_ID, 1500);

		bank.addAccount(savings);
		bank.addAccount(checking);
		bank.addAccount(certificateOfDeposit);
		testList.add(savings);
		testList.add(checking);
		testList.add(certificateOfDeposit);

	}

	@Test
	public void can_create_empty_bank() {
		bank = new Bank();
		assertTrue(bank.getOpenedAccounts().isEmpty());
	}

	@Test
	public void can_add_one_account_into_bank() {
		bank = new Bank();
		bank.addAccount(savings);
		assertEquals(savings, bank.getOpenedAccounts().get(0));
	}

	@Test
	public void can_add_more_than_one_account_into_bank() {
		assertEquals(savings, bank.getOpenedAccounts().get(0));
		assertEquals(checking, bank.getOpenedAccounts().get(1));
		assertEquals(certificateOfDeposit, bank.getOpenedAccounts().get(2));
	}

	@Test
	public void can_retrieve_account_by_id() {
		assertEquals(checking, bank.retrieveAccount(CHECKING_QUICK_ID));
		assertEquals(savings, bank.retrieveAccount(SAVINGS_QUICK_ID));
	}

	@Test
	public void can_deposit_by_account_id() {
		double depositAmount = 350;
		bank.modifyAccountBalance(SAVINGS_QUICK_ID, depositAmount, TO_DEPOSIT);
		assertEquals(350, bank.retrieveAccount(SAVINGS_QUICK_ID).getBalance());
	}

	@Test
	public void can_deposit_multiple_times() {
		double firstDeposit = 220;
		double secondDeposit = 540;
		bank.modifyAccountBalance(CHECKING_QUICK_ID, firstDeposit, TO_DEPOSIT);
		bank.modifyAccountBalance(CHECKING_QUICK_ID, secondDeposit, TO_DEPOSIT);
		assertEquals(760, bank.retrieveAccount(CHECKING_QUICK_ID).getBalance());

	}

	@Test
	public void can_withdraw_by_account_id() {
		double withdrawalAmount = 480;
		bank.modifyAccountBalance(CERTIFICATE_OF_DEPOSIT_QUICK_ID, withdrawalAmount, TO_WITHDRAW);
		assertEquals(1020, bank.retrieveAccount(CERTIFICATE_OF_DEPOSIT_QUICK_ID).getBalance());
	}

	@Test
	public void can_withdraw_multiple_times() {
		double firstWithdrawal = 360;
		double secondWithdrawal = 490;
		bank.modifyAccountBalance(CERTIFICATE_OF_DEPOSIT_QUICK_ID, firstWithdrawal, TO_WITHDRAW);
		bank.modifyAccountBalance(CERTIFICATE_OF_DEPOSIT_QUICK_ID, secondWithdrawal, TO_WITHDRAW);
		assertEquals(650, bank.retrieveAccount(CERTIFICATE_OF_DEPOSIT_QUICK_ID).getBalance());
	}

}
