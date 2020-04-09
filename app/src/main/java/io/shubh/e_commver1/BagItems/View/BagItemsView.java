package io.shubh.e_commver1.BagItems.View;

import android.content.Context;

import java.util.List;

import io.shubh.e_commver1.Models.BagItem;

public interface BagItemsView {

    void switchActivity(int i);

    Context getContext(boolean getActvityContext);

    void showProgressBar(boolean b);


    void ShowSnackBarWithAction(String msg, String actionName);

    void showToast(String msg);

    void showItemsInRecyclerView(List<BagItem> bagItemlist);

    void updateReclrViewListAfterDeletionOfItem();
}
