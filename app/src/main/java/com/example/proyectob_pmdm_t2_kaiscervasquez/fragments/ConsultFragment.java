package com.example.proyectob_pmdm_t2_kaiscervasquez.fragments;

import static androidx.core.content.ContextCompat.getSystemService;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectob_pmdm_t2_kaiscervasquez.R;
import com.example.proyectob_pmdm_t2_kaiscervasquez.recycleutil.ListAdapter;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitdata.APIWebService;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitdata.RetrofitClient;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitutils.Graph;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitutils.Museum;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ConsultFragment extends Fragment implements View.OnClickListener {


    RecyclerView rv;
    ListAdapter adapter;
    RecyclerView.LayoutManager llm;
    ArrayList<Graph> listMuseum;



    public ConsultFragment() {

    }

    public static ConsultFragment newInstance(String filter) {
        ConsultFragment fragment = new ConsultFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rv = getView().findViewById(R.id.rv_listMuseums);
        //TODO: Solucionar error del recycle no se rompe al intentar configurarlo
        configRv(listMuseum);
        getWebServices();



    }
    private boolean isNetworkAvailable() {
        boolean isAvailable = false;
        //Gestor de conectividad
        ConnectivityManager manager = (ConnectivityManager) getActivity()
                .getSystemService(getActivity().CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void getWebServices() {
        if (isNetworkAvailable()) {
            Retrofit retrofit = RetrofitClient.getClient(APIWebService.BASE_URL_ALL);
            APIWebService service = retrofit.create(APIWebService.class);
            Call<Museum> call = service.getMuseums(APIWebService.API_KEY);
            call.enqueue(new retrofit2.Callback<Museum>() {
                @Override
                public void onResponse(Call<Museum> call, retrofit2.Response<Museum> response) {
                    if (response.isSuccessful()) {
                        Museum museum = response.body();
                        configRv(museum.getGraph());
                    }
                }

                @Override
                public void onFailure(Call<Museum> call, Throwable t) {

                }
            });
        }

    }

    private void configRv(List<Graph> graph) {
        llm = new LinearLayoutManager(this.getContext());
        adapter = new ListAdapter((ArrayList<Graph>) graph);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return null;
    }

    @Override
    public void onClick(View v) {

    }
}