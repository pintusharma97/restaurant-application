package pintu_sharma97.com.mrchef;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit retrofit = null;
    public static final String BASE_URL = "http://13.233.147.38:8080/Retrofitdemo/";
    public static Retrofit getApiClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
