package io.shubh.e_commver1.NewOrderListFrSellerFragment.Interactor;


public interface NewOrderListFrSellerInteractor {

    interface CallbacksToPresnter {
        void onFinishedCheckingSomething1();

        void onFinishedCheckingSomething2();

    }
    interface SeparateCallbackToPresnterForSystemUpdate {

        void onFinishedCheckingSystemUpdate(boolean callbackResultOfTheCheck);
    }

    void init(CallbacksToPresnter mPresenter);


    void checkSomethingInDatabase();

    void checkSomethingInDatabaseWithArgAsCallbackFunction(NewOrderListFrSellerInteractor.SeparateCallbackToPresnterForSystemUpdate l);
}

