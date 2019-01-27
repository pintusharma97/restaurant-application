package pintu_sharma97.com.mrchef;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Categorydata implements Serializable {
    @SerializedName("category")
    ArrayList<CategoryList> categoryList;

    public ArrayList<CategoryList> getCategoryList() {
        return categoryList;
    }
}
