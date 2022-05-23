package com.dam.peluqueriacanina.retrofit;

import com.dam.peluqueriacanina.model.NombreDirVet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRestService {

    //http://myjson.dit.upm.es/api/bins/92tv/
    public static  final String BASE_URL =  "http://localhost:3000/";

    @GET("veterinarias")
    Call<ArrayList<NombreDirVet>> obtenerVeterinarias();
}
