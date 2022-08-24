package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.PersonDAO;
import com.october.to.finish.restaurantwebapp.exceptions.DBException;
import com.october.to.finish.restaurantwebapp.model.Person;

import java.sql.Connection;
import java.util.List;

public class PersonDAOImpl implements PersonDAO {
    private final Connection connection;

    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertPerson(Person person) throws DBException {
        return false;
    }

    @Override
    public boolean deletePerson(Person person) throws DBException {
        return false;
    }

    @Override
    public boolean updatePerson(Person person) throws DBException {
        return false;
    }

    @Override
    public Person getPersonById(long personId) throws DBException {
        return null;
    }

    @Override
    public Person getPersonByEmail(String eMail) throws DBException {
        return null;
    }

    @Override
    public List<Person> findAllPersons() throws DBException {
        return null;
    }
}
