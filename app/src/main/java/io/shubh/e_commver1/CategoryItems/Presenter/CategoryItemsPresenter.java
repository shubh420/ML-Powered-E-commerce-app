package io.shubh.e_commver1.CategoryItems.Presenter;

import java.util.List;

import io.shubh.e_commver1.Models.ItemsForSale;

public interface CategoryItemsPresenter {

    void getItemsFromFirebase(String param1CategoryName, String param2CategoryPath, String rootCtgr, String mParam1CategoryName, String mParam2CategoryPath, boolean ifItsALoadMorecall);

    void saveTheItemToLikedItems(String docId);

    void deleteTheItemFromLikedItems(String docId);

}
