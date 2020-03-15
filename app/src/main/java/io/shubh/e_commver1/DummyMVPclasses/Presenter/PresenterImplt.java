package io.shubh.e_commver1.DummyMVPclasses.Presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import io.shubh.e_commver1.DummyMVPclasses.Interactor.Interactor;

import io.shubh.e_commver1.DummyMVPclasses.View.View;
import io.shubh.e_commver1.StaticClassForGlobalInfo;

public class PresenterImplt implements Presenter, Interactor.CallbacksToPresnter {

    private View splashview;
    private Interactor mInteractor;


    public PresenterImplt(View splashview , Interactor mSplashInteractor) {
       this.splashview=splashview;
       this.mInteractor = mSplashInteractor;

       mInteractor.init(this);



    }


    @Override
    public void LoginRelatedWork() {


        mInteractor.checkSomethingInDatabaseWithArgAsCallbackFunction( new Interactor.SeparateCallbackToPresnterForSystemUpdate(){
            @Override
            public void onFinishedCheckingSystemUpdate(boolean callbackResultOfTheCheck) {

              if(callbackResultOfTheCheck==true){
                  //system upadte available ..so throw a dialog asking to download update
              }else{
                  //system upadte not available ..so continue
              }
            }
        });
    }


    @Override
    public void onFinishedCheckingSomething1() {
//this is call from interactor
    }

    @Override
    public void onFinishedCheckingSomething2() {

        //this is call from interactor
    }
}
