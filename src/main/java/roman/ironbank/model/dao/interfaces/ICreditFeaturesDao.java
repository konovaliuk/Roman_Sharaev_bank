package roman.ironbank.model.dao.interfaces;

import roman.ironbank.domain.account.CreditFeatures;

import java.util.LinkedList;

public interface ICreditFeaturesDao {
    int create(CreditFeatures entity);

    LinkedList<CreditFeatures> findAll();

    CreditFeatures findById(int id);

//    boolean update(CreditFeatures entity);
//
//    boolean delete(CreditFeatures entity);
}
