package Services;

import Classes.*;

import javax.swing.plaf.nimbus.State;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

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

    public Vector<Vector<String>> getRestauransCity(String oras){

        Vector<Vector<String>> respond = new Vector<>();
        String query = "select * from restaurant where restaurant_oras = " + "'" + oras + "'";
        try{
            Statement st = conector.createStatement();
            ResultSet respondData = st.executeQuery(query);
            while(respondData.next())
            {
                Vector<String> lineRespond = new Vector<>();
                String restaurantName = respondData.getString("restaurant_nume");
                String restaurantRating = respondData.getString("restaurant_rating");
                String restaurantAdress = respondData.getString("restaurant_adress");
                String restaurantID = respondData.getString("id_restaurant");
                //String restaurantCity = respondData.getString("restaurant_oras");
                lineRespond.add(restaurantName);
                lineRespond.add(restaurantRating);
                lineRespond.add(restaurantAdress);
                lineRespond.add(restaurantID);

                respond.add(lineRespond);

            }

        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }

        return respond;
    }

    public Vector<String> getRestaurantInfo(String id){

        Vector<String> respond = new Vector<>();
        String query = "select * from restaurant where id_restaurant = " + id;

        try {
                Statement st = conector.createStatement();
                ResultSet respondData = st.executeQuery(query);

                while(respondData.next())
                {
                    String restaurantName = respondData.getString("restaurant_nume");
                    String restaurantRating = respondData.getString("restaurant_rating");
                    String restaurantCity = respondData.getString("restaurant_oras");
                    String restaurantAdress = respondData.getString("restaurant_adress");

                    respond.add(restaurantName);
                    respond.add(restaurantRating);
                    respond.add(restaurantCity);
                    respond.add(restaurantAdress);

                }



        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }



        return respond;


    }

    public int getNumberReviewsRestaurant(String id){

        int numberReviews = 0;

        String query = " select count(*) from reviews where id_restaurant = " + id;

        try{
            Statement st = conector.createStatement();
            ResultSet respondData = st.executeQuery(query);

            while(respondData.next()){
                numberReviews = Integer.valueOf(respondData.getString("count(*)"));
            }

        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }

        return numberReviews;
    }

    public Vector<Review> getReviewsRestaurant(String id){

        Vector<Review> reviewsRestaurant = new Vector<>();
        String query = "select * from reviews where id_restaurant =" + id;

        try{
            Statement st = conector.createStatement();
            ResultSet respondData = st.executeQuery(query);

            while(respondData.next()){

                String reviewsRateRestaurant = respondData.getString("reviews_rate");
                String reviewsUsernameRestaurant = respondData.getString("username_user");
                String reviewDescriptionRestaurant = respondData.getString("reviews_description");

                Review tempReviewRestaurant = new Review(Integer.valueOf(id),Integer.valueOf(reviewsRateRestaurant),reviewsUsernameRestaurant,reviewDescriptionRestaurant);
                reviewsRestaurant.add(tempReviewRestaurant);
            }


        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }


        return reviewsRestaurant;
    }


    public String getNumeRestaurant(String id){

        String restaurantName = "";

        String query = "select restaurant_nume from restaurant\n" +
                "where id_restaurant " + id;

        try {
            Statement st = conector.createStatement();
            ResultSet respondData = st.executeQuery(query);

            while( respondData.next()){

                restaurantName = respondData.getString("restaurant_nume");

            }

        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }

        return restaurantName;
    }

    public Vector<Produs> loadMenuRestaurant(int id){

        Vector<Produs> meniu = new Vector<>();

        String query = " select * from meniu where id_restaurant = " + id + ";";
        try{
            Statement st = conector.createStatement();
            ResultSet respondDataOrders = st.executeQuery(query);

            while(respondDataOrders.next())
            {
                Produs tempProdus = new Produs();
                String tempNumeProdus = respondDataOrders.getString("nume_produs");
                String tempPriceProdus = respondDataOrders.getString("price_produs");
                String tempIngredienteProdus = respondDataOrders.getString("ingrediente_produs");

                tempProdus.setNumeProdus(tempNumeProdus);
                tempProdus.setPretProdus(Float.parseFloat(tempPriceProdus));
                tempProdus.setIngredienteProdus(tempIngredienteProdus);

                meniu.add(tempProdus);

            }
        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }

        return meniu;

    }


    public Voucher searchVoucher(String codID){

        Voucher tempVoucher = new Voucher();

        String query = "select * from voucher where cod = " + "'" + codID + "'";

        try{
            Statement st = conector.createStatement();
            ResultSet respondData = st.executeQuery(query);

            while(respondData.next())
            {
                String tempCod = respondData.getString("cod");
                int tempValue = Integer.parseInt(respondData.getString("voucher_value"));
                String statusVoucher = respondData.getString("statusVoucher");

                tempVoucher.setCodVoucher(tempCod);
                tempVoucher.setValue(tempValue);
                tempVoucher.setStatus(statusVoucher);
            }

        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }

        return  tempVoucher;

    }

    public int getOrderCurrent(){

        int id = 0;
        String query = "select count(*) from comanda";

        try {

            Statement st = conector.createStatement();
            ResultSet respondData = st.executeQuery(query);



            while(respondData.next())
            {
                id = Integer.parseInt(respondData.getString("count(*)"));



            }

        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }

        return id;

    }

    public int getInsertOrder(){
        int futureOrderId = 0;
        String query = "select id_comanda from comanda";

        try{
            Statement st = conector.createStatement();
            ResultSet respondData = st.executeQuery(query);

            while(respondData.next())
            {
                futureOrderId = Integer.parseInt(respondData.getString("id_comanda"));
            }

        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }

        return futureOrderId;
    }

    public void insertOrderDatabase(int idComanda, String username, String usernameLivrator, int idRestaurant, int orderDay, int orderMonth, int orderYear, String adress, int suma){


    }



    public boolean historyOrder(String username){

        boolean isFirst = true;

        String query = " select count(*) from comanda where username_client = " + "'" + username + "'";

        try{

            Statement st = conector.createStatement();
            ResultSet respondData = st.executeQuery(query);

            while( respondData.next())
            {
                int check = Integer.parseInt(respondData.getString("count(*)"));
                if( check > 0)
                {
                    isFirst = false;
                }
                else {
                        isFirst = true ;
                }
            }

        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }

        return isFirst;

    }


    public Vector<Comanda> loadOrders(){

        Vector<Comanda> orders = new Vector<>();

        String query1 = "select * from comanda";

        try {

            Statement st = conector.createStatement();
            ResultSet respondDataOrders = st.executeQuery(query1);

            while(respondDataOrders.next())
            {
                String tempUsernameLivrator = respondDataOrders.getString("username_livrator");
                String tempUsernameUser = respondDataOrders.getString("username_client");
                String tempIdRestaurant = respondDataOrders.getString("id_restaurant");
                String numeRestaurant = getNumeRestaurant(tempIdRestaurant);
                String tempIdComanda = respondDataOrders.getString("id_comanda");
                String tempAdress = respondDataOrders.getString("adress");
                String tempSuma = respondDataOrders.getString("suma");






            }


        } catch (SQLException e) {
            System.out.println("Eroare in executarea comenzii SQL");
        }

        return orders;
    }



}
