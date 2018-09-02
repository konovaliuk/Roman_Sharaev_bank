package roman.ironbank.model.dao.interfaces;

import roman.ironbank.domain.account.Account;
import roman.ironbank.domain.account.AccountType;
import roman.ironbank.domain.users.User;

import java.util.LinkedList;

public interface IAccountDao {
    int create(Account entity);

    LinkedList<Account> findAll();

    Account findById(int id);

    LinkedList<Account> findByUserEmail(String email);

    LinkedList<Account> findByUserAndAccountType(User user, AccountType type);

    boolean update(Account entity);

    boolean delete(Account entity);
}
