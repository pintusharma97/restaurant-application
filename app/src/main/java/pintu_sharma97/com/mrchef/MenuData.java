package pintu_sharma97.com.mrchef;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MenuData implements Serializable {
    @SerializedName("items")
    ArrayList<MenuItems> menuItems;

    public ArrayList<MenuItems> getMenuItems() {
        return menuItems;
    }
}
