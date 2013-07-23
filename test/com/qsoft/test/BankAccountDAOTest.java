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

	// 1
	public void testInsertBankAccountDTOToDB() {
		String accountNumber = "0123456789";
		BankAccountDTO bankAccountDTO = initBankAccountDTO(accountNumber);
		long keyId = bankaccountDAO.insert(bankAccountDTO);
		assertEquals(1, keyId);
		assertEquals(1, bankaccountDAO.getNumberRecordInAccountTable());
	}

	// 2
	public void testGetBankAccountByAccountNumberAfterInsertToDB() {
		String accountNumber = "0123456789";
		BankAccountDTO bankAccountDTO = initBankAccountDTO(accountNumber);
		bankaccountDAO.insert(bankAccountDTO);
		BankAccountDTO bankAccountDTOActual = bankaccountDAO.get(accountNumber);
		assertTrue(null != bankAccountDTOActual);
		assertTrue(bankAccountDTO.getAccountNumber().equals(
				bankAccountDTOActual.getAccountNumber()));
		assertTrue(bankAccountDTO.getBalance() == bankAccountDTOActual
				.getBalance());
	}

	// 3
	public void testInsertBankAccountDTODuplicateAccountNumber()
			throws Exception {
		String accountNumber = "0123456789";
		BankAccountDTO bankAccount = initBankAccountDTO(accountNumber);
		bankaccountDAO.insert(bankAccount);

		// insert Duplicate Account Number when insert to DB
		accountNumber = "0123456789";
		bankAccount = initBankAccountDTO(accountNumber);
		bankaccountDAO.insert(bankAccount);

		assertEquals(1, bankaccountDAO.getNumberRecordInAccountTable());
	}

	// 4
	public void testUpdateBankAccountDTO() {
		// add new
		String accountNumber = "0123456789";
		bankaccountDAO.insert(initBankAccountDTO(accountNumber));

		// get to update
		BankAccountDTO bankAccountDTO = bankaccountDAO.get(accountNumber);

		// update new value
		int amount = 500;
		Long timeStamp = System.currentTimeMillis();
		bankAccountDTO.setBalance(bankAccountDTO.getBalance() + amount);
		bankAccountDTO.setTimeStamp(timeStamp);
		long numberOfRowsAffected = bankaccountDAO.update(bankAccountDTO);

		// get data after update to compare
		bankAccountDTO = bankaccountDAO.get(accountNumber);
		assertEquals(1, numberOfRowsAffected);
		assertEquals(amount, bankAccountDTO.getBalance(), 0.01);
		assertEquals(timeStamp, bankAccountDTO.getTimeStamp());
	}

	// 5
	private BankAccountDTO initBankAccountDTO(String accountNumber) {
		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber);
		bankAccount.setTimeStamp(System.currentTimeMillis());
		bankAccount.setBalance(0);
		return bankAccount;
	}

}