package com.october.to.finish.restaurantwebapp.dao.connections;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;

    private ConnectionPoolHolder() {
    }

    public static DataSource getDataSource() throws DAOException {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/mysql/db.properties"))) {
                        Properties prop = new Properties();
                        prop.load(input);

                        BasicDataSource basicDataSource = new BasicDataSource();
                        basicDataSource.setUrl(prop.getProperty("mysql.url"));
                        basicDataSource.setUsername(prop.getProperty("mysql.user"));
                        basicDataSource.setPassword(prop.getProperty("mysql.password"));
                        basicDataSource.setMinIdle(5);
                        basicDataSource.setMaxIdle(30);
                        basicDataSource.setMaxOpenPreparedStatements(100);
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
