package io.shubh.e_commver1.CategoryItems.Presenter;

import io.shubh.e_commver1.CategoryItems.Interactor.CategoryItemsInteractor;
import io.shubh.e_commver1.CategoryItems.View.CategoryItemsView;

public class CategoryItemsPresenterImplt implements CategoryItemsPresenter, CategoryItemsInteractor.CallbacksToPresnter {

    private CategoryItemsView categoryItemsView;
    private CategoryItemsInteractor mInteractor;

    String categoryName;
    String categoryPath;



    public CategoryItemsPresenterImplt(CategoryItemsView categoryItemsView , CategoryItemsInteractor mSplashInteractor , String category , String categoryPath) {
       this.categoryItemsView =categoryItemsView;
       this.mInteractor = mSplashInteractor;
       this.categoryName=category;
        this.categoryPath =categoryPath;

       mInteractor.init(this);



    }


    @Override
    public void LoginRelatedWork() {


       /* mInteractor.getItemsFromFirebaseWithArgAsCallbackFunction(new CategoryItemsInteractor.SeparateCallbackToPresnterAfterGettingItemsForRclrView(){
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
    public void getItemsFromFirebase(String mParam2CategoryPath) {

        mInteractor.getItemsFromFirebaseWithArgAsCallbackFunction(new CategoryItemsInteractor.SeparateCallbackToPresnterAfterGettingItemsForRclrView() {
            @Override
            public void onFinishedGettingItems(boolean callbackResultOfTheCheck) {


            }
        } , mParam2CategoryPath);
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
