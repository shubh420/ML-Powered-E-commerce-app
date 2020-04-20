package io.shubh.e_commver1.NewOrderListFrSellerFragment.View;


import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

import io.shubh.e_commver1.Models.Order;
import io.shubh.e_commver1.NewOrderListFrSellerFragment.Interactor.NewOrderListFrSellerInteractorImplt;
import io.shubh.e_commver1.NewOrderListFrSellerFragment.Presenter.NewOrderListFrSellerPresenter;
import io.shubh.e_commver1.NewOrderListFrSellerFragment.Presenter.NewOrderListFrSellerPresenterImplt;
import io.shubh.e_commver1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrderListFrSellerFragment extends Fragment implements NewOrderListFrSellerView {

    View containerViewGroup;
    LayoutInflater inflater;
    NewOrderListFrSellerPresenter mPresenter;
    ArrayList<Order.SubOrderItem> subOrderItems;
    ShimmerFrameLayout mShimmerViewContainer;

    public NewOrderListFrSellerFragment() {
        // Required empty public constructor

    }

    public void setLocalvariables(ArrayList<Order.SubOrderItem> subOrderItems) {
        // Required empty public constructor
        this.subOrderItems = subOrderItems;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        containerViewGroup = inflater.inflate(R.layout.fragment_new_order_list_fr_seller, container, false);
        this.inflater = inflater;

        mPresenter = new NewOrderListFrSellerPresenterImplt(this, new NewOrderListFrSellerInteractorImplt() {
        });

        DoUiWork();
        // Inflate the layout for this fragment
        return containerViewGroup;
    }

    private void DoUiWork() {

        setUpToolbar();

        // not using a recl view here for now...Todo- maybe do that later when used for real purpose
        showItemsInNonReclrViewList();
    }

    private void showItemsInNonReclrViewList() {

        LinearLayout llContainerFrItems = (LinearLayout) containerViewGroup.findViewById(R.id.llContainerFrItems);
        //llContainerFrAddressItems.removeAllViews();

        for (int i = 0; i < subOrderItems.size(); i++) {

            View inflatedVarietyBox = inflater.inflate(R.layout.inflate_sub_order_item, llContainerFrItems, false);
            llContainerFrItems.addView(inflatedVarietyBox);

            TextView tvOrdreNo = (TextView) inflatedVarietyBox.findViewById(R.id.tvOrdreNo);
            TextView tvTimeOfCreation = (TextView) inflatedVarietyBox.findViewById(R.id.tvTimeOfCreation);
            TextView tvOrdreAmount = (TextView) inflatedVarietyBox.findViewById(R.id.tvOrdreAmount);
            TextView tvItemName = (TextView) inflatedVarietyBox.findViewById(R.id.tvItemName);
            TextView tvAddress = (TextView) inflatedVarietyBox.findViewById(R.id.tvAddress);
            TextView tvVarietyCtgrName = (TextView) inflatedVarietyBox.findViewById(R.id.tvVarietyCtgrName);
            TextView tvVarietyName = (TextView) inflatedVarietyBox.findViewById(R.id.tvVarietyName);

            ImageView ivItemImage = (ImageView) inflatedVarietyBox.findViewById(R.id.ivItemImage);
            // iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_radio_button_unchecked_svg));

            Glide.with(getContext()).load(subOrderItems.get(i).getImageUrl()).centerCrop().into(ivItemImage);

            tvOrdreNo.setText("Item Id " + subOrderItems.get(i).getItemId() +
                    " from order no " + subOrderItems.get(i).getParentOrderId());
            tvTimeOfCreation.setText(String.valueOf((int) subOrderItems.get(i).getTimeOfCreationOfOrder()));
            tvOrdreAmount.setText("Qt." +subOrderItems.get(i).getItemAmount());
            tvItemName.setText(subOrderItems.get(i).getItemName());
            String sourceString = "Ordered for " + "<b>" + subOrderItems.get(i).getAdressItem().getRecieverName() + "</b> "
                    + " for to be delivered at " + "<b>" + subOrderItems.get(i).getAdressItem().getArea() + ", " +
                    subOrderItems.get(i).getAdressItem().getCity() + ", " + subOrderItems.get(i).getAdressItem().getState() + "</b>" +
                    ", House no : " + "<b>" + subOrderItems.get(i).getAdressItem().getHouseNo() + "</b>";
            tvAddress.setText(Html.fromHtml(sourceString));
            if (subOrderItems.get(i).getVarietyName() != null) {
                tvVarietyCtgrName.setVisibility(View.VISIBLE);
                tvVarietyName.setVisibility(View.VISIBLE);

                tvVarietyCtgrName.setText(subOrderItems.get(i).getVarietyName());
                tvVarietyCtgrName.setText(subOrderItems.get(i).getSelectedVarietyName());
            }
        }

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

        getFragmentManager().beginTransaction().remove(io.shubh.e_commver1.NewOrderListFrSellerFragment.View.NewOrderListFrSellerFragment.this).commit();


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
