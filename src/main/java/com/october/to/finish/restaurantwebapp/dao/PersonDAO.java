package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DBException;
import com.october.to.finish.restaurantwebapp.model.Person;

import java.util.List;

public interface PersonDAO extends GenericDAO<Person> {
    boolean insertPerson(Person person) throws DBException;

    boolean deletePerson(Person person) throws DBException;

    boolean updatePerson(Person person) throws DBException;

    Person getPersonById(long personId) throws DBException;

    Person getPersonByEmail(String eMail) throws DBException;

    List<Person> findAllPersons() throws DBException;
}
