package io.shubh.e_commver1.Welcome.Presenter;

import android.content.Intent;

public interface WelcomePresenter {

  //  void checkIfAlreadyLoggedIn();

    //void checkForSystemUpdates();

   // void onGettingThegoogleSignInResult(int code, int requestCode, Intent data);

     void onClickingNotNowButton();

     void onSignInButtonClicked();

    void onGettingActivityResultOfGoogleSignIn(int requestCode, Intent data);
}
