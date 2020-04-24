package io.shubh.e_commver1.ItemDetailPage.Interactor;


import io.shubh.e_commver1.Models.BagItem;
import io.shubh.e_commver1.Models.LikedItem;

public interface ItemDetailInteractor {



    interface CallbacksToPresnter {
      //      void onFinishedCheckingSomething1();

      //  void onFinishedCheckingSomething2();
    }


    interface SeparateCallbackToPresnterAfterBagItemUpload {

        void onFinishedUploading(boolean callbackResultOfTheUpload);
    }
    interface SeparateCallbackToPresnterAfterSavingItemToBuyerLikedItems {

        void onFinished(boolean callbackResultOfTheCheck);
    }

    interface SeparateCallbackToPresnterAfterDeletingFromBuyerLikedItems {

        void onFinished(boolean callbackResultOfTheCheck);
    }


    void init(CallbacksToPresnter mPresenter);

     void uploadBagItemWithArgAsCallbackFunction(BagItem bagItem , ItemDetailInteractor.SeparateCallbackToPresnterAfterBagItemUpload l);


    void saveItemToUserLikedListWithResultsOnSeparateCallback(LikedItem itemId, SeparateCallbackToPresnterAfterSavingItemToBuyerLikedItems l);

    void deleteItemFromUserLikedListWithResultsOnSeparateCallback(String docId, SeparateCallbackToPresnterAfterDeletingFromBuyerLikedItems l);


}

