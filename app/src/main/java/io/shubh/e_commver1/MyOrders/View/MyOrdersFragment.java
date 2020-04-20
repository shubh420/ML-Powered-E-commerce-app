package io.shubh.e_commver1.MyOrders.View;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import io.shubh.e_commver1.Adapters.ReclrAdapterClassForBagItemsList;
import io.shubh.e_commver1.Adapters.ReclrAdapterClassForOrderItemsList;
import io.shubh.e_commver1.AddressSelectionPage.View.AddressSelectionFragment;
import io.shubh.e_commver1.BagItems.View.BagItemsFragment;
import io.shubh.e_commver1.Models.Order;
import io.shubh.e_commver1.MyOrders.Interactor.MyOrdersInteractorImplt;
import io.shubh.e_commver1.MyOrders.Presenter.MyOrdersPresenter;
import io.shubh.e_commver1.MyOrders.Presenter.MyOrdersPresenterImplt;
import io.shubh.e_commver1.R;

public class MyOrdersFragment extends Fragment implements MyOrdersView{

    View containerViewGroup;
    LayoutInflater inflater;
    MyOrdersPresenter mPresenter;
    List<Order> orderItemlist;
    RecyclerView recyclerView;
    ReclrAdapterClassForOrderItemsList adapter;
    
    public MyOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        containerViewGroup = inflater.inflate(R.layout.fragment_my_orders, container, false);
        this.inflater = inflater;
        
        mPresenter = new MyOrdersPresenterImplt(this, new MyOrdersInteractorImplt() {
        });

        DoUiWork();


        // Inflate the layout for this fragment
        return containerViewGroup;
    }

    private void DoUiWork() {

        //initializations here //todo shimmer below
       // mShimmerViewContainer = containerViewGroup.findViewById(R.id.shimmer_view_container);

        //---setups here
        attachOnBackBtPressedlistener();
        setUpToolbar();

        //logic work start here
        mPresenter.getOrderItemsData();

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
    public void showItemsInRecyclerView(List<Order> orderItemlist) {
        this.orderItemlist = orderItemlist;
        //------------recycler setting up
        recyclerView = (RecyclerView) containerViewGroup.findViewById(R.id.rclrViewFrMyOrdersList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ReclrAdapterClassForOrderItemsList(getContext(), orderItemlist);
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

        getFragmentManager().beginTransaction().remove(MyOrdersFragment.this).commit();


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
    public void showEmptyListMessage() {

    }
}
