import bank.Account;
import bank.BankOperation;
import bank.OperationType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {

    private Account account;

    @Test
    public void shouldCreateTheAccountCorrectly() {
        givenANewAccount();
        thenTheAccountHasNoBalance();
        andThereAreNoMovementsInTheAccount();
    }

    @Test
    public void shouldDepositCorrectly() {
        givenANewAccount();
        whenClientDepositsTenDollars();
        thenTheBalanceIsUpdatedCorrectly(10);
        andTheMovementsAreSavedCorrectly(new BankOperation(OperationType.DEPOSIT, 10));
    }

    @Test
    public void shouldWithdrawalCorrectlyIfAmountLessThanAvailable() {
        givenANewAccount();
        whenClientDepositsTenDollars();
        whenClientWithdrawalsAnAmount(5);
        thenTheBalanceIsUpdatedCorrectly(5);
        andTheMovementsAreSavedCorrectly(new BankOperation(OperationType.DEPOSIT, 10), new BankOperation(OperationType.WITHDRAWAL, 5));
    }

    @Test
    public void shouldWithdrawalCorrectlyIfAmountSameThanAvailable() {
        givenANewAccount();
        whenClientDepositsTenDollars();
        whenClientWithdrawalsAnAmount(10);
        thenTheBalanceIsUpdatedCorrectly(0);
        andTheMovementsAreSavedCorrectly(new BankOperation(OperationType.DEPOSIT, 10), new BankOperation(OperationType.WITHDRAWAL, 10));
    }

    @Test
    public void shouldNotChangeAmountIfWithdrawalBiggerThanAvailable() {
        givenANewAccount();
        whenClientDepositsTenDollars();
        whenClientWithdrawalsAnAmount(15);
        thenTheBalanceIsUpdatedCorrectly(10);
        andTheMovementsAreSavedCorrectly(new BankOperation(OperationType.DEPOSIT, 10));
    }

    private void givenANewAccount() {
        account = Account.builder()
                .client("Test")
                .build();
    }

    private void whenClientDepositsTenDollars() {
        account.deposit(10);
    }

    private void whenClientWithdrawalsAnAmount(int amount) {
        account.withdrawal(amount);
    }

    private void thenTheBalanceIsUpdatedCorrectly(int expectedBalance) {
        assertEquals(account.getAmount(), expectedBalance);
    }

    private void andTheMovementsAreSavedCorrectly(BankOperation... operation) {
        assertEquals(operation.length, account.getMovements().size());

        Arrays.stream(operation).forEach(op -> assertTrue(account.getMovements().contains(op)));
    }

    private void thenTheAccountHasNoBalance() {
        assertEquals(0, account.getAmount());
    }

    private void andThereAreNoMovementsInTheAccount() {
        assertTrue(account.getMovements().isEmpty());
    }
}
