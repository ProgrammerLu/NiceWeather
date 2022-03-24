package com.android.niceweather.ui.place;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.niceweather.MainActivity;
import com.android.niceweather.R;
import com.android.niceweather.logic.dataModel.placeModel.Place;
import com.android.niceweather.viewmodel.PlaceViewModel;

import java.util.List;

public class PlaceFragment extends Fragment {
    public  PlaceViewModel placeViewModel;
    private View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        requireActivity().getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            public void onCreate(LifecycleOwner owner) {
                updateView();
                owner.getLifecycle().removeObserver(this);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_place, container, false);
        return view;
    }

    public void updateView() {
        placeViewModel = new ViewModelProvider(requireActivity(),
                new ViewModelProvider.NewInstanceFactory()).get(PlaceViewModel.class);

        //检查数据库是否已有数据
        if(getActivity() instanceof MainActivity) {
            placeViewModel.getSavedPlace();
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
                if(places != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                    bgImageView.setVisibility(View.GONE);
                    placeViewModel.getPlaceList().clear();
                    placeViewModel.getPlaceList().addAll(places);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "未能查询到任何地点", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
