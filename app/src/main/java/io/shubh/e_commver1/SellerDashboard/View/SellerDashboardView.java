package io.shubh.e_commver1.SellerDashboard.View;

import android.content.Context;

import java.util.ArrayList;

import io.shubh.e_commver1.Models.Order;

public interface SellerDashboardView {

    void switchActivity(int i);

    Context getContext(boolean getActvityContext);

    void showProgressBar(boolean b);


    void ShowSnackBarWithAction(String msg, String actionName);

    void showToast(String msg);

    void updateTransactionSummaryTvs(ArrayList<Order.SubOrderItem> subOrderItems, int newOrders, int processed, int returnedOrders);


    // void onCategoryButtonsClicked(int levelOfCategory , String name);

}
