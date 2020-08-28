package steps;

import bank.Account;
import bank.Bank;
import bank.BankOperation;
import bank.OperationType;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankSteps {

    public static final String CLIENT_NAME = "Name";
    private Account account;
    private Bank bank;

    @Given("a new account")
    public void givenANewAccount() {
        account = Account.builder().client(CLIENT_NAME).build();
    }

    @Given("a new bank")
    public void givenANewBank() {
        bank = new Bank();
    }

    @Given("a client creates a new bank account in a new bank")
    @Composite(steps = { "Given a new account",
                         "Given a new bank" })
    public void givenClientCreatesNewBankAccount() {
    }

    @When("the client performs a deposit for an amount of $deposited")
    public void whenPerformsDeposit(int deposited) {
        account.deposit(deposited);
    }

    @When("storing the account in a bank")
    public void whenStoringAccountInBank() {
        bank.createNewAccount(account);
    }

    @Then("the amount in the account is zero")
    public void thenTheAmountIsZero() {
        assertEquals(0, account.getAmount());
    }

    @Then("the amount in the account is $expected")
    public void thenAmountIsCorrect(int expected) {
        assertEquals(expected, account.getAmount());
    }

    @Then("the deposit of $deposited is correctly stored in the account")
    public void thenTheDepositIsStoredInAccount(int deposited) {
        assertEquals(1, account.getMovements().size());
        assertTrue(account.getMovements().contains(new BankOperation(OperationType.DEPOSIT, deposited)));
    }

    @Then("there are no accounts in the bank")
    public void thenNoAccountsInTheBank() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Then("the account is stored correctly")
    public void thenAccountStoredCorrectly() {
        assertTrue(bank.getClientAccount(CLIENT_NAME).isPresent());
    }

    @Then("there are no movements in the account")
    public void thenNoMovementsInTheAccount() {
        assertTrue(account.getMovements().isEmpty());
    }

    @Then("happens whatever")
    @Composite(steps = { "Then the amount in the account is zero",
            "Then there are no movements in the account" })
    public void thenAccountAmountIsZeroAndThereAreNoMovements() {
    }
}
