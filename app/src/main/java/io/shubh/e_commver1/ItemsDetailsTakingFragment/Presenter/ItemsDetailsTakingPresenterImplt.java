package io.shubh.e_commver1.ItemsDetailsTakingFragment.Presenter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.shubh.e_commver1.ItemsDetailsTakingFragment.Interactor.ItemsDetailsTakingInteractor;
import io.shubh.e_commver1.ItemsDetailsTakingFragment.View.ItemsDetailsTakingView;

public class ItemsDetailsTakingPresenterImplt implements ItemsDetailsTakingPresenter, ItemsDetailsTakingInteractor.CallbacksToPresnter {

    private ItemsDetailsTakingView mView;
    private ItemsDetailsTakingInteractor mInteractor;

    ArrayList<Bitmap> imagesBitmap = new ArrayList<Bitmap>();
    String TAG = "!!!!!";

    public ItemsDetailsTakingPresenterImplt(ItemsDetailsTakingView splashview, ItemsDetailsTakingInteractor mSplashInteractor) {
        this.mView = splashview;
        this.mInteractor = mSplashInteractor;


        mInteractor.init(this);
    }

    @Override
    public void onSingleImageSelectedOrCaptured(Bitmap singleImageBitmap) {
        mView.makeNewImageViewAndSetImageToIt(singleImageBitmap );
        mView.decrementAllowedImagesPickUpAmount(1);
    }



    @Override
    public void onMultipleImagesSelectedFromGallery(List<Bitmap> multipleImageBitmaps) {

        for (int i = 0; i < multipleImageBitmaps.size(); i++) {
            mView.makeNewImageViewAndSetImageToIt(multipleImageBitmaps.get(i) );
            mView.decrementAllowedImagesPickUpAmount(1);

        }
    }

    @Override
    public void removeIvAndItsDataAtThisIndex(int index) {

        mView.removeIvAtThisIndex(index);
        mView.incrementAllowedImagesPickUpAmount(1);

    }

    @Override
    public void onEditBtOfAnyImageViewIsClickedAndBitmapHasReturned(Bitmap singleImageBitmap ,int indexOfIvOfWhichEditBtWasClicked) {
        mView.replaceBitmapOnThisPosition(indexOfIvOfWhichEditBtWasClicked ,singleImageBitmap);
    }


    @Override
    public void LoginRelatedWork() {


        mInteractor.checkSomethingInDatabaseWithArgAsCallbackFunction(new ItemsDetailsTakingInteractor.SeparateCallbackToPresnterForSystemUpdate() {
            @Override
            public void onFinishedCheckingSystemUpdate(boolean callbackResultOfTheCheck) {

                if (callbackResultOfTheCheck == true) {
                    //system upadte available ..so throw a dialog asking to download update
                } else {
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
