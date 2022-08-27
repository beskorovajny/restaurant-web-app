package com.october.to.finish.restaurantwebapp.utils;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {
    public static void rollback(Connection connection) throws DAOException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
