package io.shubh.e_commver1.LikedItems.Presenter;

import io.shubh.e_commver1.LikedItems.Interactor.LikedItemsInteractor;
import io.shubh.e_commver1.LikedItems.View.LikedItemsView;

public class LikedItemsPresenterImplt implements LikedItemsPresenter, LikedItemsInteractor.CallbacksToPresnter {

    private LikedItemsView mView;
    private LikedItemsInteractor mInteractor;


    public LikedItemsPresenterImplt(LikedItemsView mView , LikedItemsInteractor mSplashInteractor) {
       this.mView=mView;
       this.mInteractor = mSplashInteractor;

       mInteractor.init(this);



    }


    @Override
    public void LoginRelatedWork() {


        mInteractor.checkSomethingInDatabaseWithArgAsCallbackFunction( new LikedItemsInteractor.SeparateCallbackToPresnterForSystemUpdate(){
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
