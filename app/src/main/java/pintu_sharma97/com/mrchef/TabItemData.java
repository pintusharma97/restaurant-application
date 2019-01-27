package pintu_sharma97.com.mrchef;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class TabItemData implements Serializable {
    @SerializedName("ItemName")
    ArrayList<TabItem> tabItems;

    public ArrayList<TabItem> getTabItems() {
        return tabItems;
    }
}
