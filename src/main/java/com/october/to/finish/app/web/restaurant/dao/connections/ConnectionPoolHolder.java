package com.october.to.finish.app.web.restaurant.dao.connections;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for creating database connections
 */
public class ConnectionPoolHolder {
    private static final Logger log = LogManager.getLogger(ConnectionPoolHolder.class);
    private static volatile DataSource dataSource;

    private ConnectionPoolHolder() {
    }

    /**
     * Method for receive Datasource object using configuration properties file
     * @param path is a properties file path value
     * @return {@link DataSource}
     * @throws DAOException if something wrong
     */
    public static DataSource getDataSource(String path) throws DAOException {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    try (InputStream inputStream = ConnectionPoolHolder.class.getResourceAsStream(path);
                         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                        Properties prop = new Properties();
                        prop.load(reader);

                        BasicDataSource basicDataSource = new BasicDataSource();
                        basicDataSource.setUrl(prop.getProperty("mysql.url"));
                        basicDataSource.setUsername(prop.getProperty("mysql.user"));
                        basicDataSource.setPassword(prop.getProperty("mysql.password"));
                        basicDataSource.setDriverClassName(prop.getProperty("mysql.driver"));
                        basicDataSource.setMinIdle(5);
                        basicDataSource.setMaxIdle(30);
                        basicDataSource.setMaxOpenPreparedStatements(100);
                        basicDataSource.setMaxWaitMillis(10000);
                        dataSource = basicDataSource;
                    } catch (IOException ex) {
                        throw new DAOException(ex.getMessage(), ex);
                    }
                }
            }
        }
        return dataSource;
    }

    /**
     * @return database connection using JNDI features using
     * src/main/webapp/META-INF/context.xml configuration file
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            DataSource dataSource = (DataSource) initContext.lookup("java:/comp/env/jdbc/mysqlDB");
            connection = dataSource.getConnection();
        } catch (SQLException | NamingException e) {
            log.error("Failed to create connection.");
        }
        return connection;
    }
}
