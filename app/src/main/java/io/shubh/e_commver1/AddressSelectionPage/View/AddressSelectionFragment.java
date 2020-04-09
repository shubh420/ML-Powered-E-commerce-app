package io.shubh.e_commver1.AddressSelectionPage.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.shubh.e_commver1.AddressSelectionPage.Presenter.AddressSelectionPresenter;
import io.shubh.e_commver1.Models.BagItem;
import io.shubh.e_commver1.R;


public class AddressSelectionFragment extends Fragment {

    private boolean isThisFragCalledFromBagItemsFrag =false;
    private BagItem bagItem;

    View containerViewGroup;
    LayoutInflater inflater;

    AddressSelectionPresenter mPresenter;


    public AddressSelectionFragment() {
        // Required empty public constructor
    }


    public void setLocalVariables( boolean isThisFragCalledFromBagItemsFrag ,BagItem bagItem) {
        this.isThisFragCalledFromBagItemsFrag =isThisFragCalledFromBagItemsFrag;
        //if above value is false then a null bag item onject is given to the below one
        this.bagItem =bagItem;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address_selection, container, false);



    }


}
