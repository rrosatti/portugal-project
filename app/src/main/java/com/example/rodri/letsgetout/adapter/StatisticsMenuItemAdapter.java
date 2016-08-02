package com.example.rodri.letsgetout.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.activity.GraphActivity;
import com.example.rodri.letsgetout.activity.SimulationActivity;
import com.example.rodri.letsgetout.model.StatisticsMenuItem;

import java.util.List;

/**
 * Created by rodri on 7/26/2016.
 */
public class StatisticsMenuItemAdapter extends RecyclerView.Adapter<StatisticsMenuItemAdapter.MyViewHolder> {

    private Activity activity;
    private List<StatisticsMenuItem> menuItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView displayIcon;
        public TextView displayTitle;
        public StatisticsMenuItem currentMenuItem;

        public MyViewHolder(View v) {
            super(v);

            displayIcon = (ImageView) v.findViewById(R.id.imgStatisticsOptionIcon);
            displayTitle = (TextView) v.findViewById(R.id.txtStatisticsOptionTitle);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // create a method to check which item was clicked in order to call the proper intent
                    if (currentMenuItem.getIconId() == R.drawable.ic_simulation) {
                        Intent i = new Intent(activity, SimulationActivity.class);
                        activity.startActivity(i);
                    } else if (currentMenuItem.getIconId() == R.drawable.ic_graph) {
                        Intent i = new Intent(activity, GraphActivity.class);
                        activity.startActivity(i);
                    }
                }
            });
        }
    }

    public StatisticsMenuItemAdapter(Activity activity, List<StatisticsMenuItem> menuItems) {
        this.activity = activity;
        this.menuItems = menuItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, parent, false);

        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final StatisticsMenuItem menuItem = menuItems.get(position);
        holder.currentMenuItem = menuItem;

        holder.displayIcon.setImageResource(menuItem.getIconId());
        holder.displayTitle.setText(menuItem.getTitle());

        /**holder.displayTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}