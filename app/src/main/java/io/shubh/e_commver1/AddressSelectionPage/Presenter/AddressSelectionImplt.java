package io.shubh.e_commver1.AddressSelectionPage.Presenter;

import java.util.List;

import io.shubh.e_commver1.AddressSelectionPage.Interactor.AddressSelectionInteractor;
import io.shubh.e_commver1.AddressSelectionPage.View.AddressSelectionView;
import io.shubh.e_commver1.Models.BagItem;

public class AddressSelectionImplt implements AddressSelectionPresenter, AddressSelectionInteractor.CallbacksToPresnter {

    private AddressSelectionView mView;
    private AddressSelectionInteractor mInteractor;


    public AddressSelectionImplt(AddressSelectionView mView , AddressSelectionInteractor mSplashInteractor) {
       this.mView=mView;
       this.mInteractor = mSplashInteractor;

       mInteractor.init(this);



    }

    @Override
    public void getBagItemsData() {

        mView.showProgressBar(true);

        mInteractor.getbagItemsDataWithArgAsCallbackFunction( new AddressSelectionInteractor.SeparateCallbackToPresnterAfterGettingTheObjectList(){
            @Override
            public void onFinished(boolean callbackResultOfTheCheck, List<BagItem> bagItemlist) {

                if(callbackResultOfTheCheck==true){
                    //system upadte available ..so throw a dialog asking to download update
                    if(bagItemlist.size()!=0){

                        mView.showProgressBar(false);
                        mView.showItemsInRecyclerView(bagItemlist);
                    }else{
                        mView.showProgressBar(false);
                        mView.showEmptyListMessage();
                    }


                }else{
                    //system upadte not available ..so continue
                    mView.showProgressBar(false);
                     mView.showToast("Some Problem Ocuured");
                }
            }
        });

    }

    @Override
    public void deleteBagItem(String docId) {
        mInteractor.deletebagItemsWithArgAsCallbackFunction(docId,new AddressSelectionInteractor.SeparateCallbackToPresnterAfterDeletingBagItem() {
            @Override
            public void onFinished(boolean callbackResultOfTheCheck) {

                if(callbackResultOfTheCheck==true){
                    mView.updateReclrViewListAfterDeletionOfItem();
                }else{
                    mView.showToast("Some Problem Ocuured");
                }
            }
        });
    }


}
