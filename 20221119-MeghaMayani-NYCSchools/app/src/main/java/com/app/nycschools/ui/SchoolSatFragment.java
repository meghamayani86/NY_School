package com.app.nycschools.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.app.nycschools.BR;
import com.app.nycschools.R;
import com.app.nycschools.databinding.FragmentSchoolSatBinding;
import com.app.nycschools.model.DataNYCSchoolSat;
import com.app.nycschools.util.Constant;
import com.app.nycschools.util.Resource;
import com.app.nycschools.util.Urls;
import com.app.nycschools.viewmodel.SchoolViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SchoolSatFragment extends Fragment {


    Observer<Resource<List<DataNYCSchoolSat>>> myObserver;
    SchoolViewModel mViewModel;


    String dBn;
    FragmentSchoolSatBinding binding;
    @Inject
    SchoolViewModel.ViewViewModelFactory mViewViewModelFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Received argument from previous fragment
        if (getArguments() != null) {
            dBn = getArguments().getString(Urls.DBN);

        }
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment using view binding
        binding = FragmentSchoolSatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myObserver = this::consumeResponse;

        mViewModel = mViewViewModelFactory.create(dBn);

        // ViewModel updates the Model after observing changes in the View
        // model will also update the view via the ViewModel
        mViewModel.schoolSat.observe(getViewLifecycleOwner(), myObserver);


    }

    // Method to handle response
    private void consumeResponse(Resource<List<DataNYCSchoolSat>> apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.layoutError.setVisibility(View.GONE);
                break;

            case SUCCESS:
                binding.layoutSuccess.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.layoutError.setVisibility(View.GONE);
                renderSuccessResponse(apiResponse.data);
                break;

            case ERROR:
                binding.layoutSuccess.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.layoutError.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }

    // Method to handle success response
    private void renderSuccessResponse(List<DataNYCSchoolSat> response) {

        if (response != null) {

            Log.e("TAG", "renderSuccessResponse: " + response.get(0).getSchool_name());
            binding.setVariable(BR.model, response.get(0));
            binding.executePendingBindings();
        } else {
            Constant.runMeMessage(getContext(), getResources().getString(R.string.errorString));
        }
    }
}