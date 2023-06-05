package Classes;

public class Produs {

    protected String numeProdus;
    protected String ingredienteProdus;
    protected float pretProdus;
    private  int idProdsX;

    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    public void setIngredienteProdus(String ingredienteProdus) {
        this.ingredienteProdus = ingredienteProdus;
    }

    public void setPretProdus(float pretProdus) {
        this.pretProdus = pretProdus;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public String getIngredienteProdus() {
        return ingredienteProdus;
    }

    public float getPretProdus() {
        return pretProdus;
    }
}
