package io.shubh.e_commver1.NewOrderListFrSellerFragment.View;

import android.content.Context;

public interface NewOrderListFrSellerView {

    void switchActivity(int i);

    Context getContext(boolean getActvityContext);

    void showProgressBar(boolean b);


    void ShowSnackBarWithAction(String msg, String actionName);

    void showToast(String msg);
}
