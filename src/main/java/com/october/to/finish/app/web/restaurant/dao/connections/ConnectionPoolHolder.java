package com.october.to.finish.app.web.restaurant.dao.connections;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;

    private ConnectionPoolHolder() {
    }

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
}
