package io.shubh.e_commver1.ItemDetailPage.View;


import android.content.Context;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import io.shubh.e_commver1.Adapters.CustomPagerAdapterForItemDetailImageViewsPager;
import io.shubh.e_commver1.CategoryItems.View.CategoryItemsFragment;
import io.shubh.e_commver1.CustomPagerAdapter;
import io.shubh.e_commver1.Models.ItemsForSale;
import io.shubh.e_commver1.R;


public class ItemDetailFragment extends Fragment implements ItemDetailView {


    View containerViewGroup;

    ItemsForSale item;
    LayoutInflater inflater;


    public ItemDetailFragment(ItemsForSale item) {
        // Required empty public constructor
        this.item = item;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //setting the transparent theme dynamicallly and infalting the layout
        Context contextWrapper = new ContextThemeWrapper(getActivity(), R.style.Theme_AppCompat_Translucent);
        LayoutInflater localInflater = inflater.cloneInContext(contextWrapper);
        containerViewGroup = localInflater.inflate(R.layout.fragment_item_detail,
                container, false);

        this.inflater = inflater;

        doUiWork();


        // Inflate the layout for this fragment
        return containerViewGroup;
    }

    private void doUiWork() {

        attachOnBackBtPressedlistener();
        doPagerAndImageViewWork();
    }

    private void doPagerAndImageViewWork() {


        ViewPager viewPager = (ViewPager) containerViewGroup.findViewById(R.id.pager2);
        CustomPagerAdapterForItemDetailImageViewsPager adapter = new CustomPagerAdapterForItemDetailImageViewsPager(getContext() ,item.getListOfImageURLs());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) containerViewGroup.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);




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

        //now closing this activty
        getFragmentManager().beginTransaction().remove(ItemDetailFragment.this).commit();
    }


    @Override
    public void switchActivity(int i) {

    }

    @Override
    public Context getContext(boolean getActvityContext) {
        return null;
    }

    @Override
    public void showProgressBar(boolean b) {

    }

    @Override
    public void ShowSnackBarWithAction(String msg, String actionName) {

    }

    @Override
    public void showToast(String msg) {

    }
}
