package io.shubh.e_commver1.Welcome.Interactor;


import android.content.Context;

public interface WelcomeInteractor {

    void push_the_uid_to_users_node(Context context);

    interface CallbacksToPresnter {
        void onFinishedPushingTheUidToUsersNode();

       // void onFinishedCheckingSomething2();

    }
 /*   interface SeparateCallbackToPresnterForSystemUpdate {

        void onFinishedGettingItems(boolean callbackResultOfTheCheck);
    }*/

    void init(CallbacksToPresnter mPresenter);


  /*  void checkSomethingInDatabase();

    void getItemsFromFirebaseWithArgAsCallbackFunction(WelcomeInteractor.SeparateCallbackToPresnterForSystemUpdate l);
*/
}

