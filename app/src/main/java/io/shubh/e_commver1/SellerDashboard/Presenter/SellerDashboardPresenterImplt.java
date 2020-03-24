package io.shubh.e_commver1.SellerDashboard.Presenter;

import java.util.List;

import io.shubh.e_commver1.Models.ItemsForSale;
import io.shubh.e_commver1.SellerDashboard.Interactor.SellerDashboardInteractor;
import io.shubh.e_commver1.SellerDashboard.View.SellerDashboardView;

public class SellerDashboardPresenterImplt implements SellerDashboardPresenter, SellerDashboardInteractor.CallbacksToPresnter {

    private SellerDashboardView categoryItemsView;
    private SellerDashboardInteractor mInteractor;

    public SellerDashboardPresenterImplt(SellerDashboardView categoryItemsView , SellerDashboardInteractor mSplashInteractor ) {
       this.categoryItemsView =categoryItemsView;
       this.mInteractor = mSplashInteractor;


       mInteractor.init(this);



    }


    @Override
    public void LoginRelatedWork() {


       /* mInteractor.getItemsFromFirebaseWithResultsOnSeparateCallback(new CategoryItemsInteractor.SeparateCallbackToPresnterAfterGettingItemsForRclrView(){
            @Override
            public void onFinishedGettingItems(boolean callbackResultOfTheCheck) {

              if(callbackResultOfTheCheck==true){
                  //system upadte available ..so throw a dialog asking to download update
              }else{
                  //system upadte not available ..so continue
              }
            }
        });*/
    }



    @Override
    public void onFinishedCheckingSomething1() {
//this is call from interactor
    }



}
