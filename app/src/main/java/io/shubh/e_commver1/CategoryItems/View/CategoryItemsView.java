package io.shubh.e_commver1.CategoryItems.View;

import android.content.Context;

public interface CategoryItemsView {

    void switchActivity(int i);

    Context getContext(boolean getActvityContext);

    void showProgressBar(boolean b);


    void ShowSnackBarWithAction(String msg, String actionName);

    void showToast(String msg);

    //level of catgr
    //1->ctgr
    //2->sub-ctgr
    //3->sub-sub-ctgr
    void onCategoryButtonsClicked(int levelOfCategory , String name);

}
