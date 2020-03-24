package io.shubh.e_commver1.ItemsDetailsTakingFragment.Interactor;


public interface ItemsDetailsTakingInteractor {

    interface CallbacksToPresnter {
        void onFinishedCheckingSomething1();

        void onFinishedCheckingSomething2();

    }
    interface SeparateCallbackToPresnterForSystemUpdate {

        void onFinishedCheckingSystemUpdate(boolean callbackResultOfTheCheck);
    }

    void init(CallbacksToPresnter mPresenter);


    void checkSomethingInDatabase();

    void checkSomethingInDatabaseWithArgAsCallbackFunction(ItemsDetailsTakingInteractor.SeparateCallbackToPresnterForSystemUpdate l);
}

