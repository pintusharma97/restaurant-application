package pintu_sharma97.com.mrchef;

import com.google.gson.annotations.SerializedName;

public class SectionList {
    @SerializedName("sectionname")
    String sectionname;

    @SerializedName("sectionid")
    String sectionid;

    public String getSectionname() {
        return sectionname;
    }

    public String getSectionid() {
        return sectionid;
    }
}
