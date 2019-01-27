package pintu_sharma97.com.mrchef;

import com.google.gson.annotations.SerializedName;

public class TabItem {
    @SerializedName("name")
    String name;

    @SerializedName("image")
    String image;

    @SerializedName("category")
    String category;

    @SerializedName("type")
    String type;

    @SerializedName("price")
    int price;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }
}
