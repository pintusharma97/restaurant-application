package pintu_sharma97.com.mrchef;

import com.google.gson.annotations.SerializedName;

public class MenuItems {
    @SerializedName("name")
    String name;

    @SerializedName("image")
    String image;

    @SerializedName("price")
    int price;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
