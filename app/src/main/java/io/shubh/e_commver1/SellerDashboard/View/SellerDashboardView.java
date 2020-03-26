package io.shubh.e_commver1.SellerDashboard.View;

import android.content.Context;

public interface SellerDashboardView {

    void switchActivity(int i);

    Context getContext(boolean getActvityContext);

    void showProgressBar(boolean b);


    void ShowSnackBarWithAction(String msg, String actionName);

    void showToast(String msg);




    // void onCategoryButtonsClicked(int levelOfCategory , String name);

}