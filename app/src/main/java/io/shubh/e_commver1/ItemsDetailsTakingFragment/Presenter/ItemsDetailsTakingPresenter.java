package io.shubh.e_commver1.ItemsDetailsTakingFragment.Presenter;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.List;

public interface ItemsDetailsTakingPresenter {

  //  void checkIfAlreadyLoggedIn();

    //void checkForSystemUpdates();

   // void onGettingThegoogleSignInResult(int code, int requestCode, Intent data);

     void LoginRelatedWork();

    void onSingleImageSelectedOrCaptured(Bitmap singleImageBitmap);

    void onMultipleImagesSelectedFromGallery(List<Bitmap> multipleImageBitmaps);

    void removeIvAndItsDataAtThisIndex(int index);


    void onEditBtOfAnyImageViewIsClickedAndBitmapHasReturned(Bitmap singleImageBitmap ,int indexOfIvOfWhichEditBtWasClicked);
}
