package com.consultants.week5daily1.ui.firstAct;

import android.content.Context;

import com.consultants.week5daily1.ui.base.BasePresenter;
import com.consultants.week5daily1.ui.base.BaseView;

public interface MainContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

        void setDataToContent(String first, String last, String days, String salary,Context context);
    }
}
