package roman.ironbank.model.dao.interfaces;

import roman.ironbank.domain.users.Admin;

import java.util.LinkedList;

public interface IAdminDao {
    int create(Admin entity);

    LinkedList<Admin> findAll();

//    Admin findById(int id);

    Admin findByEmail(String email);

    boolean update(Admin entity);

//    boolean delete(Admin entity);
}
