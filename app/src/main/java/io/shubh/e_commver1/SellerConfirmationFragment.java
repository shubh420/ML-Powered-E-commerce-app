package io.shubh.e_commver1;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class SellerConfirmationFragment extends Fragment {
//this fragment doesnt have factory methods like ''newinstance' method ..and bundle args passed
View containerViewGroup;

    public SellerConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        containerViewGroup =inflater.inflate(R.layout.fragment_seller_confirmation, container, false);





        return containerViewGroup;
    }

}
