package com.android.niceweather.ui.place;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.android.niceweather.R;
import com.android.niceweather.logic.dataModel.placeModel.Place;
import com.android.niceweather.repository.Repository;
import com.android.niceweather.ui.weather.WeatherActivity;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder>{
    private final PlaceFragment fragment;
    private final List<Place> placeList;

    public PlaceAdapter(PlaceFragment fragment, List<Place> placeList) {
        this.fragment = fragment;
        this.placeList = placeList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView placeName;
        TextView placeAddress;

        public ViewHolder(@NonNull View view) {
            super(view);
            placeAddress = view.findViewById(R.id.placeAddress);
            placeName = view.findViewById(R.id.placeName);
        }
    }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getBindingAdapterPosition();
            Place place = placeList.get(position);
            FragmentActivity activity = fragment.getActivity();
            if(activity instanceof WeatherActivity) {
                DrawerLayout drawerLayout = activity.findViewById(R.id.drawerLayout);
                drawerLayout.closeDrawers();
                ((WeatherActivity) activity).weatherViewModel.setPlaceName(place.getName());
                ((WeatherActivity) activity).weatherViewModel.setLocationLng(place.getLocation().getLng());
                ((WeatherActivity) activity).weatherViewModel.setLocationLat(place.getLocation().getLat());
                ((WeatherActivity) activity).searchWeather();
            } else {
                Intent intent = new Intent(parent.getContext(), WeatherActivity.class);
                intent.putExtra("location_lng", place.getLocation().getLng());
                intent.putExtra("location_lat", place.getLocation().getLat());
                intent.putExtra("place_name", place.getName());
                fragment.startActivity(intent);
                if(fragment.getActivity() != null)
                    fragment.getActivity().finish();
            }

            //缓存地址
            fragment.placeViewModel.savePlace(place);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.placeName.setText(place.getName());
        holder.placeAddress.setText(place.getAddress());
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

}
