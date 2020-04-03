package io.shubh.e_commver1.ItemDetailPage.Interactor;


public interface ItemDetailInteractor {

    interface CallbacksToPresnter {
        void onFinishedCheckingSomething1();

        void onFinishedCheckingSomething2();

    }
    interface SeparateCallbackToPresnterForSystemUpdate {

        void onFinishedCheckingSystemUpdate(boolean callbackResultOfTheCheck);
    }

    void init(CallbacksToPresnter mPresenter);


    void checkSomethingInDatabase();

    void checkSomethingInDatabaseWithArgAsCallbackFunction(ItemDetailInteractor.SeparateCallbackToPresnterForSystemUpdate l);
}

