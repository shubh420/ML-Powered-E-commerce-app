package io.shubh.e_commver1.BagItems.View;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import io.shubh.e_commver1.Adapters.ReclrAdapterClassForBagItemsList;
import io.shubh.e_commver1.BagItems.Interactor.BagItemsInteractorImplt;
import io.shubh.e_commver1.BagItems.Presenter.BagItemsPresenter;
import io.shubh.e_commver1.BagItems.Presenter.BagItemsPresenterImplt;
import io.shubh.e_commver1.Models.BagItem;
import io.shubh.e_commver1.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BagItemsFragment extends Fragment implements BagItemsView, InterfaceForClickcCallback {

    View containerViewGroup;
    LayoutInflater inflater;

    ImageView splashimageicon;
    ProgressBar progressBar;
    BagItemsPresenter mPresenter;

    RecyclerView recyclerView;
    ReclrAdapterClassForBagItemsList adapter;
    List<BagItem> bagItemlist;
    int postionFromItemtoDelete;

    public BagItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        containerViewGroup = inflater.inflate(R.layout.fragment_bag_items, container, false);
        this.inflater = inflater;

        mPresenter = new BagItemsPresenterImplt(this, new BagItemsInteractorImplt() {
        });

        DoUiWork();


        // Inflate the layout for this fragment
        return containerViewGroup;
    }


    private void DoUiWork() {

        attachOnBackBtPressedlistener();
        setUpToolbar();

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

    }

    @Override
    public void ShowSnackBarWithAction(String msg, String actionName) {

    }

    @Override
    public void showToast(String msg) {

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
