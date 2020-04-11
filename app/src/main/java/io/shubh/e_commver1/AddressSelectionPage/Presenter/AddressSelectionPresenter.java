package io.shubh.e_commver1.AddressSelectionPage.Presenter;

import io.shubh.e_commver1.Models.AdressItem;

public interface AddressSelectionPresenter {



    void getAddressData();

    void deleteBagItem(String docId);

    void addAdressObject(AdressItem adressItem);
}
