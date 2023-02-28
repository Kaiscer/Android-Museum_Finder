package com.example.proyectob_pmdm_t2_kaiscervasquez.fragments;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;


import com.example.proyectob_pmdm_t2_kaiscervasquez.DetailsActivity;
import com.example.proyectob_pmdm_t2_kaiscervasquez.R;
import com.example.proyectob_pmdm_t2_kaiscervasquez.recycleutil.ListAdapter;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitdata.APIWebService;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitdata.RetrofitClient;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitutils.Graph;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitutils.Museum;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConsultFragment extends Fragment implements View.OnClickListener {

    public static final String KEY_MUSEUM = "MUSEUM_SEL";

    RecyclerView rvMuseum;
    ListAdapter adapter;
    RecyclerView.LayoutManager llm;
    ArrayList<Graph> listMuseum = new ArrayList<>();



    public ConsultFragment() {

    }

    public static ConsultFragment newInstance(String filter) {
        ConsultFragment fragment = new ConsultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // Elimino el onCreate para evitar el el NullPointerException en RecycleView
    // ya que la vista no se ha creado
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_consult, container, false);
         rvMuseum = view.findViewById(R.id.rv_listMuseums);
         configRv(listMuseum);
         getWebServices();
        return view;
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
                public void onResponse(Call<Museum> call, Response<Museum> response) {
                    if (response.isSuccessful()) {
                        Museum museum = response.body();
                        uploadData(museum);
                    }
                }

                @Override
                public void onFailure(Call<Museum> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        }

    }

    private void uploadData(Museum museum) {
        listMuseum = (ArrayList<Graph>) museum.getGraph();
       adapter = new ListAdapter(listMuseum);
        rvMuseum.setAdapter(adapter);
    }

    private void configRv(ArrayList<Graph> graph) {
        llm = new LinearLayoutManager(getActivity());
        adapter = new ListAdapter((ArrayList<Graph>) graph);
        rvMuseum.setHasFixedSize(true);
        rvMuseum.setLayoutManager(llm);
        rvMuseum.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        int pos = rvMuseum.getChildLayoutPosition(v);
        Graph graph = listMuseum.get(pos);

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(KEY_MUSEUM, (CharSequence) graph);
        startActivity(intent);


    }
}