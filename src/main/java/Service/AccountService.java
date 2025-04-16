package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    
    public Account registerAccount(Account account) {
        if (account == null || account.getUsername() == null || account.getUsername().isBlank() || account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }
        if (accountDAO.getAccountByUsername(account.getUsername())!=null) {
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    public Account loginAccount(Account account) {
        if (account == null || account.getUsername() == null || account.getPassword() == null) {
            return null;
        }
        Account existing = accountDAO.getAccountByUsername(account.getUsername());
        if (existing!=null && existing.getPassword().equals(account.getPassword())) {
            return existing;
        }
        return null;
    }
}
