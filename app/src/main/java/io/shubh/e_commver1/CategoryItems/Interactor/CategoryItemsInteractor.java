package io.shubh.e_commver1.CategoryItems.Interactor;


import java.util.List;

import io.shubh.e_commver1.Models.ItemsForSale;

public interface CategoryItemsInteractor {

    interface CallbacksToPresnter {
        void onFinishedCheckingSomething1();

        void onFinishedGettingItems(List<ItemsForSale> itemList, Boolean listNotEmpty);

    }
   /* interface SeparateCallbackToPresnterAfterGettingItemsForRclrView {

        void onFinishedGettingItems(boolean callbackResultOfTheCheck);
    }*/

    void init(CallbacksToPresnter mPresenter);


    void checkSomethingInDatabase();

    void getTheFirstItemDocumentAsAReferenceForStartAtFunct(String ctgr , String  ctgrPath);

    void getItemsFromFirebaseWithResultsOnSeparateCallback( String ctgr, String  ctgrPath);
}

