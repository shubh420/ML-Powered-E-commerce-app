package io.shubh.e_commver1.SellerDashboard.View;


import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import io.shubh.e_commver1.ItemsDetailsTakingFragment.View.ItemsDetailsTakingFragment;
import io.shubh.e_commver1.Models.Order;
import io.shubh.e_commver1.NewOrderListFrSellerFragment.View.NewOrderListFrSellerFragment;
import io.shubh.e_commver1.R;
import io.shubh.e_commver1.SellerConfirmationFragment;
import io.shubh.e_commver1.SellerDashboard.Interactor.SellerDashboardInteractorImplt;
import io.shubh.e_commver1.SellerDashboard.Presenter.SellerDashboardPresenter;
import io.shubh.e_commver1.SellerDashboard.Presenter.SellerDashboardPresenterImplt;


public class SellerDashboardFragment extends Fragment  implements SellerDashboardView{

    View containerViewGroup;
    LayoutInflater inflater;
    SellerDashboardPresenter mPresenter;

    public SellerDashboardFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflater=inflater;
         containerViewGroup= inflater.inflate(R.layout.fragment_seller_dashboard, container, false);
        mPresenter = new SellerDashboardPresenterImplt(this, new SellerDashboardInteractorImplt() {
        });
 //------------------------------------
   doUiWork();
mPresenter.getSellerData();

        return containerViewGroup;
    }

    private void doUiWork() {
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

    }

    @Override
    public void updateTransactionSummaryTvs(ArrayList<Order.SubOrderItem> subOrderItems, int newOrders, int processed, int returnedOrders) {

       TextView newOrderTv =(TextView)containerViewGroup.findViewById(R.id.newOrderTv);
       TextView processedOrederTv =(TextView)containerViewGroup.findViewById(R.id.processedOrederTv);
       TextView returnedOrderTv =(TextView)containerViewGroup.findViewById(R.id.returnedOrderTv);

        newOrderTv.setText(String.valueOf(newOrders));
        processedOrederTv.setText(String.valueOf(processed));
        returnedOrderTv.setText(String.valueOf(returnedOrders));


        //since the data has been retrived ..now we can open the new order list
        CardView cvNewOrderBt =(CardView)containerViewGroup.findViewById(R.id.cvNewOrderBt);
        CardView cvProcessedBt =(CardView)containerViewGroup.findViewById(R.id.cvProcessedBt);
        CardView cvReturnedBt =(CardView)containerViewGroup.findViewById(R.id.cvReturnedBt);

        cvNewOrderBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewOrderListFrSellerFragment newOrderListFrSellerFragment = new NewOrderListFrSellerFragment();
                newOrderListFrSellerFragment.setLocalvariables(subOrderItems);

                getFragmentManager().beginTransaction()
                        .add(R.id.drawerLayout, newOrderListFrSellerFragment)
                        .commit();
            }
        });

        cvProcessedBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cvReturnedBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        //since the fragment is about to be destroyed..I will check if this fragemnt is the ony one opened or not
        //if its the only one opened ..that means after closing it the mainactivty will appear ..so I have to enable the drawer layout open on swipe for the activty

       /* List<Fragment> fragments = getFragmentManager().getFragments();
        if(fragments.size()==1){
            drawerLayoutSttatic.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }*/

        //now closing this activty
        getFragmentManager().beginTransaction().remove(SellerDashboardFragment.this).commit();
    }

    @Override
    public void showProgressBar(boolean b) {

        ProgressBar progressBarFrTrnsctSummryTvs =(ProgressBar)containerViewGroup.findViewById(R.id.progressBarFrTrnsctSummryTvs);
        if(b==true) {
            progressBarFrTrnsctSummryTvs.setVisibility(View.VISIBLE);
        }else {
            progressBarFrTrnsctSummryTvs.setVisibility(View.GONE);
        }
    }


    @Override
    public void switchActivity(int i) {

    }

    @Override
    public Context getContext(boolean getActvityContext) {
        return null;
    }



    @Override
    public void ShowSnackBarWithAction(String msg, String actionName) {

    }

    @Override
    public void showToast(String msg) {

    }


}
