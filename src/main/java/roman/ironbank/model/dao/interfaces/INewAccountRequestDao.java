package roman.ironbank.model.dao.interfaces;

import roman.ironbank.domain.account.AccountType;
import roman.ironbank.domain.account.NewAccountRequest;
import roman.ironbank.domain.users.User;

import java.util.LinkedList;

public interface INewAccountRequestDao {
    int create(NewAccountRequest entity);

    LinkedList<NewAccountRequest> findAll();

    NewAccountRequest findById(int id);

    LinkedList<NewAccountRequest> findAllNotConfirmed();

    boolean setConfirmedById(int id);

    boolean setDeclinedById(int id);

    boolean isContainNotConfirmedByUserAndType(AccountType type, User user);

//    LinkedList<NewAccountRequest> findAllDeclined();
}
