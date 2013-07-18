package com.qsoft.test;

import android.test.AndroidTestCase;

import com.qsoft.bank.BankAccountDAO;
import com.qsoft.bank.BankAccountDTO;


public class BankAccountDAOTest extends AndroidTestCase {

	private BankAccountDAO bankaccountDAO;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		bankaccountDAO = new BankAccountDAO(getContext(), null);
	}

	public void testInsertNewAccount() {
		BankAccountDTO bankAccount = createBankAccount("0123456789");
		long result = bankaccountDAO.insert(bankAccount);
		assertEquals(1, result);
		assertEquals(1, bankaccountDAO.getRecordSize());
	}

	public void testGetBankAccountByAccountNumber() {
		String accountNumber = "0123456789";
		BankAccountDTO bankAccount = createBankAccount(accountNumber);
		bankaccountDAO.insert(bankAccount);
		BankAccountDTO bankAccountActual = bankaccountDAO.get(accountNumber);
		assertTrue(null != bankAccountActual);
//		assertTrue(bankAccount == bankAccountActual);
	}

	public void testInsertNewAccountDuplicateAccountNumber() throws Exception {
		String accountNumber = "0123456789";
		BankAccountDTO bankAccount = createBankAccount(accountNumber);
		bankaccountDAO.insert(bankAccount);

		// insert Duplicate Account Number
		bankAccount = createBankAccount(accountNumber);
		bankaccountDAO.insert(bankAccount);

		assertEquals(1, bankaccountDAO.getRecordSize());
	}

	public void testUpdateBankAccount() {
		// add new
		String accountNumber = "0123456789";
		bankaccountDAO.insert(createBankAccount(accountNumber));

		// get to update
		BankAccountDTO bankAccount = bankaccountDAO.get(accountNumber);

		// update new value
		int amount = 500;
		Long timeStamp = System.currentTimeMillis();
		bankAccount.setBalance(bankAccount.getBalance() + amount);
		bankAccount.setTimeStamp(timeStamp);
		long result = bankaccountDAO.update(bankAccount);

		// get after update to compare
		bankAccount = bankaccountDAO.get(accountNumber);
		assertEquals(1, result);
		assertEquals(amount, bankAccount.getBalance(), 0.01);
		assertEquals(timeStamp, bankAccount.getTimeStamp());
	}

	private BankAccountDTO createBankAccount(String accountNumber) {
		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber);
		bankAccount.setTimeStamp(System.currentTimeMillis());
		bankAccount.setBalance(0);
		return bankAccount;
	}

}