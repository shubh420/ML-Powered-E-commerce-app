package io.shubh.e_commver1.ItemDetailPage.Presenter;

import io.shubh.e_commver1.Models.ItemsForSale;

public interface ItemDetailPresenter {

  //  void checkIfAlreadyLoggedIn();

    //void checkForSystemUpdates();

   // void onGettingThegoogleSignInResult(int code, int requestCode, Intent data);

    void onBagItBtClicked(ItemsForSale item, int itemAmount , int chosenVarietyIndex);

    void saveTheItemToLikedItems(String docId);

    void deleteTheItemFromLikedItems(String docId);

}
