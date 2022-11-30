package com.app.nycschools.network;

import com.app.nycschools.model.DataNYCHighSchools;
import com.app.nycschools.model.DataNYCSchoolSat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SchoolApi {

    // Api for get school list
    @GET("resource/s3k6-pzi2.json")
    Call<List<DataNYCHighSchools>> getSchoolList(@Query("$$app_token") String app_token);

    // Api for get sat for school
    @GET("resource/f9bf-2cp4.json")
    Call<List<DataNYCSchoolSat>> getSchoolSat(@Query("dbn") String dbn, @Query("$$app_token") String app_token);
}
