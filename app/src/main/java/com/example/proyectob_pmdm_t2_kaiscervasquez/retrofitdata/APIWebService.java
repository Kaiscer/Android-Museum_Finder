package com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitdata;


import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitutils.Museum;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIWebService {

    public static final String API_KEY = "201132-0-museos.json";
    public static final String BASE_URL_ALL = "https://datos.madrid.es/egob/catalogo/";

    @GET("{API_KEY}")
    Call<Museum> getMuseums(@Path("API_KEY") String API_KEY);

}
