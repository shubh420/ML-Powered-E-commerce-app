package io.shubh.e_commver1.CategoryItems.Presenter;

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


    }

    @Override
    public void getItemsFromFirebase(String param1CategoryName, String param2CategoryPath, String rootCtgr, String subCtgr, String subSubCtgr, boolean ifItsALoadMorecall) {

        mInteractor.getTheFirstItemDocumentAsAReferenceForStartAtFunct(param1CategoryName,param2CategoryPath ,rootCtgr,subCtgr,subSubCtgr,ifItsALoadMorecall );

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
