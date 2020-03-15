package io.shubh.e_commver1.CategoryItems.Presenter;

import io.shubh.e_commver1.CategoryItems.Interactor.CategoryItemsInteractor;
import io.shubh.e_commver1.CategoryItems.View.CategoryItemsView;

public class CategoryItemsPresenterImplt implements CategoryItemsPresenter, CategoryItemsInteractor.CallbacksToPresnter {

    private CategoryItemsView categoryItemsView;
    private CategoryItemsInteractor mInteractor;

    String categoryNameOrPath;
    int categoryLevel;


    public CategoryItemsPresenterImplt(CategoryItemsView categoryItemsView , CategoryItemsInteractor mSplashInteractor , String category , int categoryLevel) {
       this.categoryItemsView =categoryItemsView;
       this.mInteractor = mSplashInteractor;
       this.categoryNameOrPath=category;
        this.categoryLevel =categoryLevel;

       mInteractor.init(this);



    }


    @Override
    public void LoginRelatedWork() {


        mInteractor.checkSomethingInDatabaseWithArgAsCallbackFunction( new CategoryItemsInteractor.SeparateCallbackToPresnterForSystemUpdate(){
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
