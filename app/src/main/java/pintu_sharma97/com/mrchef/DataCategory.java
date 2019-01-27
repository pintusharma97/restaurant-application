package pintu_sharma97.com.mrchef;

import com.google.gson.annotations.SerializedName;

public class DataCategory {
    @SerializedName("subcatid")
    int subcatid;

    @SerializedName("subcatname")
    String subcatname;

    @SerializedName("subcatimage")
    String subcatimage;

    public int getSubcatid() {
        return subcatid;
    }

    public String getSubcatname() {
        return subcatname;
    }

    public String getSubcatimage() {
        return subcatimage;
    }
}
