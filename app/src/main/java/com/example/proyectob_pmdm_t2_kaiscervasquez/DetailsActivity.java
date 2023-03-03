package com.example.proyectob_pmdm_t2_kaiscervasquez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.proyectob_pmdm_t2_kaiscervasquez.fragments.ConsultFragment;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitdata.APIWebService;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitdata.RetrofitClient;

import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitutils.Museum;


import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsActivity extends AppCompatActivity {

    TextView tvName, tvDistrict, tvZone,
            tvDirection, tvDescription, tvTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        references();
        getData(getIntent().getStringExtra(ConsultFragment.TAG_ID));
    }

    private void getData(String idMuseum) {
        Retrofit retrofit = RetrofitClient.getClient(APIWebService.BASE_URL);
        APIWebService apiWebService = retrofit.create(APIWebService.class);
        if (idMuseum != null) {
            Call<Museum> call = apiWebService.getAllMuseums(APIWebService.API_KEY);
            call.enqueue(new retrofit2.Callback<Museum>() {
                @Override
                public void onResponse(@NonNull Call<Museum> call, @NonNull Response<Museum> response) {
                    if (response.isSuccessful()) {
                        Museum museum = response.body();
                        upLoadData(museum);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Museum> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        }else {

        }


    }

    private void upLoadData(Museum museum) {
        tvName.setText(museum.getGraph().get(0).getTitle());
        tvDistrict.setText((CharSequence) museum.getGraph().get(0).getAddress());
        tvZone.setText((CharSequence) museum.getGraph().get(0).getLocation());
        tvDirection.setText(museum.getGraph().get(0).getAddress().getLocality());
        tvDescription.setText((CharSequence) museum.getGraph().get(0).getOrganization());
        tvTime.setText(museum.getGraph().get(0).getOrganization().getSchedule());
    }


    private void references() {
        tvName = findViewById(R.id.tv_name);
        tvDistrict = findViewById(R.id.tv_district);
        tvZone = findViewById(R.id.tv_zone);
        tvDirection = findViewById(R.id.tv_direction);
        tvDescription = findViewById(R.id.tv_description);
        tvTime = findViewById(R.id.tv_time);


    }
}