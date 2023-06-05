package MainApplicationUI;

import javafx.event.ActionEvent;

import java.io.IOException;

public class rateOrderController {




    public void rateOrderController(){

    }

    public void goToOrder(ActionEvent event) throws IOException {

        MainFoodDeliveryApplication m = new MainFoodDeliveryApplication();
        m.changeScene("userHome.fxml");

    }

    public void goToHistory(ActionEvent event) throws IOException {
        MainFoodDeliveryApplication m = new MainFoodDeliveryApplication();
        m.changeScene("userOrderHistory.fxml");
    }


}
