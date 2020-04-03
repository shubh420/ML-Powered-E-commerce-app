package io.shubh.e_commver1.ItemDetailPage.View;

import android.content.Context;

public interface ItemDetailView {

    void switchActivity(int i);

    Context getContext(boolean getActvityContext);

    void showProgressBar(boolean b);


    void ShowSnackBarWithAction(String msg, String actionName);

    void showToast(String msg);
}
