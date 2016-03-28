package com.refat.restweather.app.retrofit;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.refat.restweather.app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by refat on 16/01/2016.
 */
public class RvAdapter extends RecyclerView.Adapter< RvAdapter.ViewHolder> {

    private Context mContext;
    private List<RetroMainFragment.RvData> rvDataList;

    public RvAdapter(Context context,List<RetroMainFragment.RvData> rvDataList) {
        this.mContext=context;
        rvDataList.remove(0);
        this.rvDataList=rvDataList;



    }

    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(RvAdapter.ViewHolder holder, int position) {

        if(rvDataList.size()>0 ){

            Picasso.with(mContext)
                    .load(rvDataList.get(position).getIcon())
                    .resize(150, 150)
                    .into(holder.iv_icon);
            holder.tv_temp.setText(rvDataList.get(position).getTemp().toString()+ "\u00b0" + "C");
            holder.tv_desc.setText(rvDataList.get(position).getDesc());
            holder.tv_date.setText(rvDataList.get(position).getDate());





        }else{
                Toast.makeText(mContext,"Not enough to show.",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public int getItemCount() {
        //Toast.makeText(mContext, "rvDataListadapter" + String.valueOf(rvDataList.size()), Toast.LENGTH_LONG).show();

        return rvDataList.size();



    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        ImageView iv_icon;
        TextView tv_temp;
        TextView tv_desc;
        TextView tv_date;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            iv_icon=(ImageView)itemView.findViewById(R.id.iv_icon);
            tv_temp=(TextView)itemView.findViewById(R.id.tv_temp);
            tv_desc=(TextView)itemView.findViewById(R.id.tv_desc);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);

        }

        @Override
        public void onClick(View v) {

            Toast.makeText(mContext,"You have clicked me!!!",Toast.LENGTH_LONG).show();


        }


    }
}
