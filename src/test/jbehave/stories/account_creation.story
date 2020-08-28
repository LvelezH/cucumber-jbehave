Scenario: when a new account is created, the amount is zero and there are no movements

Given a new account
Then the amount in the account is 0
And there are no movements in the account
