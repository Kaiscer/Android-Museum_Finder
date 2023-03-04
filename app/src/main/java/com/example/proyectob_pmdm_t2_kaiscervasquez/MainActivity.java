package com.example.proyectob_pmdm_t2_kaiscervasquez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectob_pmdm_t2_kaiscervasquez.fragments.ConsultFragment;
import com.example.proyectob_pmdm_t2_kaiscervasquez.fragments.Container_Fragment;
import com.example.proyectob_pmdm_t2_kaiscervasquez.fragments.MapFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnFiltersListener  {


    TextView tvFilter;
    Button btnSelectFilter;
    Button btnConsult;
    String filter = "";
    boolean showList = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        references();
        managerFG();

    }

    private void managerFG() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fg_container, new Container_Fragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_list:
                btnConsult.setText("Consultar Listado");
                break;
            case R.id.menu_map:
                btnConsult.setText("Consultar Mapa");
                showList = false;
                return false;

        }
        return super.onOptionsItemSelected(item);
    }

    private void fragmentManager(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fg_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void references() {
        tvFilter = findViewById(R.id.tv_filter);
        tvFilter.setVisibility(View.INVISIBLE);
        btnSelectFilter = findViewById(R.id.btn_selectFilter);
        btnConsult = findViewById(R.id.btn_consult);
        btnSelectFilter.setOnClickListener(this);
        btnConsult.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_selectFilter:
                tvFilter.setText(null);
                showDialogFilters();
                break;
            case R.id.btn_consult:
                if (showList){
                    fragmentManager(ConsultFragment.newInstance(filter));
                }else {
                    fragmentManager(MapFragment.newInstance(filter));
                }
                break;

        }
    }

    private void showDialogFilters() {
        DialogFilter dialogFilters = new DialogFilter();
        dialogFilters.setCancelable(false);
        dialogFilters.show(getSupportFragmentManager(), "DialogFilters");
    }

    @Override
    public void onFilterDistrict(String district) {
        tvFilter.setVisibility(View.VISIBLE);
        tvFilter.setText(district);
        this.filter = district;
        if (tvFilter.getText().toString().isEmpty()){
            filter = null;
        }else {
            filter = district;

        }


    }
}