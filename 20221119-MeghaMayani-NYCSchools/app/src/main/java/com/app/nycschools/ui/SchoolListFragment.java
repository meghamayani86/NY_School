package com.app.nycschools.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.nycschools.R;
import com.app.nycschools.adapter.SchoolListAdapter;
import com.app.nycschools.databinding.FragmentSchoolBinding;
import com.app.nycschools.model.DataNYCHighSchools;
import com.app.nycschools.util.Constant;
import com.app.nycschools.util.Resource;
import com.app.nycschools.viewmodel.SchoolViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class SchoolListFragment extends Fragment {


    Observer<Resource<List<DataNYCHighSchools>>> myObserver;
    FragmentSchoolBinding binding;
    SchoolViewModel mViewModel;
    @Inject
    SchoolViewModel.ViewViewModelFactory mViewViewModelFactory;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding
        binding = FragmentSchoolBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Set layout manager to recycle view
        binding.mRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        myObserver = this::consumeResponse;
        mViewModel = mViewViewModelFactory.create("");


        // ViewModel updates the Model after observing changes in the View
        // Model will also update the view via the ViewModel
        mViewModel.getSchoolLiveData().observe(getViewLifecycleOwner(), myObserver);

    }

    // Method to handle response
    private void consumeResponse(@NonNull Resource<List<DataNYCHighSchools>> apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.layoutError.setVisibility(View.GONE);
                break;

            case SUCCESS:
                binding.progressBar.setVisibility(View.GONE);
                binding.layoutError.setVisibility(View.GONE);
                renderSuccessResponse(apiResponse.data);
                break;

            case ERROR:
                binding.progressBar.setVisibility(View.GONE);
                binding.layoutError.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }


    // Method to handle success response
    private void renderSuccessResponse(List<DataNYCHighSchools> response) {
        if (response != null) {

            SchoolListAdapter schoolListAdapter = new SchoolListAdapter(response);
            binding.setSchoolListAdapter(schoolListAdapter);
        } else {
            Constant.runMeMessage(getContext(), getResources().getString(R.string.errorString));
        }
    }


}