package io.shubh.e_commver1.DummyMVPclasses.View;

import android.content.Context;

public interface View {

    void switchActivity(int i);

    Context getContext();

    void showProgressBar(boolean b);


    void ShowSnackBarWithAction(String msg, String actionName);

    void showToast(String msg);
}
