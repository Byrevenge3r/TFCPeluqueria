package com.dam.peluqueriacanina.retrofit;

import com.dam.peluqueriacanina.model.NombreDirVet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRestService {

    public static  final String BASE_URL =  "http://myjson.dit.upm.es/api/bins/ei4f/";

    @GET("veterinarias")
    Call<ArrayList<NombreDirVet>> obtenerVeterinarias();
}
