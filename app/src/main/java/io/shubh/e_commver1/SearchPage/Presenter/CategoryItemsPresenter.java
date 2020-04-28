package io.shubh.e_commver1.SearchPage.Presenter;

public interface CategoryItemsPresenter {

    void getItemsFromFirebase(String param1CategoryName, String param2CategoryPath, String rootCtgr, String mParam1CategoryName, String mParam2CategoryPath, boolean ifItsALoadMorecall);

    void saveTheItemToLikedItems(String docId);

    void deleteTheItemFromLikedItems(String docId);

}
