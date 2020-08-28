import bank.Account;
import bank.Bank;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    private static final String CLIENT_NAME = "Name";

    private Bank bank;

    @Test
    public void shouldAddNewAccountCorrectly() {
        givenABankWithNoAccounts();
        whenAddingANewAccount();
        thenTheAccountIsCreatedCorrectly();
    }

    @Test
    public void shouldRetrieveTheCorrectAccountForAGivenClient() {
        givenABankWithAnAccount();
        Optional<Account> account = whenRetrievingTheClientAccount();
        thenTheClientAccountIsRetrievedCorrectly(account);
    }

    @Test
    public void shouldRetrieveEmptyIfAccountDoesNotExist() {
        givenABankWithNoAccounts();
        Optional<Account> account = whenRetrievingTheClientAccount();
        thenNoAccountIsRetrieved(account);
    }

    @Test
    public void shouldDeleteAnAccountCorrectly() {
        givenABankWithAnAccount();
        whenDeletingTheStoredAccount();
        thenTheAccountIsDeletedCorrectly();
    }

    @Test
    public void shouldUpdateAnAccountCorrectly() {
        givenABankWithAnAccount();
        whenUpdatingTheStoredAccount();
        thenTheAccountIsUpdatedCorrectly();
    }

    private void givenABankWithNoAccounts() {
        bank = new Bank();
    }

    private void givenABankWithAnAccount() {
        givenABankWithNoAccounts();
        whenAddingANewAccount();
    }

    private void whenAddingANewAccount() {
        Account account = Account.builder()
                .client(CLIENT_NAME)
                .build();

        bank.createNewAccount(account);
    }

    private Optional<Account> whenRetrievingTheClientAccount() {
        return bank.getClientAccount(CLIENT_NAME);
    }

    private void whenDeletingTheStoredAccount() {
        bank.deleteAccount(CLIENT_NAME);
    }

    private void whenUpdatingTheStoredAccount() {
        Optional<Account> account = bank.getClientAccount(CLIENT_NAME);

        account.ifPresent(a -> {
            a.deposit(30);
            bank.updateAccount(a);
        });
    }

    private void thenTheAccountIsCreatedCorrectly() {
        assertEquals(1, bank.getAccounts().size());
        assertEquals(CLIENT_NAME, bank.getAccounts().get(0).getClient());
        assertEquals(0, bank.getAccounts().get(0).getAmount());
        assertTrue(bank.getAccounts().get(0).getMovements().isEmpty());
    }

    private void thenTheClientAccountIsRetrievedCorrectly(Optional<Account> account) {
        assertTrue(account.isPresent());
        assertEquals(CLIENT_NAME,account.get().getClient());
    }

    private void thenNoAccountIsRetrieved(Optional<Account> account) {
        assertFalse(account.isPresent());
    }

    private void thenTheAccountIsDeletedCorrectly() {
        assertFalse(bank.getClientAccount(CLIENT_NAME).isPresent());
    }

    private void thenTheAccountIsUpdatedCorrectly() {
        bank.getClientAccount(CLIENT_NAME)
                .ifPresent(a -> assertEquals(30,a.getMovements().get(0).getAmount()));
    }
}
