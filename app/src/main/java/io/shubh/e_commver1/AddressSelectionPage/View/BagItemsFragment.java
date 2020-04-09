package io.shubh.e_commver1.AddressSelectionPage.View;


import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import io.shubh.e_commver1.Adapters.ReclrAdapterClassForBagItemsList;
import io.shubh.e_commver1.AddressSelectionPage.Interactor.AddressSelectionInteractorImplt;
import io.shubh.e_commver1.AddressSelectionPage.Presenter.AddressSelectionPresenter;
import io.shubh.e_commver1.AddressSelectionPage.Presenter.AddressSelectionImplt;
import io.shubh.e_commver1.Models.BagItem;
import io.shubh.e_commver1.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BagItemsFragment extends Fragment implements AddressSelectionView, InterfaceForClickcCallback {

    View containerViewGroup;
    LayoutInflater inflater;

    AddressSelectionPresenter mPresenter;

    RecyclerView recyclerView;
    ReclrAdapterClassForBagItemsList adapter;
    List<BagItem> bagItemlist;
    int postionFromItemtoDelete;
    ShimmerFrameLayout mShimmerViewContainer;


    public BagItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        containerViewGroup = inflater.inflate(R.layout.fragment_bag_items, container, false);
        this.inflater = inflater;

        mPresenter = new AddressSelectionImplt(this, new AddressSelectionInteractorImplt() {
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
        mPresenter.getBagItemsData();
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

    @Override
    public void showItemsInRecyclerView(List<BagItem> bagItemlist) {
        this.bagItemlist = bagItemlist;
        //------------recycler setting up
        recyclerView = (RecyclerView) containerViewGroup.findViewById(R.id.rclrViewFrBagItemsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ReclrAdapterClassForBagItemsList(getContext(), this, bagItemlist);
        recyclerView.setAdapter(adapter);

        AppBarLayout appBarLayout = (AppBarLayout) containerViewGroup.findViewById(R.id.appBarLayout);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(-1)) {
                    appBarLayout.setElevation(50f);
                } else {
                    appBarLayout.setElevation(0f);
                }
            }
        });
    }

    @Override
    public void updateReclrViewListAfterDeletionOfItem() {
        bagItemlist.remove(postionFromItemtoDelete);

        adapter.notifyItemRemoved(postionFromItemtoDelete);
        adapter.notifyItemRangeChanged(postionFromItemtoDelete,bagItemlist.size());

    }

    @Override
    public void showEmptyListMessage() {

        showToast("No Items Found");

        //TODO - either show a custom toast msg here  or show a graphiv in image view on the center of the screen
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

        getFragmentManager().beginTransaction().remove(BagItemsFragment.this).commit();


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
        if(b==true) {

            if(recyclerView!=null) {
                recyclerView.setVisibility(View.GONE);
            }
            mShimmerViewContainer.startShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.VISIBLE);
        }else {
            if(recyclerView!=null) {
                recyclerView.setVisibility(View.VISIBLE);
            }
            mShimmerViewContainer.stopShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.GONE);

        }
    }

    @Override
    public void ShowSnackBarWithAction(String msg, String actionName) {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onrecyclrItemClick(BagItem bagItem) {

    }

    @Override
    public void onDeleteItemClick(String docId, int position) {
        mPresenter.deleteBagItem(docId);
        this.postionFromItemtoDelete = position;
    }
}


interface InterfaceForClickcCallback {

    void onrecyclrItemClick(BagItem bagItem);

    void onDeleteItemClick(String docId, int position);


}
