package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnect {
    private final String MySQLname = "jdbc:mysql://localhost:3306/fooddeliveryapp";
    private final String username ="root";
    private final String password = "B@@kc@s3";
    private final Connection conector = DriverManager.getConnection(MySQLname,username,password);

    public SQLConnect() throws SQLException {
        //constructorul imi spune daca nu ne putem connecta
        try {
            Connection conector = DriverManager.getConnection(MySQLname,username,password);
        }
        catch ( SQLException e){
            System.out.println("Wrong path");
        }
    }
}
