package com.app.nycschools.viewmodel;


import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.app.nycschools.model.DataNYCHighSchools;
import com.app.nycschools.network.SchoolApi;
import com.app.nycschools.repository.SchoolRepository;
import com.app.nycschools.util.Resource;
import com.app.nycschools.util.Urls;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@RunWith(MockitoJUnitRunner.class)
public class SchoolViewModelNewTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();

    @Mock
    Observer<Resource<List<DataNYCHighSchools>>> observer;

    private SchoolViewModel schoolViewModel;
    Application application;

    @Mock
    private SchoolApi schoolApi;


    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        application = Mockito.mock(Application.class);

        schoolApi = new Retrofit
                .Builder()
                .baseUrl(Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(SchoolApi.class);
        SchoolRepository schoolRepository = new SchoolRepository(schoolApi);
        schoolViewModel = new SchoolViewModel(schoolRepository, "");

    }

    @Test
    public void getSchoolList() throws IOException {

        Call<List<DataNYCHighSchools>> call = schoolApi.getSchoolList(Urls.API_Token);
        Response<List<DataNYCHighSchools>> response = call.execute();
        Assert.assertTrue(response.isSuccessful());
        schoolViewModel.schoolSat.observeForever(observer);
    }


}

