package pintu_sharma97.com.mrchef;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("login.jsp")
    Call<User> login(@Query("id")String id,
                     @Query ("password") String password);

    @GET("CategoryFetch.jsp")
    Call<Categorydata> getCategoryData();

    @GET("CategoryFirst.jsp")
    Call<CategoryFirstData> CategoryFirst(@Query("cateogry_id")int cateogry_id);

    @GET("CategoryFetchFirst.jsp")
    Call<Categorydata> getCategorydatafirst();

    @GET("CategorySecond.jsp")
    Call<MenuData> CategorySecond(@Query("id")int id);

    @GET("ChefTab.jsp")
    Call<DataTab> getTabCall();

    @GET("ChefTabTwo.jsp")
    Call<DataTab> getTabTwoCall();

    @GET("ChefTabThree.jsp")
    Call<DataTab> getTabThreeCall();

    @GET("TabItems.jsp")
    Call<TabItemData> getTabItems(@Query("tabname")String tabname);

    @GET("CuisineSection.jsp")
    Call<SectionData> CuisineSection(@Query("sectionid")int sectionid);

    @GET("SectionData.jsp")
    Call<MenuData> SectionData(@Query("sectionid")String sectionid);

    @GET("OrderCart.jsp")
    Call<User> OrderCart(@Query("tableid")String tableid,
                              @Query("itemname")String itemname,
                              @Query("itemimage")String itemimage,
                              @Query("quantity")int quantity,
                              @Query("price")int price,
                                @Query("category")String category);

    @GET("GetCartList.jsp")
    Call<GetCartData> GetCartList(@Query("tableid")String tableid);

    @GET("OrderStatus.jsp")
    Call<User> OrderStatus(@Query("table_id")String tableid,
                                 @Query("order_status")String status);

    @GET("ConfirmPay.jsp")
    Call<User> ConfirmPay(@Query("table_id")String tableid);

    @GET("removeFromCart.jsp")
    Call<User> removeFromCart(@Query("item")String item);
}
