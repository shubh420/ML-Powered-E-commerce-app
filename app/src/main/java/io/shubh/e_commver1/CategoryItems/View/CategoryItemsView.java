package io.shubh.e_commver1.CategoryItems.View;

import android.content.Context;

public interface CategoryItemsView {

    void switchActivity(int i);

    Context getContext(boolean getActvityContext);

    void showProgressBar(boolean b);


    void ShowSnackBarWithAction(String msg, String actionName);

    void showToast(String msg);


    //void onCategoryButtonsClicked(int levelOfCategory , String name);

}
