package com.app.nycschools.repository;

import com.app.nycschools.model.DataNYCHighSchools;
import com.app.nycschools.model.DataNYCSchoolSat;
import com.app.nycschools.network.SchoolApi;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class SchoolRepository {

    SchoolApi schoolApiService;

    @Inject
    public SchoolRepository(SchoolApi schoolApiService) {
        this.schoolApiService = schoolApiService;
    }

    public Call<List<DataNYCHighSchools>> getSchoolList(String app_token) {
        return schoolApiService.getSchoolList(app_token);
    }

    public Call<List<DataNYCSchoolSat>> getSchoolSat(String dbn, String app_token) {
        return schoolApiService.getSchoolSat(dbn, app_token);
    }
}


