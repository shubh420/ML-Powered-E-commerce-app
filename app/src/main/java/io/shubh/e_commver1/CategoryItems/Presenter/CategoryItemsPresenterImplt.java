package io.shubh.e_commver1.CategoryItems.Presenter;

import android.util.Log;

import java.util.List;

import io.shubh.e_commver1.CategoryItems.Interactor.CategoryItemsInteractor;
import io.shubh.e_commver1.CategoryItems.View.CategoryItemsView;
import io.shubh.e_commver1.Models.ItemsForSale;

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
    public void getItemsFromFirebase(String mParam1CategoryName, String mParam2CategoryPath, boolean ifItsALoadMorecall) {

        mInteractor.getTheFirstItemDocumentAsAReferenceForStartAtFunct(mParam1CategoryName,mParam2CategoryPath ,ifItsALoadMorecall );

     //   mInteractor.getItemsFromFirebaseWithResultsOnSeparateCallback(mParam1CategoryName , mParam2CategoryPath);
    }


    @Override
    public void onFinishedCheckingSomething1() {
//this is call from interactor
    }

    @Override
    public void onFinishedGettingItems(List<ItemsForSale> itemList, Boolean listNotEmpty, String ctgrName ,boolean ifItsALoadMoreCallResult) {

        if(listNotEmpty==true){

            categoryItemsView.onGettingCtgrItemsFromPrsntr(itemList,listNotEmpty ,ctgrName ,ifItsALoadMoreCallResult);
        }else{
            //tell the fragment to show no results found
            categoryItemsView.showToast("No items Found");

            //tell the fragment to show no results found
            categoryItemsView.onNoItemsFoundResult(ctgrName , ifItsALoadMoreCallResult) ;
        }

    }

    @Override
    public void showToast(String no_more_items_found) {
        categoryItemsView.showToast("No More Items Found");
    }


}
