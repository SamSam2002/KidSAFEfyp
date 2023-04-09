package com.samikshya.lock.mvp.contract;

import android.content.Context;

import com.samikshya.lock.base.BasePresenter;
import com.samikshya.lock.base.BaseView;
import com.samikshya.lock.model.CommLockInfo;
import com.samikshya.lock.mvp.p.LockMainPresenter;

import java.util.List;



public interface LockMainContract {
    interface View extends BaseView<Presenter> {

        void loadAppInfoSuccess(List<CommLockInfo> list);
    }

    interface Presenter extends BasePresenter {
        void loadAppInfo(Context context);

        void searchAppInfo(String search, LockMainPresenter.ISearchResultListener listener);

        void onDestroy();
    }
}
