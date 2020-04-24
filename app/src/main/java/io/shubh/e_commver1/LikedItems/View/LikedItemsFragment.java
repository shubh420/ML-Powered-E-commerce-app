package io.shubh.e_commver1.LikedItems.View;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import io.shubh.e_commver1.Adapters.ReclrAdapterClassForBagItemsList;
import io.shubh.e_commver1.LikedItems.Interactor.LikedItemsInteractorImplt;
import io.shubh.e_commver1.LikedItems.Presenter.LikedItemsPresenter;
import io.shubh.e_commver1.LikedItems.Presenter.LikedItemsPresenterImplt;
import io.shubh.e_commver1.Models.BagItem;
import io.shubh.e_commver1.Models.LikedItem;
import io.shubh.e_commver1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikedItemsFragment extends Fragment implements LikedItemsView  {

    View containerViewGroup;
    LayoutInflater inflater;
    LikedItemsPresenter mPresenter;


    RecyclerView recyclerView;
    ReclrAdapterClassForBagItemsList adapter;
    List<LikedItem> likedItemList;
    int postionFromItemtoDelete;
    ShimmerFrameLayout mShimmerViewContainer;

    public LikedItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        containerViewGroup = inflater.inflate(R.layout.fragment_liked_items, container, false);
        this.inflater = inflater;

        mPresenter = new LikedItemsPresenterImplt(this, new LikedItemsInteractorImplt() {
        });

        DoUiWork();


        // Inflate the layout for this fragment
        return containerViewGroup;
    }


    private void DoUiWork() {

        //initializations here
        mShimmerViewContainer = containerViewGroup.findViewById(R.id.shimmer_view_container);

        //---setups here
        attachOnBackBtPressedlistener();
        setUpToolbar();

        //logic work start here
      //
        //  mPresenter.getBagItemsData();
    }


    private void setUpToolbar() {
        ImageButton btCloseFrag = (ImageButton) containerViewGroup.findViewById(R.id.btCloseFrag);
        btCloseFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackButtonPressed();
            }
        });
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

        getFragmentManager().beginTransaction().remove(LikedItemsFragment.this).commit();


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
