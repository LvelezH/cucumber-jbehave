package bank;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static bank.OperationType.DEPOSIT;
import static bank.OperationType.WITHDRAWAL;

@Builder
@Getter
@Setter
@Slf4j
@EqualsAndHashCode
public class Account {
    public static final String WITHDRAWAL_ERROR = "Get some more money and try again";

    private String client;
    @Builder.Default @EqualsAndHashCode.Exclude private int amount = 0;
    @Builder.Default @EqualsAndHashCode.Exclude private List<BankOperation> movements = new ArrayList<>();

    public void deposit(int amount) {
        this.amount += amount;
        this.movements.add(new BankOperation(DEPOSIT, amount));
    }

    public void withdrawal(int amount) {
        if (amount > this.amount) {
            log.error(WITHDRAWAL_ERROR);
            return;
        }

        this.amount -= amount;
        this.movements.add(new BankOperation(WITHDRAWAL, amount));
    }
}
