package com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitdata;


import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitutils.Museum;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIWebService {

    public static final String API_KEY = "201132-0-museos.json";
    public static final String BASE_URL = "https://datos.madrid.es/egob/catalogo/";



    @GET("{API_KEY}")
    Call<Museum> getAllMuseums(@Path("API_KEY") String API_KEY);
    /*
     @Path("API_KEY"): el texto {API_KEY} pasará a ser el valor de la variable user
      cuando se realice la petición.
     */



    @GET("{API_KEY}")
    Call<Museum> getMuseumByDistrict(@Path("API_KEY") String key,
                                    @Query("distrito_nombre") String districtName);
    /*
    @Query(""): con el valor de la variable var1 se formará la cadena
    Query string de la URL de la petición.
     */

    @GET("tipo/entidadesyorganismos/{id}")
    Call<Museum> getMuseumDetails(@Path("id") String idMuseum);




}
