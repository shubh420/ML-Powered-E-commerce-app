package io.shubh.e_commver1.ItemsDetailsTakingFragment.View;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import io.shubh.e_commver1.ItemsDetailsTakingFragment.Interactor.ItemsDetailsTakingInteractorImplt;
import io.shubh.e_commver1.ItemsDetailsTakingFragment.Presenter.ItemsDetailsTakingPresenter;
import io.shubh.e_commver1.ItemsDetailsTakingFragment.Presenter.ItemsDetailsTakingPresenterImplt;
import io.shubh.e_commver1.R;


// ItemsDetailsTaking
public class ItemsDetailsTakingFragment extends Fragment implements ItemsDetailsTakingView{

    ItemsDetailsTakingPresenter mPresenter;
    View containerViewGroup;
    BottomSheetBehavior behavior_bttm_sheet_which_select_img;
    LayoutInflater inflater;

    public ItemsDetailsTakingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        containerViewGroup = inflater.inflate(R.layout.fragment_items_details_taking, container, false);
        this.inflater = inflater;


        DoUiWork();
        //always do presenter related work at last in Oncreate
        mPresenter = new ItemsDetailsTakingPresenterImplt(this, new ItemsDetailsTakingInteractorImplt() {
        });


        return containerViewGroup;
    }

    private void DoUiWork() {

        attachOnBackBtPressedlistener();
        setUpImagePickingDialogueBottomSheet();
        setUpImageViewsOfImageTakingStrip();
    }

    private void setUpImagePickingDialogueBottomSheet() {


        CoordinatorLayout rootView =(CoordinatorLayout)containerViewGroup.findViewById(R.id.cl_root);
        View inflated = inflater.inflate(R.layout.select_image_dialog_bottom_sheet,rootView , false);
        //View bottomSheet = inflated.findViewById(R.id.ll_root_view_bttm_sheet);
        rootView.addView(inflated);

        behavior_bttm_sheet_which_select_img = BottomSheetBehavior.from(inflated);

        behavior_bttm_sheet_which_select_img.setState(BottomSheetBehavior.STATE_EXPANDED );

        /*View dim_background_of_bottom_sheet = (View) containerViewGroup.findViewById(R.id.touch_to_dismiss_bottom_sheet_dim_background);
        dim_background_of_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior_bttm_sheet_which_select_img.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });*/

        behavior_bttm_sheet_which_select_img.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
                if (newState == BottomSheetBehavior.STATE_HIDDEN || newState == BottomSheetBehavior.STATE_COLLAPSED) {
                   // dim_background_of_bottom_sheet.setVisibility(View.GONE);
                   // is_bottom_sheet_expanded = false;
                } else {
                   // dim_background_of_bottom_sheet.setVisibility(View.VISIBLE);
                    //is_bottom_sheet_expanded = true;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });
    }

    private void setUpImageViewsOfImageTakingStrip() {

        ImageView imageViewsForImageSelectionStrip[] = new ImageView[5];


        imageViewsForImageSelectionStrip[0] = (ImageView) containerViewGroup.findViewById(R.id.id_fr_slected_image_1);
        imageViewsForImageSelectionStrip[1] = (ImageView) containerViewGroup.findViewById(R.id.id_fr_slected_image_2);
        imageViewsForImageSelectionStrip[2] = (ImageView) containerViewGroup.findViewById(R.id.id_fr_slected_image_3);
        imageViewsForImageSelectionStrip[3] = (ImageView) containerViewGroup.findViewById(R.id.id_fr_slected_image_4);
        imageViewsForImageSelectionStrip[4] = (ImageView) containerViewGroup.findViewById(R.id.id_fr_slected_image_5);

        Button btAddImages = (Button) containerViewGroup.findViewById(R.id.id_fr_bt_choose_images);
        btAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior_bttm_sheet_which_select_img.setState(BottomSheetBehavior.STATE_EXPANDED );
            }
        });



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
        getFragmentManager().beginTransaction().remove(ItemsDetailsTakingFragment.this).commit();
    }




}
