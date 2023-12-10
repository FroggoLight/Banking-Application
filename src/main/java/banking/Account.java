package banking;

import java.util.Objects;

public abstract class Account {

	private String identificationNumber;
	private double apr;
	private double balance;
	private String accountType;
	private int monthsPassed;
	private boolean withdrawable;

	public Account(double APR, String identificationNumber, double balance, String accountType) {
		this.apr = APR;
		this.identificationNumber = identificationNumber;
		this.balance = balance;
		this.accountType = accountType;
		this.monthsPassed = 0;
		this.withdrawable = true;
	}

	public double getAPR() {
		return apr;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public double getBalance() {
		return Math.floor(balance * 100.0) / 100.0;
	}

	public String getAccountType() {
		return accountType;
	}

	public int getMonthsPassed() {
		return monthsPassed;
	}

	public boolean getWithdrawStatus() {
		return withdrawable;
	}

	public void modifyWithdrawStatus(boolean status) {
		this.withdrawable = status;
	}

	public void incrementPassedMonths(int numOfMonths) {
		this.monthsPassed += numOfMonths;
		if (Objects.equals(this.accountType, "savings")) {
			modifyWithdrawStatus(true);
		}
	}

	public void modifyBalance(double amount, String operation) {
		if (amount < 0) {
			balance += 0;
		} else {
			if (Objects.equals(operation, "withdraw")) {
				amount *= -1;
				if (Objects.equals(accountType, "savings")) {
					modifyWithdrawStatus(false);
				}
			}
			balance += amount;
			if (balance < 0) {
				balance = 0;
			}
		}
	}

	public void applyMinimumBalancePenalty() {
		this.balance -= 25;
		if (this.balance < 0) {
			this.balance = 0;
		}
	}

	public void applyAPRToBalance() {
		double accountAPR = this.apr / 1200;
		this.balance += this.balance * accountAPR;
	}

}
