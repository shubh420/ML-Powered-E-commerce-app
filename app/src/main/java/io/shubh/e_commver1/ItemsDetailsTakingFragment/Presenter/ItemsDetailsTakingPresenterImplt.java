package io.shubh.e_commver1.ItemsDetailsTakingFragment.Presenter;

import io.shubh.e_commver1.ItemsDetailsTakingFragment.Interactor.ItemsDetailsTakingInteractor;
import io.shubh.e_commver1.ItemsDetailsTakingFragment.View.ItemsDetailsTakingView;

public class ItemsDetailsTakingPresenterImplt implements ItemsDetailsTakingPresenter, ItemsDetailsTakingInteractor.CallbacksToPresnter {

    private ItemsDetailsTakingView splashview;
    private ItemsDetailsTakingInteractor mInteractor;


    public ItemsDetailsTakingPresenterImplt(ItemsDetailsTakingView splashview , ItemsDetailsTakingInteractor mSplashInteractor) {
       this.splashview=splashview;
       this.mInteractor = mSplashInteractor;

       mInteractor.init(this);



    }


    @Override
    public void LoginRelatedWork() {


        mInteractor.checkSomethingInDatabaseWithArgAsCallbackFunction( new ItemsDetailsTakingInteractor.SeparateCallbackToPresnterForSystemUpdate(){
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
