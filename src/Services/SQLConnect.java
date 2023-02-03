package Services;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SQLConnect {
    private final String MySQLname = "jdbc:mysql://localhost:3306/fooddeliveryapp";
    private final String username = "root";
    private final String password = "B@@kc@s3";
    private final Connection conector = DriverManager.getConnection(MySQLname, username, password);

    public SQLConnect() throws SQLException {
        //constructorul imi spune daca nu ne putem connecta
        try {
            Connection conector = DriverManager.getConnection(MySQLname, username, password);
        } catch (SQLException e) {
            System.out.println("Wrong path");
        }
    }

    public boolean Login(String username, String pass) {
        boolean loginOK = false;
        String query = "select * from users where username =" + "'" + username + "'";
        try {
            Statement st = conector.createStatement();
            ResultSet respondData = st.executeQuery(query);

            while (respondData.next()) {
                String tempUsername = respondData.getString("username");
                String tempPass = respondData.getString("pass");


                if (pass.equals(tempPass))
                    return true;
            }
        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }
        return false;

    }

    public int findUserType(String username){
        int tempUserType = -1;
        // 0 - user
        // 1 - livrator
        // 2/3 - restaurant/admin

        String query = " select userType from users where username = " + "'" + username + "'";
        try {
            Statement st = conector.createStatement();
            ResultSet respondData = st.executeQuery(query);

            while ( respondData.next()){
                tempUserType = Integer.parseInt(respondData.getString("userType"));
            }

        } catch (SQLException e){
            System.out.println("Eroare in executarea comenzii SQL");
        }

        return tempUserType;
    }

    public boolean searchUsername(String username){

        boolean userUnique = false;
        String query = "select count(*) from users where username=" + "'" + username + "'";

        try{
            Statement st = conector.createStatement();
            ResultSet respondData = st.executeQuery(query);
            while(respondData.next())
            {
                String numarCount = respondData.getString("count(*)");

                if(Integer.parseInt(numarCount) == 0)
                {
                    userUnique = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }

        return  userUnique;
    }

    public void insertUserInDatabase(String username, String password, String nume, String prenume, String email, String telefon, int userType, int bday, int bmonth, int byear, String gender){

        String query = "insert into users (username, pass, nume, prenume, email, telefon, userType, birthDay, birthMonth, birthYear, gender ) VALUES " +
                "(" + "'" + username + "'" + "," + "'" + password + "'" + "," + "'" + nume + "'" + "," + "'" + prenume + "'" + "," +
                "'" + email + "'" + "," + "'" + telefon + "'" + "," + userType + "," + bday + "," + bmonth + "," + byear + "," + "'" + gender + "'" + ")";

        try{
            Statement st = conector.createStatement();
            st.execute(query);

        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }

    }

    public void insterAuditLog(String description){

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime dateCurrent = LocalDateTime.now();

        String query = "insert into auditlog ( operationDate, operationType ) VALUES "  + "(" + "'" + dateFormat.format(dateCurrent) + "'" + "," +
                "'" + description + "'"
                + ")";

        try{
            Statement st = conector.createStatement();
            st.execute(query);
        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }

    }
}
