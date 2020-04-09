package io.shubh.e_commver1.ItemDetailPage.Interactor;


import io.shubh.e_commver1.Models.BagItem;

public interface ItemDetailInteractor {



    interface CallbacksToPresnter {
        void onFinishedCheckingSomething1();

        void onFinishedCheckingSomething2();
    }


    interface SeparateCallbackToPresnterAfterBagItemUpload {

        void onFinishedUploading(boolean callbackResultOfTheUpload);
    }

    void init(CallbacksToPresnter mPresenter);



     void uploadBagItemWithArgAsCallbackFunction(BagItem bagItem , ItemDetailInteractor.SeparateCallbackToPresnterAfterBagItemUpload l);


}

