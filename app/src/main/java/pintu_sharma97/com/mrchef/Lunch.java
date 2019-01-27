package pintu_sharma97.com.mrchef;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Lunch extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private Button L_button;
    private Bundle bundle;
    private String category;
    //private TextView counter;
    /*private Integer[] images = {R.drawable.juice,R.drawable.toast,R.drawable.omelette,R.drawable.sandwich,R.drawable.pancake};
    private String[] name = {"Juice","Toast","Omelette","Sandwich","Pancake"};
    private String[] category = {"Indian","Chinese","French","Italian","Thai"};
    private String[] type = {"veg","veg","non veg","veg","veg"};*/
    private Adapterbreak adapter;
    private SharedPreferences sharedPreferencesCounter;
    private TextView counter;
    private int float_counter;

    public Lunch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lunch, container, false);
        recyclerView = view.findViewById(R.id.lunchRecyclerView);
        recyclerView.setHasFixedSize(true);
        L_button = view.findViewById(R.id.buttontabtwo);
        //counter = view.findViewById(R.id.counter_TextView);
        L_button.setOnClickListener(this);
        bundle = new Bundle();
        category = "Lunch";
        sharedPreferencesCounter = getContext().getSharedPreferences("mycounter",Context.MODE_PRIVATE);
        float_counter = sharedPreferencesCounter.getInt("counter",0);
        counter = getActivity().findViewById(R.id.counter_TextView);
        counter.setText(""+float_counter);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DataTab> call = apiInterface.getTabTwoCall();
        call.enqueue(new Callback<DataTab>() {
            @Override
            public void onResponse(Call<DataTab> call, Response<DataTab> response) {
                DataTab res = response.body();
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                adapter = new Adapterbreak(getContext(),res.getTabItems(),category,counter);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DataTab> call, Throwable t) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v==L_button){
            Intent intent = new Intent(getContext(),MenuActivity.class);
            bundle.putString("tabname","Lunch");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
