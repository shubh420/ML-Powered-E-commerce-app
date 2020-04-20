package io.shubh.e_commver1.SellerDashboard.Interactor;


import java.util.ArrayList;

import io.shubh.e_commver1.Models.Order;

public interface SellerDashboardInteractor {



    interface CallbacksToPresnter {
        void onFinishedCheckingSomething1();

    }

    interface SeparateCallbackToPresnterAfterGettingSellerData {

        void onFinished(boolean callbackResultOfTheCheck, ArrayList<Order.SubOrderItem> subOrderItems);
    }

    void init(CallbacksToPresnter mPresenter);


    //void checkSomethingInDatabase();

    void getSellerDataWithArgAsCallback(SeparateCallbackToPresnterAfterGettingSellerData l);

}

