package com.example.myapplication.model;

import android.graphics.drawable.Drawable;

public class AppItem {
    private Drawable icon;
    private String name;
    private String packageName;
    private boolean isLocked;
    public AppItem(Drawable icon, String name, String packageName) {
        this.icon = icon;
        this.name = name;
        this.packageName = packageName;
        this.isLocked = false;
    }


    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void isLocked(boolean b) {
        this.isLocked = isLocked;
    }
    public void setChecked(boolean checked) { // setter method for isChecked
        isLocked = isLocked;
    }
}
