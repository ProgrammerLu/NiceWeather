package com.android.niceweather.ui.place;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.niceweather.MainActivity;
import com.android.niceweather.R;
import com.android.niceweather.logic.dataModel.placeModel.Place;
import com.android.niceweather.viewmodel.PlaceViewModel;

import java.util.List;

public class PlaceFragment extends Fragment {
    private View view;
    public  PlaceViewModel placeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_place, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placeViewModel = new ViewModelProvider(requireActivity(),
                new ViewModelProvider.NewInstanceFactory()).get(PlaceViewModel.class);

        //检查数据库是否已有数据
        if(getActivity() instanceof MainActivity) {
            placeViewModel.isSavedPlace();
        }

        View bgImageView = view.findViewById(R.id.bgImageView);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        PlaceAdapter adapter = new PlaceAdapter(this, placeViewModel.getPlaceList());
        recyclerView.setAdapter(adapter);

        EditText searchPlaceEdit = view.findViewById(R.id.searchPlaceEdit);
        searchPlaceEdit.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString();
                if(query.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    bgImageView.setVisibility(View.VISIBLE);
                    placeViewModel.getPlaceList().clear();
                    adapter.notifyDataSetChanged();
                } else {
                    placeViewModel.searchPlace(query);
                }
            }
        });
        placeViewModel.getPlaceLiveData().observe(requireActivity(), new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                recyclerView.setVisibility(View.VISIBLE);
                bgImageView.setVisibility(View.GONE);
                placeViewModel.getPlaceList().clear();
                placeViewModel.getPlaceList().addAll(places);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
