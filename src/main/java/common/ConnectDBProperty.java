package common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectDBProperty {
    public static String getConnectionUrlFromClasssPath() {
        String str = null;
        try (InputStream file = ConnectDBProperty.class.getClassLoader().getResourceAsStream("db.properties");) {
            var pro = new Properties();
            pro.load(file);
            str = pro.getProperty("url") + pro.getProperty("serverName") + ":" + pro.getProperty("portNumber")
                    + ";databaseName=" + pro.getProperty("databaseName") + ";user=" + pro.getProperty("username")
                    + ";password=" + pro.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        return str;
    }

    public static Connection getConnectionFromClassPath() {
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(getConnectionUrlFromClasssPath());
        } catch (Exception e) {
          e.printStackTrace();
        }
        return connect;
    }
}
