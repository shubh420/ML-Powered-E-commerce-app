package io.shubh.e_commver1.SellerDashboard.Interactor;


import java.util.ArrayList;
import java.util.List;

import io.shubh.e_commver1.Models.ItemsForSale;

public interface SellerDashboardInteractor {

    interface CallbacksToPresnter {
        void onFinishedCheckingSomething1();

    }

   /* interface SeparateCallbackToPresnterAfterGettingItemsForRclrView {

        void onFinishedGettingItems(boolean callbackResultOfTheCheck);
    }*/

    void init(CallbacksToPresnter mPresenter);


    void checkSomethingInDatabase();


}

