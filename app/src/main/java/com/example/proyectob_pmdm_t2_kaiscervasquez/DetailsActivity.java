package com.example.proyectob_pmdm_t2_kaiscervasquez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectob_pmdm_t2_kaiscervasquez.fragments.ConsultFragment;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitdata.APIWebService;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitdata.RetrofitClient;

import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitutils.Graph;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitutils.Museum;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsActivity extends AppCompatActivity {

    TextView tvName, tvDistrict, tvZone,
            tvDirection, tvDescription, tvTime;

    ArrayList<Graph> listMuseum = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        references();
        String idMuseum = getIntent().getStringExtra(ConsultFragment.TAG_ID);

        getData(idMuseum);
    }

    private void getData(String idMuseum) {
        Retrofit retrofit = RetrofitClient.getClient(APIWebService.BASE_URL);
        APIWebService apiWebService = retrofit.create(APIWebService.class);
        if (idMuseum != null) {
            Call<Museum> call = apiWebService.getMuseumDetails(idMuseum);
            call.enqueue(new retrofit2.Callback<Museum>() {
                @Override
                public void onResponse(@NonNull Call<Museum> call, @NonNull Response<Museum> response) {
                    if (response.isSuccessful()) {
                        Museum museum = response.body();
                        upLoadData(museum);
                    }else {
                        Toast.makeText(DetailsActivity.this, "Error " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Museum> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        }


    }

    private void upLoadData(Museum museum) {
        tvName.setText(museum.getGraph().get(0).getTitle());
        String district = museum.getGraph().get(0).getAddress().getLocality();
        String editDistrict = district.substring(district.lastIndexOf("/")+1);
        tvDistrict.setText(editDistrict);
        String zone = museum.getGraph().get(0).getAddress().getArea().getId();
        String editZone = zone.substring(zone.lastIndexOf("/")+1);
        tvZone.setText(editZone);
        tvDirection.setText(museum.getGraph().get(0).getAddress().getStreetAddress() + ", " +
                museum.getGraph().get(0).getAddress().getPostalCode()
                + ", " + museum.getGraph().get(0).getAddress().getLocality());
        tvDescription.setText(museum.getGraph().get(0).getOrganization().getOrganizationDesc());
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