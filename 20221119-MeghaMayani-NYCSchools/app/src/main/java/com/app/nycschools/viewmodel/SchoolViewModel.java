package com.app.nycschools.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.nycschools.model.DataNYCHighSchools;
import com.app.nycschools.model.DataNYCSchoolSat;
import com.app.nycschools.repository.SchoolRepository;
import com.app.nycschools.util.Resource;
import com.app.nycschools.util.Urls;

import java.util.List;
import java.util.Objects;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SchoolViewModel extends ViewModel {
    @AssistedFactory
    public interface ViewViewModelFactory {
        SchoolViewModel create(@Assisted String dbn);
    }

    private MutableLiveData _schoolList;


    public LiveData getSchoolLiveData() {
        if (_schoolList == null) {
            _schoolList = new MutableLiveData();
            getSchoolList();
        }
        return _schoolList;
    }

    private final MutableLiveData _schoolSat = new MutableLiveData();
    public LiveData schoolSat = _schoolSat;

    private final SchoolRepository repository;

    @AssistedInject
    public SchoolViewModel(SchoolRepository repository, @Assisted String dbn) {
        this.repository = repository;
        if (!dbn.isEmpty()) {
            getSchoolSat(dbn);
        }

    }

    public void getSchoolList() {
        _schoolList.postValue(Resource.loading());
        repository.getSchoolList(Urls.API_Token).enqueue(new Callback<List<DataNYCHighSchools>>() {
            @Override
            public void onResponse(@NonNull Call<List<DataNYCHighSchools>> call, @NonNull Response<List<DataNYCHighSchools>> response) {
                if (response.isSuccessful() && !Objects.requireNonNull(response.body()).isEmpty()) {
                    _schoolList.postValue(Resource.success(response.body()));
                } else {
                    assert response.body() != null;
                    _schoolList.postValue(Resource.error(null, ""));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DataNYCHighSchools>> call, @NonNull Throwable t) {
                _schoolList.postValue(Resource.error(t.getMessage(), null));
            }
        });

    }

    public void getSchoolSat(String dbn) {
        _schoolSat.postValue(Resource.loading());
        repository.getSchoolSat(dbn, Urls.API_Token).enqueue(new Callback<List<DataNYCSchoolSat>>() {
            @Override
            public void onResponse(@NonNull Call<List<DataNYCSchoolSat>> call, @NonNull Response<List<DataNYCSchoolSat>> response) {
                if (response.isSuccessful() && !Objects.requireNonNull(response.body()).isEmpty()) {
                    _schoolSat.postValue(Resource.success(response.body()));
                } else {
                    assert response.body() != null;
                    _schoolSat.postValue(Resource.error(null, ""));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DataNYCSchoolSat>> call, @NonNull Throwable t) {
                _schoolSat.postValue(Resource.error(t.getMessage(), null));
            }
        });
    }

}