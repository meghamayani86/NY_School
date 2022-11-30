package com.app.nycschools.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nycschools.BR;
import com.app.nycschools.R;
import com.app.nycschools.databinding.LayoutSchoolItemBinding;
import com.app.nycschools.model.DataNYCHighSchools;
import com.app.nycschools.util.Urls;

import java.util.List;

public class SchoolListAdapter extends RecyclerView.Adapter<SchoolListAdapter.ViewHolder> {

    private final List<DataNYCHighSchools> dataModelList;

    public SchoolListAdapter(List<DataNYCHighSchools> dataModelList) {
        this.dataModelList = dataModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSchoolItemBinding binding = LayoutSchoolItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataNYCHighSchools dataModel = dataModelList.get(position);
        holder.bind(dataModel);

        holder.itemView.setOnClickListener(v -> {

            // Navigate next fragment using navigation component
            Bundle bundle = new Bundle();
            bundle.putString(Urls.DBN, dataModel.getDbn());
            Navigation.findNavController(holder.itemView).navigate(
                    R.id.action_schoolListFragment_to_schoolSatFragment,
                    bundle
            );

        });
    }


    @Override
    public int getItemCount() {
        return dataModelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LayoutSchoolItemBinding itemRowBinding;

        public ViewHolder(@NonNull LayoutSchoolItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            // Bind data to view using data binding
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }


}