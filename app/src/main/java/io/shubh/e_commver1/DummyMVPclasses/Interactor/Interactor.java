package io.shubh.e_commver1.DummyMVPclasses.Interactor;


public interface Interactor {

    interface CallbacksToPresnter {
        void onFinishedCheckingSomething1();

        void onFinishedCheckingSomething2();

    }
    interface SeparateCallbackToPresnterForSystemUpdate {

        void onFinishedCheckingSystemUpdate(boolean callbackResultOfTheCheck);
    }

    void init(CallbacksToPresnter mPresenter);


    void checkSomethingInDatabase();

    void checkSomethingInDatabaseWithArgAsCallbackFunction(Interactor.SeparateCallbackToPresnterForSystemUpdate l);
}

