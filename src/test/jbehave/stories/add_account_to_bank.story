Scenario: when a new account is created in the bank, the account is stored correctly

Given a client creates a new bank account in a new bank
Then the amount in the account is 0
And there are no accounts in the bank
When storing the account in a bank
Then the account is stored correctly