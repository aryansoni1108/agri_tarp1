package org.tensorflow.demo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.tensorflow.demo.R;
import org.tensorflow.demo.modals.BazaarRates;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BazaarRatesAdapter extends RecyclerView.Adapter<BazaarRatesAdapter.MyViewHolder> {

    private List<BazaarRates> br = new ArrayList<>();
    private Context context;
    public  BazaarRatesAdapter(Context context,List<BazaarRates> br){
        this.context=context;
        this.br=br;

    }
    @Override
    public BazaarRatesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bazaarrateitem,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BazaarRatesAdapter.MyViewHolder holder, int position) {
        holder.variety.setText(br.get(position).getVariety());
        holder.market.setText(br.get(position).getMarket());
        holder.maxprice.setText(br.get(position).getMax_price());
        holder.minprice.setText(br.get(position).getMin_price());
        holder.commo.setText(br.get(position).getCommodity());
        holder.avgprice.setText(br.get(position).getModal_price());
        holder.district.setText(br.get(position).getDistrict());
        holder.state.setText(br.get(position).getState());
        holder.arrivaldate.setText(br.get(position).getArrival_date());
        holder.maxprice.setText(br.get(position).getMax_price());

    }

    @Override
    public int getItemCount() {
        return br.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView minprice,maxprice,avgprice,state,district, commo,variety,market,arrivaldate;
        public MyViewHolder(View itemView) {

            super(itemView);
            minprice = itemView.findViewById(R.id.min_price);
            maxprice=itemView.findViewById(R.id.max_price);
            avgprice=itemView.findViewById(R.id.average_price);
            state=itemView.findViewById(R.id.state1);
            district=itemView.findViewById(R.id.district1);
            variety=itemView.findViewById(R.id.variety1);
            market=itemView.findViewById(R.id.market1);
            commo=itemView.findViewById(R.id.commo);
            arrivaldate=itemView.findViewById(R.id.arrival_date);
        }
    }
}
