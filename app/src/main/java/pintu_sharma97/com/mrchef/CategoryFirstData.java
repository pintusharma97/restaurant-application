package pintu_sharma97.com.mrchef;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryFirstData implements Serializable {
    @SerializedName("category")
    ArrayList<DataCategory> dataCategory;

    public ArrayList<DataCategory> getDataCategory() {
        return dataCategory;
    }
}
