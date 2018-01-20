package com.navigate.reminderlazier.models;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.List;

import com.navigate.reminderlazier.R;

/**
 * Created by BaLinh on 1/20/2018.
 */

public class TimesAdapter extends RecyclerView.Adapter<TimesAdapter.MyViewHolder>{
    private Context mContext;
    private List<Time> timeList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Time time = timeList.get(position);
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

        holder.timecount.setText(sf.format(time.getTime()));
        holder.creator.setText(time.getCreator());
        holder.location.setText(time.getLocation());
        holder.reminderName.setText(time.getReminderName());

    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView timecount,location,reminderName,creator;

        public MyViewHolder(View view) {
            super(view);
            timecount = (TextView) view.findViewById(R.id.timecount);
            location = (TextView) view.findViewById(R.id.edtLocation);
            reminderName = (TextView) view.findViewById(R.id.edtReminderName);
            creator = (TextView) view.findViewById(R.id.creator);
        }
    }

    public TimesAdapter(Context mContext, List<Time> timeList) {
        this.mContext = mContext;
        this.timeList = timeList;
    }
}
