package io.shubh.e_commver1.ItemDetailPage.View;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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

    BottomSheetBehavior behavior;
    RelativeLayout rlVpContainer;
    int itemAmount;
    String chosenvarietyName = "null";

    public ItemDetailFragment() {
        // Required empty public constructor

    }

    public static ItemDetailFragment newInstance() {
        // Bundle args = new Bundle();
        //args.putString("id", id);

        ItemDetailFragment f = new ItemDetailFragment();
        //f.setArguments(args);
        return f;
    }

    public void passData(ItemsForSale item) {
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
        doBottomSheetWork();
        setUpTvs();
        setUpAmountBarAndVariety();

        setUpToolbar();
        //doTheAnimationWorkAtLast
        doAnimationAtDelay();
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


    private void doPagerAndImageViewWork() {


        ViewPager viewPager = (ViewPager) containerViewGroup.findViewById(R.id.pager2);
        CustomPagerAdapterForItemDetailImageViewsPager adapter = new CustomPagerAdapterForItemDetailImageViewsPager(getContext(), item.getListOfImageURLs());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) containerViewGroup.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);

        rlVpContainer = (RelativeLayout) containerViewGroup.findViewById(R.id.rl_viewpager_container);


    }

    private void doBottomSheetWork() {

        RelativeLayout bottomSheet = (RelativeLayout) containerViewGroup.findViewById(R.id.ll_parent_bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int heightInPixels = displayMetrics.heightPixels;
        Log.i("***", "layout hieght =" + heightInPixels);
        int topMarginForBottomSheetInPixFromDp = (int) (285 * this.getResources().getDisplayMetrics().density + 0.5f);
        Log.i("***", "bs hieght =" + (heightInPixels - topMarginForBottomSheetInPixFromDp));

        bottomSheet.getLayoutParams().height = heightInPixels - (int) (65 * this.getResources().getDisplayMetrics().density + 0.5f);

        behavior.setPeekHeight(heightInPixels - topMarginForBottomSheetInPixFromDp);


        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_HIDDEN) {
                    getFragmentManager().beginTransaction().remove(ItemDetailFragment.this).commit();
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

    }


    private void setUpTvs() {

        TextView tvItemName = (TextView) containerViewGroup.findViewById(R.id.tvItemName);
        TextView tvItemCtgrPath = (TextView) containerViewGroup.findViewById(R.id.tvItemCtgrPath);
        TextView tvItemDecsrp = (TextView) containerViewGroup.findViewById(R.id.tvItemDecsrp);

        tvItemName.setText(item.getName());
        tvItemCtgrPath.setText(item.getCategory());
        tvItemDecsrp.setText(item.getDescription());

    }

    private void setUpAmountBarAndVariety() {

        ImageButton btPlusItemAmount = (ImageButton) containerViewGroup.findViewById(R.id.btPlusItemAmount);
        ImageButton btMinusItemAmount = (ImageButton) containerViewGroup.findViewById(R.id.btMinusItemAmount);
        TextView tvItemAmount = (TextView) containerViewGroup.findViewById(R.id.tvItemAmount);
        TextView tvItemPrice = (TextView) containerViewGroup.findViewById(R.id.tvItemPrice);

        itemAmount = 1;
        tvItemAmount.setText(String.valueOf(itemAmount));
        tvItemPrice.setText("₹" + item.getItem_price());


        btPlusItemAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemAmount++;
                tvItemAmount.setText(String.valueOf(itemAmount));
                tvItemPrice.setText("₹" + String.valueOf(Integer.valueOf(item.getItem_price()) * itemAmount));
            }
        });

        btMinusItemAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemAmount > 1) {
                    itemAmount--;
                    tvItemAmount.setText(String.valueOf(itemAmount));
                    tvItemPrice.setText("₹" + String.valueOf(Integer.valueOf(item.getItem_price()) * itemAmount));

                }
            }
        });
//---------------------------------------------

        if (item.getVarieies().size() != 0) {
            RelativeLayout rlContainerFrVariety = (RelativeLayout) containerViewGroup.findViewById(R.id.rlContainerFrVariety);
            rlContainerFrVariety.setVisibility(View.VISIBLE);
            TextView tvVarietyName = (TextView) containerViewGroup.findViewById(R.id.tvVarietyName);
            LinearLayout llVarietyContainer = (LinearLayout) containerViewGroup.findViewById(R.id.llVarietyContainer);


            for (int i = 0; i < item.getVarieies().size(); i++) {
                View inflatedVarietyBox = inflater.inflate(R.layout.infalte_variety_boxes_in_item_detail_frag, llVarietyContainer, false);
                llVarietyContainer.addView(inflatedVarietyBox);
                TextView tvIndivVarietyNmae = (TextView) inflatedVarietyBox.findViewById(R.id.tvIndivVarietyNmae);
                tvIndivVarietyNmae.setText(item.getVarieies().get(i));

                tvIndivVarietyNmae.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chosenvarietyName = tvIndivVarietyNmae.getText().toString();
                        //removing color from every other if they have it
                        final int childCount = llVarietyContainer.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            RelativeLayout rlContainingOtherTv = (RelativeLayout) llVarietyContainer.getChildAt(i);
                            TextView otherTv = (TextView) rlContainingOtherTv.findViewById(R.id.tvIndivVarietyNmae);
                            otherTv.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                        }
                        //after removing color now setting it
                        tvIndivVarietyNmae.setBackgroundColor(getResources().getColor(R.color.colorSecondaryAtHalfTransparency));
                    }
                });
//chosing the first variety as selected by default
                if (i == 0) {
                    chosenvarietyName = tvIndivVarietyNmae.getText().toString();
                    tvIndivVarietyNmae.setBackgroundColor(getResources().getColor(R.color.colorSecondaryAtHalfTransparency));
                }
            }
        }

    }


    private void doAnimationAtDelay() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                rlVpContainer.setVisibility(View.VISIBLE);
                TranslateAnimation animate = new TranslateAnimation(0, 0, -rlVpContainer.getHeight(), 0);
                animate.setDuration(300);

                rlVpContainer.startAnimation(animate);
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }, 50);

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

        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, -rlVpContainer.getHeight());
        animate.setDuration(300);
        rlVpContainer.startAnimation(animate);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rlVpContainer.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);


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
