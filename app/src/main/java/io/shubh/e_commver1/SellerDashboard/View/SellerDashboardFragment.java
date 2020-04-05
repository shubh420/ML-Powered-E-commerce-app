package io.shubh.e_commver1.SellerDashboard.View;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import io.shubh.e_commver1.ItemsDetailsTakingFragment.View.ItemsDetailsTakingFragment;
import io.shubh.e_commver1.R;
import io.shubh.e_commver1.SellerConfirmationFragment;


public class SellerDashboardFragment extends Fragment {

    View containerViewGroup;

    public SellerDashboardFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         containerViewGroup= inflater.inflate(R.layout.fragment_seller_dashboard, container, false);

 //------------------------------------
        attachOnBackBtPressedlistener();

        Button btAddNewItem = (Button) containerViewGroup.findViewById(R.id.btAddNewItem);
        btAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO- change the below drawer layout to the root layout of this fragment ..and do this same changes
                // in all the fragments which are opening other fragments

                getFragmentManager().beginTransaction()
                        //both parameters for instantiating the fragment will be same as at rootl level of ctgr tree ,the name of ctgr and path is same
                        .add(R.id.drawerLayout, new ItemsDetailsTakingFragment())
                        .commit();
            }
        });

        ImageButton btCloseFrag = (ImageButton) containerViewGroup.findViewById(R.id.btCloseFrag);
        btCloseFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackButtonPressed();
            }
        });



        return containerViewGroup;
    }


    //below function is for catching back button pressed
    private void attachOnBackBtPressedlistener() {
        containerViewGroup.setFocusableInTouchMode(true);
        containerViewGroup.requestFocus();
        containerViewGroup.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    onBackButtonPressed();
                    return true;
                }
                return false;
            }
        });
    }

    private void onBackButtonPressed() {
        //since the fragment is about to be destroyed..I will check if this fragemnt is the ony one opened or not
        //if its the only one opened ..that means after closing it the mainactivty will appear ..so I have to enable the drawer layout open on swipe for the activty

       /* List<Fragment> fragments = getFragmentManager().getFragments();
        if(fragments.size()==1){
            drawerLayoutSttatic.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }*/

        //now closing this activty
        getFragmentManager().beginTransaction().remove(SellerDashboardFragment.this).commit();
    }

}
