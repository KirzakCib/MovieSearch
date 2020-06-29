package com.example.moviesearch;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ParserJSON {

    @GET("films.json")
    Call<Parser> filmParser();

}
