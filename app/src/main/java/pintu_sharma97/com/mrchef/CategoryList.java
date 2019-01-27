package pintu_sharma97.com.mrchef;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryList implements Serializable {

    @SerializedName("categoryname")
    String categoryname;

    @SerializedName("menuid")
    int menuid;

    @SerializedName("image")
    String image;

    public String getImage() {
        return image;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public int getMenuid() {
        return menuid;
    }
}
