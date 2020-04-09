package io.shubh.e_commver1.AddressSelectionPage.Interactor;


import java.util.List;

import io.shubh.e_commver1.Models.BagItem;

public interface AddressSelectionInteractor {

    interface CallbacksToPresnter {
       /* void onFinishedCheckingSomething1();

        void onFinishedCheckingSomething2();*/

    }

    interface SeparateCallbackToPresnterAfterGettingTheObjectList {

        void onFinished(boolean callbackResultOfTheCheck, List<BagItem> bagItemlist);
    }
    interface SeparateCallbackToPresnterAfterDeletingBagItem {

        void onFinished(boolean callbackResultOfTheCheck);
    }

    void init(CallbacksToPresnter mPresenter);

    void getbagItemsDataWithArgAsCallbackFunction(AddressSelectionInteractor.SeparateCallbackToPresnterAfterGettingTheObjectList l);

    void deletebagItemsWithArgAsCallbackFunction(String docId, SeparateCallbackToPresnterAfterDeletingBagItem l);
}

