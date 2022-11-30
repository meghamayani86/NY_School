package com.app.nycschools.di;

import com.app.nycschools.network.SchoolApi;
import com.app.nycschools.repository.SchoolRepository;
import com.app.nycschools.util.Urls;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn({SingletonComponent.class})
public class AppModule {

    // Provide dependency of client
    @Singleton
    @Provides
    public OkHttpClient provideOtherInterceptorOkHttpClient(
    ) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .build();
                    return chain.proceed(request);
                })
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    // Provide dependency of retrofit object
    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit
                .Builder()
                .baseUrl(Urls.BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // Provide dependency of school api object
    @Provides
    @Singleton
    public SchoolApi provideSchoolApiService(Retrofit retrofit) {
        return retrofit.create(SchoolApi.class);
    }

    // Provide dependency of repository object
    @Provides
    @Singleton
    public SchoolRepository provideSchoolRepository(SchoolApi schoolApi) {
        return new SchoolRepository(schoolApi);

    }


}
