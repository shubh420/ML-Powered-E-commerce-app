package io.shubh.e_commver1.AddressSelectionPage.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import io.shubh.e_commver1.AddressSelectionPage.Interactor.AddressSelectionInteractorImplt;
import io.shubh.e_commver1.AddressSelectionPage.Presenter.AddressSelectionPresenterImplt;
import io.shubh.e_commver1.AddressSelectionPage.Presenter.AddressSelectionPresenter;
import io.shubh.e_commver1.Models.AdressItem;
import io.shubh.e_commver1.Models.BagItem;
import io.shubh.e_commver1.R;


public class AddressSelectionFragment extends Fragment implements AddressSelectionView {

    private boolean isThisFragCalledFromBagItemsFrag =false;
    private BagItem bagItem;

    View containerViewGroup;
    LayoutInflater inflater;

    AddressSelectionPresenter mPresenter;
    List<AdressItem> bagItemlist;
    int postionFromItemtoDelete;
    ShimmerFrameLayout mShimmerViewContainer;

    BottomSheetBehavior behavior_bttm_sheet_address_taking;
    int PLACE_PICKER_REQUEST = 1;

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
        containerViewGroup = inflater.inflate(R.layout.fragment_address_selection, container, false);
        this.inflater = inflater;

        mPresenter = new AddressSelectionPresenterImplt(this, new AddressSelectionInteractorImplt() {
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
        setUpAddressTakingBttmSheet();

        //logic work start here
        mPresenter.getAddressData();
    }


    private void setUpToolbar() {
        ImageButton btCloseFrag = (ImageButton) containerViewGroup.findViewById(R.id.btCloseFrag);
        btCloseFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackButtonPressed();
            }
        });

        ImageButton btAddAddress = (ImageButton) containerViewGroup.findViewById(R.id.btAddAddress);
        btCloseFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior_bttm_sheet_address_taking.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });
    }

    private void setUpAddressTakingBttmSheet() {

//---------------------------------------Bottom Sheet setup------------------

        CoordinatorLayout rootView = (CoordinatorLayout) containerViewGroup.findViewById(R.id.cl_root);
        View inflatedBottomSheetdialog = inflater.inflate(R.layout.bottom_sheet_fr_address_detail_taking, rootView, false);
        rootView.addView(inflatedBottomSheetdialog);

        behavior_bttm_sheet_address_taking = BottomSheetBehavior.from(inflatedBottomSheetdialog);
        behavior_bttm_sheet_address_taking.setState(BottomSheetBehavior.STATE_COLLAPSED);

        View dim_background_of_bottom_sheet = (View) containerViewGroup.findViewById(R.id.touch_to_dismiss_bottom_sheet_dim_background);
        dim_background_of_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior_bttm_sheet_address_taking.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        behavior_bttm_sheet_address_taking.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
                if (newState == BottomSheetBehavior.STATE_HIDDEN || newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    dim_background_of_bottom_sheet.setVisibility(View.GONE);
                    // is_bottom_sheet_expanded = false;
                } else {
                    dim_background_of_bottom_sheet.setVisibility(View.VISIBLE);
                    // is_bottom_sheet_expanded = true;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        LinearLayout llAsBtUseGps = (LinearLayout) inflatedBottomSheetdialog.findViewById(R.id.llAsBtUseGps);
        llAsBtUseGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

       /* Button btCamera = (Button) inflatedBottomSheetdialog.findViewById(R.id.bt_camera);
        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior_bttm_sheet_which_select_img.setState(BottomSheetBehavior.STATE_COLLAPSED);

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (allowedImagesAmountForPickup != 0) {
                    startActivityForResult(cameraIntent, CAPTURE_IMAGE_FROM_CAMERA);
                } else {
                    showToast("Images Limit of 5 crossed");
                }
            }
        });*/

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

        getFragmentManager().beginTransaction().remove(AddressSelectionFragment.this).commit();


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
    public void showItemsInRecyclerView(List<BagItem> bagItemlist) {

    }

    @Override
    public void updateReclrViewListAfterDeletionOfItem() {

    }

    @Override
    public void showEmptyListMessage() {

    }
}
