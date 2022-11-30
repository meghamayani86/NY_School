package com.app.nycschools.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.annotation.NonNull;


import com.app.nycschools.model.DataNYCHighSchools;
import com.app.nycschools.util.Urls;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceTest {
    MockWebServer mockWebServer;

    SchoolApi apiService;
    Gson gson;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        gson = new Gson();
        mockWebServer = new MockWebServer();
        apiService = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(SchoolApi.class);
    }


    @Test
    public void getSchoolApiTest() throws IOException, InterruptedException {
        MockResponse mockResponse = new MockResponse();
        mockWebServer.enqueue(mockResponse.setBody("[]"));
        Response<List<DataNYCHighSchools>> response = apiService.getSchoolList(Urls.API_Token).execute();
        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("/resource/s3k6-pzi2.json?%24%24app_token="+Urls.API_Token, request.getPath());

        assertTrue(responseCheck(response));
    }


    @After
    public void teardown() throws IOException {
        mockWebServer.shutdown();
    }


    public boolean responseCheck(@NonNull Response<List<DataNYCHighSchools>> response) {
        if (response.body() != null) {
            return response.body().isEmpty();
        } else {
            return false;
        }
    }
}
