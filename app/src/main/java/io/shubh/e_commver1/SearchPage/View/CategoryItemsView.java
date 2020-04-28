package io.shubh.e_commver1.SearchPage.View;

import android.content.Context;

import java.util.List;

import io.shubh.e_commver1.Models.ItemsForSale;

public interface CategoryItemsView {

    void switchActivity(int i);

    Context getContext(boolean getActvityContext);

    void showProgressBar(boolean b);


    void ShowSnackBarWithAction(String msg, String actionName);

    void showToast(String msg);

    void onGettingCtgrItemsFromPrsntr(List<ItemsForSale> itemList, Boolean listNotEmpty, String ctgrName, boolean ifItsALoadMoreCallResult);

    void onNoItemsFoundResult(String ctgrName, boolean ifItsALoadMoreCallResult);


    // void onCategoryButtonsClicked(int levelOfCategory , String name);

}
