package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Interface.AppOnClickListener;
import com.example.myapplication.R;
import com.example.myapplication.model.AppItem;
import com.example.myapplication.utils.Utils;
import com.example.myapplication.viewHolder.AppViewHolder;

import java.util.List;

public class AppAdapters extends RecyclerView.Adapter<AppViewHolder> {
    private Context mContext;
    private List<AppItem> appItemList;
    private Utils utils;

    public AppAdapters(Context mContext, List<AppItem> appItemList) {
        this.mContext = mContext;
        this.appItemList = appItemList;
        this.utils = new Utils(mContext);
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_apps, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        AppItem appItem = appItemList.get(position);
        holder.app_name.setText(appItem.getName());
        holder.app_icon.setImageDrawable(appItem.getIcon());

        String pk = appItem.getPackageName();
        if (utils.isLock(pk)) {
            holder.app_checkbox.setChecked(true);
        } else {
            holder.app_checkbox.setChecked(false);
        }

        holder.setListener(new AppOnClickListener() {
            @Override
            public void selectApp(int pos, boolean isChecked) {
                if (isChecked) {
                    utils.lock(pk);
                } else {
                    utils.unLock(pk);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appItemList.size();
    }
}
