package MainApplicationUI;

import Services.SQLConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class UserOrderHistoryController {

    public void UserOrderHistory(){

    }


    @FXML
    private Label isFirstOrder;

    @FXML
    private AnchorPane showHistoryPanel;


    public void initialize() throws SQLException {


        Preferences userPreferences = Preferences.userRoot();
        String selectedUsername = userPreferences.get("username","none");

        SQLConnect SQL = new SQLConnect();
        boolean maybeNoOrder = SQL.historyOrder(selectedUsername);

        if( maybeNoOrder == true)
        {
            isFirstOrder.setVisible(true);
        }





        // if login succesful initializez ala









    }

    public void goToTempRate(ActionEvent event) throws IOException {

        MainFoodDeliveryApplication m = new MainFoodDeliveryApplication();
        m.changeScene("rateOrder.fxml");

    }



}
