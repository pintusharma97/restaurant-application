package pintu_sharma97.com.mrchef;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetCartData implements Serializable {
    @SerializedName("cart")
    ArrayList<CartData> cartData;

    public ArrayList<CartData> getCartData() {
        return cartData;
    }
}
