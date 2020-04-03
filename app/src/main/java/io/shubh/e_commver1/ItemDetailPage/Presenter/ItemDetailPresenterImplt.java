package io.shubh.e_commver1.ItemDetailPage.Presenter;

import io.shubh.e_commver1.ItemDetailPage.Interactor.ItemDetailInteractor;
import io.shubh.e_commver1.ItemDetailPage.View.ItemDetailView;

public class ItemDetailPresenterImplt implements ItemDetailPresenter, ItemDetailInteractor.CallbacksToPresnter {

    private ItemDetailView splashview;
    private ItemDetailInteractor mInteractor;


    public ItemDetailPresenterImplt(ItemDetailView splashview , ItemDetailInteractor mSplashInteractor) {
       this.splashview=splashview;
       this.mInteractor = mSplashInteractor;

       mInteractor.init(this);



    }


    @Override
    public void LoginRelatedWork() {


        mInteractor.checkSomethingInDatabaseWithArgAsCallbackFunction( new ItemDetailInteractor.SeparateCallbackToPresnterForSystemUpdate(){
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
