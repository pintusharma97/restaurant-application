package pintu_sharma97.com.mrchef;

import com.google.gson.annotations.SerializedName;

public class CartData {
    @SerializedName("name")
    String name;

    @SerializedName("image")
    String image;

    @SerializedName("quantity")
    int quantitty;

    @SerializedName("price")
    int price;

    @SerializedName("category")
    String category;

    @SerializedName("total")
    int total;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getQuantitty() {
        return quantitty;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getTotal() {
        return total;
    }
}
