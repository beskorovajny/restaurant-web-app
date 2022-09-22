package com.october.to.finish.app.web.restaurant.utils.db;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
    public static final String MYSQL_PROPS_PATH = "/db.properties";
    private DBUtils() {}
    public static void rollback(Connection connection) throws DAOException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
