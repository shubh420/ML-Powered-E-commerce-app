package io.shubh.e_commver1.CategoryItems.Presenter;

public interface CategoryItemsPresenter {

  //  void checkIfAlreadyLoggedIn();

    //void checkForSystemUpdates();

   // void onGettingThegoogleSignInResult(int code, int requestCode, Intent data);

     void LoginRelatedWork();

    void getItemsFromFirebase(String mParam1CategoryName,String mParam2CategoryPath,boolean ifItsALoadMorecall);
}
