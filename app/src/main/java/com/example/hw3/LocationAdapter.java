package com.example.hw3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>{
    // create the basic adapter extending from RecyclerView.Adapter
    // create an inner/helper class that specify the custom ViewHolder,
    // which gives us access to our views

    // list of the locations to be populated, done with an instance variable
    private List<Location> locations;

    // pass this list into the constructor of the adapter
    public LocationAdapter(List<Location> locations) { // I don't know if the imageListener parameter is needed, but I assume it does
        this.locations = locations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // used to inflate a layout from xml and return the ViewHolder
        // standard template code to inflate layout
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // inflate the custom layout
        View locationView = inflater.inflate(R.layout.item_location, parent, false);
        // return the ViewHolder
        ViewHolder viewHolder = new ViewHolder(locationView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // populate date into the item through holder

        // grab the location date data (i.e location List)
        Location location = locations.get(position);

        // set the view based on the data and the view names
        holder.textView_locationName.setText(location.getName());
        holder.textView_locationType.setText(location.getType());
        holder.textView_locationDimension.setText(location.getDimension());
    }

    @Override
    public int getItemCount() {
        // return the total number of items in the list
        return locations.size();
    }


    // provide a direct reference to each of the views within the data item
    // used to cache the views within the item layout for fast access

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // all the views that should be set as the row is rendered
        // location name, location type, location dimension
        TextView textView_locationName;
        TextView textView_locationType;
        TextView textView_locationDimension;

        // create constructor to set these views
        public ViewHolder(View itemView) {
            // itemView -> represents the entire view of each row
            super(itemView);
            // look up each views from the custom layout
            textView_locationName = itemView.findViewById(R.id.textView_locationName);
            textView_locationType = itemView.findViewById(R.id.textView_locationType);
            textView_locationDimension = itemView.findViewById(R.id.textView_locationDimension);
        }


        @Override
        public void onClick(View view) {

        }
    }
}
