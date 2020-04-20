package io.shubh.e_commver1.NewOrderListFrSellerFragment.Presenter;

import io.shubh.e_commver1.NewOrderListFrSellerFragment.Interactor.NewOrderListFrSellerInteractor;
import io.shubh.e_commver1.NewOrderListFrSellerFragment.View.NewOrderListFrSellerView;

public class NewOrderListFrSellerPresenterImplt implements NewOrderListFrSellerPresenter, NewOrderListFrSellerInteractor.CallbacksToPresnter {

    private NewOrderListFrSellerView mView;
    private NewOrderListFrSellerInteractor mInteractor;


    public NewOrderListFrSellerPresenterImplt(NewOrderListFrSellerView mView , NewOrderListFrSellerInteractor mSplashInteractor) {
       this.mView=mView;
       this.mInteractor = mSplashInteractor;

       mInteractor.init(this);



    }


    @Override
    public void LoginRelatedWork() {


        mInteractor.checkSomethingInDatabaseWithArgAsCallbackFunction( new NewOrderListFrSellerInteractor.SeparateCallbackToPresnterForSystemUpdate(){
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
