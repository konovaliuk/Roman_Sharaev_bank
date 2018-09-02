package roman.ironbank.model.dao.interfaces;

import roman.ironbank.domain.users.User;

import java.util.LinkedList;

public interface IUserDao {

    int create(User entity);

    LinkedList<User> findAll();

    User findById(int id);

//    User findByName(String name);

    User findByEmail(String email);

    boolean update(User entity);

    boolean delete(User entity);
}
