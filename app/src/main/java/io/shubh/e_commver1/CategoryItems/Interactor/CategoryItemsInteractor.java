package io.shubh.e_commver1.CategoryItems.Interactor;


public interface CategoryItemsInteractor {

    interface CallbacksToPresnter {
        void onFinishedCheckingSomething1();

        void onFinishedCheckingSomething2();

    }
    interface SeparateCallbackToPresnterAfterGettingItemsForRclrView {

        void onFinishedGettingItems(boolean callbackResultOfTheCheck);
    }

    void init(CallbacksToPresnter mPresenter);


    void checkSomethingInDatabase();

    void getItemsFromFirebaseWithArgAsCallbackFunction(SeparateCallbackToPresnterAfterGettingItemsForRclrView l , String  ctgrPath);
}

