package pintu_sharma97.com.mrchef;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SectionData implements Serializable {
    @SerializedName("section")
    ArrayList<SectionList> sectionLists;

    public ArrayList<SectionList> getSectionLists() {
        return sectionLists;
    }
}
