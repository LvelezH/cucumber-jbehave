package bank;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class Bank {
    private final List<Account> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    public void createNewAccount(Account account) {
        this.accounts.add(account);
    }

    public Optional<Account> getClientAccount(String clientName) {
        return accounts.stream()
                .filter(account -> clientName.equals(account.getClient()))
                .findFirst();
    }

    public void deleteAccount(String clientName) {
        getClientAccount(clientName)
                .ifPresent(this.accounts::remove);
    }

    public void updateAccount(Account account) {
        getClientAccount(account.getClient())
                .ifPresent(a -> a.setMovements(account.getMovements()));
    }
}
