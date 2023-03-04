package com.example.proyectob_pmdm_t2_kaiscervasquez.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyectob_pmdm_t2_kaiscervasquez.R;


public class MapFragment extends Fragment {

    TextView tvMap;
    public static final String TAG_ID = "ID";
    public static final String TAG_FILTER = "FILTER";
    String filter;


    public MapFragment() {

    }


    public static MapFragment newInstance(String param1) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        tvMap = view.findViewById(R.id.tvMap);
        if (getArguments() != null) {
            filter = getArguments().getString(TAG_FILTER);

        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }
}