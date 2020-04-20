package io.shubh.e_commver1.ItemDetailPage.Presenter;

import io.shubh.e_commver1.ItemDetailPage.Interactor.ItemDetailInteractor;
import io.shubh.e_commver1.ItemDetailPage.View.ItemDetailView;
import io.shubh.e_commver1.Models.BagItem;
import io.shubh.e_commver1.Models.ItemsForSale;
import io.shubh.e_commver1.Utils.StaticClassForGlobalInfo;

public class ItemDetailPresenterImplt implements ItemDetailPresenter, ItemDetailInteractor.CallbacksToPresnter {

    private ItemDetailView mview;
    private ItemDetailInteractor mInteractor;


    public ItemDetailPresenterImplt(ItemDetailView mview, ItemDetailInteractor mInteractor) {
        this.mview = mview;
        this.mInteractor = mInteractor;

        mInteractor.init(this);


    }


    @Override
    public void onBagItBtClicked(ItemsForSale item, int itemAmount , int chosenVarietyIndex) {

        BagItem bagItem = new BagItem();
        bagItem.setItemAmount(String.valueOf(itemAmount));
        bagItem.setItemId(String.valueOf(item.getItem_id()));
        bagItem.setTime_of_upload(System.currentTimeMillis() / 1000L);
        bagItem.setUserId(StaticClassForGlobalInfo.UId);
      if(item.getVarieies().size()!=0){
        bagItem.setSelectedVarietyIndexInList(String.valueOf(chosenVarietyIndex));
      }

mview.showProgressBarAtBagItBt(true);
        mInteractor.uploadBagItemWithArgAsCallbackFunction(bagItem, new ItemDetailInteractor.SeparateCallbackToPresnterAfterBagItemUpload() {
            @Override
            public void onFinishedUploading(boolean callbackResultOfTheUpload) {
                if(callbackResultOfTheUpload){
                    mview.showToast("added to bag");
                    mview.showProgressBarAtBagItBt(false);
                    //TODO-make  a snackbar here or a button in a toast wich reads 'view bag items' or just 'view'
                }else{
                    mview.showToast("some error occured");
                    mview.showProgressBarAtBagItBt(false);
                }

            }
        });
    }


    @Override
    public void onFinishedCheckingSomething1() {

    }

    @Override
    public void onFinishedCheckingSomething2() {

        //this is call from interactor
    }
}
