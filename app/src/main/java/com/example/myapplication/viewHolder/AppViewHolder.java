package com.example.myapplication.viewHolder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Interface.AppOnClickListener;
import com.example.myapplication.R;

public class AppViewHolder extends RecyclerView.ViewHolder {
    public ImageView app_icon;
    public TextView app_name;
    public CheckBox app_checkbox;

    private AppOnClickListener listener;

    public AppViewHolder(@NonNull View itemView) {
        super(itemView);

        app_icon = itemView.findViewById(R.id.app_icon);
        app_name = itemView.findViewById(R.id.app_name);
        app_checkbox = itemView.findViewById(R.id.checkbox);

        app_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    listener.selectApp(getAdapterPosition(), isChecked);
                }
            }
        });
    }

    public void setListener(AppOnClickListener listener) {
        this.listener = listener;
    }
}
