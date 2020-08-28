Scenario: depositing an amount of money in an account

Given a new account
When the client performs a deposit for an amount of <deposited>
Then the amount in the account is <expected>
And the deposit of <deposited> is correctly stored in the account

Examples:
|deposited  |expected
|10         |10|
|30         |30|