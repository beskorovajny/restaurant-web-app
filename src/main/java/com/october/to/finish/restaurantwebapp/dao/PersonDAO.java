package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Person;

import java.util.List;

public interface PersonDAO extends GenericDAO<Person> {
    boolean insertPerson(Person person) throws DAOException;

    boolean deletePerson(long personId) throws DAOException;

    boolean updatePerson(long personId, Person person) throws DAOException;

    Person getPersonById(long personId) throws DAOException;

    Person getPersonByEmail(String eMail) throws DAOException;

    List<Person> findAllPersons() throws DAOException;
}
