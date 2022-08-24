package com.october.to.finish.restaurantwebapp.dao.connections;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;

    private ConnectionPoolHolder() {
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource basicDataSource = new BasicDataSource();
                    basicDataSource.setUrl("jdbc:mysql://localhost:3306/restaurant");
                    basicDataSource.setUsername("root");
                    basicDataSource.setPassword("root");
                    basicDataSource.setMinIdle(5);
                    basicDataSource.setMaxIdle(30);
                    basicDataSource.setMaxOpenPreparedStatements(100);
                    dataSource = basicDataSource;
                }
            }
        }
        return dataSource;
    }
}
