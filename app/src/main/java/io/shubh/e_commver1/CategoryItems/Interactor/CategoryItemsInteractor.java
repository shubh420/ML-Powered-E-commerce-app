package io.shubh.e_commver1.CategoryItems.Interactor;


import java.util.ArrayList;
import java.util.List;

import io.shubh.e_commver1.Models.ItemsForSale;

public interface CategoryItemsInteractor {

    interface CallbacksToPresnter {
        void onFinishedCheckingSomething1();

        void onFinishedGettingItems(List<ItemsForSale> itemList, Boolean listNotEmpty, String ctgrName ,boolean ifItsALoadMoreCallResult);

        void showToast(String no_more_items_found);
    }
   /* interface SeparateCallbackToPresnterAfterGettingItemsForRclrView {

        void onFinishedGettingItems(boolean callbackResultOfTheCheck);
    }*/

    void init(CallbacksToPresnter mPresenter);


    void checkSomethingInDatabase();

    void getTheFirstItemDocumentAsAReferenceForStartAtFunct(String ctgr, String ctgrPath,String rootCtgr, String subCtgr, String subSubCtgr, boolean ifItsALoadMorecall);

    void getItemsFromFirebaseWithResultsOnSeparateCallback(String ctgr, String ctgrPath,String rootCtgr, String subCtgr, String subSubCtgr, boolean ifItsALoadMorecall, ArrayList<ItemsForSale> itemsList);
}

