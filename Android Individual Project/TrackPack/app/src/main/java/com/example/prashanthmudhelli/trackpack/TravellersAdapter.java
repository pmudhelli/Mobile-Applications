package com.example.prashanthmudhelli.trackpack;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TravellersAdapter extends RecyclerView.Adapter<TravellersAdapter.MyViewHolder> {

    private List<Travellers> travellersList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fullName, fromCity, fromDate, toCity, toDate, email, mobile;

        public MyViewHolder(View view) {
            super(view);
            fullName = (TextView) view.findViewById(R.id.fullName);
            fromCity = (TextView) view.findViewById(R.id.fromCity);
            fromDate = (TextView) view.findViewById(R.id.fromDate);
            toCity = (TextView) view.findViewById(R.id.toCity);
            toDate = (TextView) view.findViewById(R.id.toDate);
            email = (TextView) view.findViewById(R.id.email);
            mobile = (TextView) view.findViewById(R.id.mobile);
        }
    }

    public TravellersAdapter(List<Travellers> moviesList) {
        this.travellersList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travellers_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Travellers travellers = travellersList.get(position);
        holder.fullName.setText(travellers.getFullName());
        holder.fromCity.setText(travellers.getFromCity());
        holder.fromDate.setText(travellers.getFromDate());
        holder.toCity.setText(travellers.getToCity());
        holder.toDate.setText(travellers.getToDate());
        holder.email.setText(travellers.getEmail());
        holder.mobile.setText(travellers.getMobile());
    }

    @Override
    public int getItemCount() {
        return travellersList.size();
    }
}