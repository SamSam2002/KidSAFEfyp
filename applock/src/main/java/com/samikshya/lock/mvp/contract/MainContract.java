package com.samikshya.lock.mvp.contract;

import android.content.Context;

import com.samikshya.lock.base.BasePresenter;
import com.samikshya.lock.base.BaseView;
import com.samikshya.lock.model.CommLockInfo;

import java.util.List;



public interface MainContract {
    interface View extends BaseView<Presenter> {
        void loadAppInfoSuccess(List<CommLockInfo> list);
    }

    interface Presenter extends BasePresenter {
        void loadAppInfo(Context context, boolean isSort);

        void loadLockAppInfo(Context context);

        void onDestroy();
    }
}
