package com.example.proyectob_pmdm_t2_kaiscervasquez.fragments;



import android.content.Context;
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
import android.widget.Toast;

import com.example.proyectob_pmdm_t2_kaiscervasquez.DetailsActivity;
import com.example.proyectob_pmdm_t2_kaiscervasquez.R;
import com.example.proyectob_pmdm_t2_kaiscervasquez.recycleutil.ListAdapter;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitdata.APIWebService;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitdata.RetrofitClient;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitutils.Graph;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitutils.Museum;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConsultFragment extends Fragment implements View.OnClickListener {


    public static final String TAG_ID = "ID";
    public static final String TAG_FILTER = "FILTER";
    RecyclerView rvMuseum;
    ListAdapter adapter;
    RecyclerView.LayoutManager llm;
    ArrayList<Graph> listMuseum = new ArrayList<>();
    View.OnClickListener listener;
    String filter;

    public ConsultFragment() {

    }

    public static ConsultFragment newInstance(String param) {
        ConsultFragment fragment = new ConsultFragment();
        Bundle args = new Bundle();
        args.putString(TAG_FILTER, param);
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
         listener = this;
         if (getArguments() != null){
                filter = getArguments().getString(TAG_FILTER);
         }
         configRv();
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
            Retrofit retrofit = RetrofitClient.getClient(APIWebService.BASE_URL);
            APIWebService service = retrofit.create(APIWebService.class);
            Call<Museum> call;
            if (filter.isEmpty()){
                call = service.getAllMuseums(APIWebService.API_KEY);
            }else {
               call = service.getMuseumByDistrict(APIWebService.API_KEY, filter);

            }
            call.enqueue(new retrofit2.Callback<Museum>() {
                @Override
                public void onResponse(Call<Museum> call, Response<Museum> response) {
                    if (response.isSuccessful()) {
                        Museum museum = response.body();
                        uploadData(museum);
                    }else {
                        Toast.makeText(getActivity(), "Error en la respuesta", Toast.LENGTH_SHORT).show();
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
        adapter = new ListAdapter(listMuseum,  this);
        rvMuseum.setAdapter(adapter);
    }

    private void configRv() {
        llm = new LinearLayoutManager(getActivity());
        adapter = new ListAdapter(listMuseum,  this);
        rvMuseum.setHasFixedSize(true);
        rvMuseum.setLayoutManager(llm);
        rvMuseum.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        int pos = rvMuseum.getChildLayoutPosition(v);
        String id = listMuseum.get(pos).getId();
        String editId = id.substring(id.lastIndexOf("/")+1);
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(TAG_ID, editId);
        startActivity(intent);
    }
}