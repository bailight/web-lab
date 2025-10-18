package com.web.table;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;


public class TabCreator {
    private static final String DB_PROPERTIES_FILE = "/db.properties";
    private static final String SQL_FILE = "/sql/table.sql";

    public Properties dbProps;

    public TabCreator() throws Exception {
        createTables();
    }

    private Properties loadDbProperties() {
        Properties props = new Properties();
        try (InputStream conf = getClass().getResourceAsStream(DB_PROPERTIES_FILE)) {
            props.load(conf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }

    private String loadTableSql() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream(SQL_FILE)) {
            if (inputStream != null) {
                byte[] bytes = inputStream.readAllBytes();
                return new String(bytes, StandardCharsets.UTF_8);
            }else{
                return null;
            }
        }
    }

    public void createTables() throws Exception {
        dbProps = loadDbProperties();
        String sql = loadTableSql();

        String url = dbProps.getProperty("db.url");
        String user = dbProps.getProperty("db.user");
        String password = dbProps.getProperty("db.password");

        Connection conn = DriverManager.getConnection(url, user, password);
        Statement statement = conn.createStatement();
        statement.execute(sql);
        statement.close();
    }
}
