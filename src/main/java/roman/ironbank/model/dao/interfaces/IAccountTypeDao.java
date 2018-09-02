package roman.ironbank.model.dao.interfaces;

import roman.ironbank.domain.account.AccountType;

import java.util.LinkedList;

public interface IAccountTypeDao {
    int create(AccountType entity);

    LinkedList<AccountType> findAll();

    AccountType findById(int id);

//    AccountType findByName(String name);

    int findId(AccountType entity);

    boolean update(AccountType entity);

    boolean delete(AccountType entity);
}
