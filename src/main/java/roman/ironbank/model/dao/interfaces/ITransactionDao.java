package roman.ironbank.model.dao.interfaces;


import roman.ironbank.domain.account.Transaction;
import roman.ironbank.domain.users.Admin;

import java.util.LinkedList;

public interface ITransactionDao {

    int create(Transaction entity);

    LinkedList<Transaction> findAll();

    Transaction findById(int id);

    LinkedList<Transaction> findByAccountId(int accountId);

//    boolean update(Transaction entity);
//
//    boolean delete(Transaction entity);
}
