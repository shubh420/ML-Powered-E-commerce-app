package io.shubh.e_commver1.ItemsDetailsTakingFragment.View;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import io.shubh.e_commver1.ItemsDetailsTakingFragment.Interactor.ItemsDetailsTakingInteractorImplt;
import io.shubh.e_commver1.ItemsDetailsTakingFragment.Presenter.ItemsDetailsTakingPresenter;
import io.shubh.e_commver1.ItemsDetailsTakingFragment.Presenter.ItemsDetailsTakingPresenterImplt;
import io.shubh.e_commver1.Models.Category;
import io.shubh.e_commver1.R;
import io.shubh.e_commver1.StaticClassForGlobalInfo;


// ItemsDetailsTaking
public class ItemsDetailsTakingFragment extends Fragment implements ItemsDetailsTakingView {

    ItemsDetailsTakingPresenter mPresenter;
    View containerViewGroup;
    BottomSheetBehavior behavior_bttm_sheet_which_select_img;
    LayoutInflater inflater;


    Bitmap singleImageBitmap;
    List<Bitmap> multipleImageBitmap;
    int allowedImagesAmountForPickup = 5;

    int PICKUP_MULTIPLE_IMAGE_FROM_STORAGE = 1;
    int CAPTURE_IMAGE_FROM_CAMERA = 2;
    int PICKUP_SINGLE_IMAGE_FROM_STORAGE = 3;

    boolean isEditBtOnAnyImageViewIscalled = false;
    int indexOfIvOfWhichEditBtWasClicked = -1;

    String mCategoryPath = "null initially";
    String mCategoryName = "null initially";
    String rootCtgr;
    String subCtgr;
    String subSubCtgr;
    String TAG = "!!!!!";

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
        setUpImagePickingDialogueBottomSheetAndImgBttnForIt();
        setUpCtgrSelectionBox();
    }

    private void setUpImagePickingDialogueBottomSheetAndImgBttnForIt() {

        Button btAddImages = (Button) containerViewGroup.findViewById(R.id.id_fr_bt_choose_images);
        btAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior_bttm_sheet_which_select_img.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
//---------------------------------------Bottom Sheet setup------------------

        CoordinatorLayout rootView = (CoordinatorLayout) containerViewGroup.findViewById(R.id.cl_root);
        View inflatedBottomSheetdialog = inflater.inflate(R.layout.select_image_dialog_bottom_sheet, rootView, false);
        rootView.addView(inflatedBottomSheetdialog);

        behavior_bttm_sheet_which_select_img = BottomSheetBehavior.from(inflatedBottomSheetdialog);

        behavior_bttm_sheet_which_select_img.setState(BottomSheetBehavior.STATE_COLLAPSED);

        View dim_background_of_bottom_sheet = (View) containerViewGroup.findViewById(R.id.touch_to_dismiss_bottom_sheet_dim_background);
        dim_background_of_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior_bttm_sheet_which_select_img.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        behavior_bttm_sheet_which_select_img.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
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

        Button btGallery = (Button) inflatedBottomSheetdialog.findViewById(R.id.bt_gallery);
        btGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior_bttm_sheet_which_select_img.setState(BottomSheetBehavior.STATE_COLLAPSED);

                //when edit bt is clicked ,that means only one image needs to be picked up and replaced
                if (isEditBtOnAnyImageViewIscalled == false) {
                    //throwing an intent which opens gallery to select multiple images
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    if (allowedImagesAmountForPickup != 0) {
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICKUP_MULTIPLE_IMAGE_FROM_STORAGE);
                    } else {
                        showToast("Images Limit of 5 crossed");
                    }
                } else {
                    //throwing an intent which opens gallery to select single images
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickIntent, PICKUP_SINGLE_IMAGE_FROM_STORAGE);
                    pickIntent.setType("image/*");
                    isEditBtOnAnyImageViewIscalled = false;
                }
            }
        });

        Button btCamera = (Button) inflatedBottomSheetdialog.findViewById(R.id.bt_camera);
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
        });

    }

    @Override
    public void decrementAllowedImagesPickUpAmount(int decrmntThisAmount) {
        allowedImagesAmountForPickup = allowedImagesAmountForPickup - decrmntThisAmount;
    }

    @Override
    public void makeNewImageViewAndSetImageToIt(Bitmap imgBitmap) {
        LinearLayout llContainerFrIvs = (LinearLayout) containerViewGroup.findViewById(R.id.ll_container_fr_ivs);


        RelativeLayout inflatedIvRlContainer = (RelativeLayout) inflater.inflate(R.layout.inflate_relative_layouts_with_image_views, llContainerFrIvs, false);
        llContainerFrIvs.addView(inflatedIvRlContainer);

        ImageView iv = (ImageView) inflatedIvRlContainer.findViewById(R.id.id_fr_slected_image);
        ImageButton btRemove = (ImageButton) inflatedIvRlContainer.findViewById(R.id.id_fr_bt_remove);
        ImageButton btEdit = (ImageButton) inflatedIvRlContainer.findViewById(R.id.id_fr_bt_edit);

        //TODO--use glide or piccasso below to set image ...so that quality can be changed to thumbnail level
        iv.setImageBitmap(imgBitmap);

        btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indexOfThisRelativeView = llContainerFrIvs.indexOfChild(inflatedIvRlContainer);
                mPresenter.removeIvAndItsDataAtThisIndex(indexOfThisRelativeView);
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indexOfThisRelativeView = llContainerFrIvs.indexOfChild(inflatedIvRlContainer);
                indexOfIvOfWhichEditBtWasClicked = indexOfThisRelativeView;
                isEditBtOnAnyImageViewIscalled = true;
                behavior_bttm_sheet_which_select_img.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

    }

    @Override
    public void removeIvAtThisIndex(int index) {
        LinearLayout llContainerFrIvs = (LinearLayout) containerViewGroup.findViewById(R.id.ll_container_fr_ivs);
        llContainerFrIvs.removeViewAt(index);
    }

    @Override
    public void incrementAllowedImagesPickUpAmount(int i) {
        allowedImagesAmountForPickup++;
    }

    @Override
    public void replaceBitmapOnThisPosition(int indexOfIvOfWhichEditBtWasClicked, Bitmap singleImageBitmap) {
        LinearLayout llContainerFrIvs = (LinearLayout) containerViewGroup.findViewById(R.id.ll_container_fr_ivs);
        ImageView iv = (ImageView) llContainerFrIvs.getChildAt(indexOfIvOfWhichEditBtWasClicked).findViewById(R.id.id_fr_slected_image);
        iv.setImageBitmap(singleImageBitmap);
    }


//========================================================


    private void setUpCtgrSelectionBox() {
        //  loadCategorylayoutsInTheSidebarWithAnimation();
        loadCategorylayoutsInTheSidebar();
    }

    private void loadCategorylayoutsInTheSidebarWithAnimation() {
        LinearLayout ll_container_side_bar = (LinearLayout) containerViewGroup.findViewById(R.id.container_fr_ctgr);
        TranslateAnimation animate = new TranslateAnimation(0, -ll_container_side_bar.getWidth(), 0, 0);
        animate.setDuration(300);
        ll_container_side_bar.startAnimation(animate);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadCategorylayoutsInTheSidebar();

                TranslateAnimation animate = new TranslateAnimation(ll_container_side_bar.getWidth(), 0, 0, 0);
                animate.setDuration(300);
                ll_container_side_bar.startAnimation(animate);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    private void loadCategorylayoutsInTheSidebar() {


        LinearLayout llContainerFrCtgr = (LinearLayout) containerViewGroup.findViewById(R.id.container_fr_ctgr);

        //if ctgr path is equal to  "null initially" that means ctgr box havnt been touched  ..and The first thing I need to show is list of ctgr not subctgrs
        if (mCategoryPath == "null initially") {

            ArrayList<Category> categoriesList = StaticClassForGlobalInfo.categoriesList;
            llContainerFrCtgr.removeAllViews();
            setUpTheHeaderBackbutton(true);
            updateCtgrBoxHeader("Select a Category");

            for (int i = 0; i < categoriesList.size(); i++) {

                View inflatedRow = inflater.inflate(R.layout.infalte_vertical_rows_fr_ctgr_box_of_item_details_taking_frag, llContainerFrCtgr, false);
                llContainerFrCtgr.addView(inflatedRow);

                TextView tv = (TextView) inflatedRow.findViewById(R.id.tv_ctgr_name);
                ImageView iv = (ImageView) inflatedRow.findViewById(R.id.iv_ctgr_indicator);

                tv.setText(categoriesList.get(i).getName());
                //making the iv with arrows if the ctgr have subctgrs
                if (categoriesList.get(i).isHaveSubCatgr()) {
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_svg));
                } else {
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.radio_bt));
                }



                int finalI = i;
                inflatedRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mCategoryName = categoriesList.get(finalI).getName();
                        rootCtgr = mCategoryName;
                        mCategoryPath = rootCtgr;


                        /*if (categoriesList.get(i).isHaveSubCatgr()) {
                            // above if is to check if the same ctgr is clicked twice or not...because if it is...then the ctgrpath will already have the string i am adding below
                            mCategoryPath = rootCtgr + "/" + subCtgr;
                            mParam1CategoryName = subCategoriesList.get(finalJ).getName();
                        }
*/
                        if (categoriesList.get(finalI).isHaveSubCatgr()) {
                            //   updateCtgrBoxHeader();
                            loadCategorylayoutsInTheSidebarWithAnimation();

                        } else {
                            // updateCtgrBoxHeader();
                            iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_svg));
                            setRadioBtToAllOtherRowsWhichHadThemOriginally(inflatedRow);


                        }
                    }
                });
            }

        }


        //below if checks if path has the / and // char in it or not ...-1 means it doesnt ..so it checks if we
        //are at level 1 ...that means we have selected a ctgr and now we have to show a list a subctgrs using below code
        if (mCategoryPath.indexOf('/') == -1 && mCategoryPath.indexOf("//") == -1 && mCategoryPath != "null initially") {
            //  tv_header.setText(mParam1CategoryName);
            //I have only have the name of the category and where it is on the ctgr level
            //since its at one ,I will just iterate through each ctgr at one level to find it
            //after finding it i will hust load the subctgr under it

            for (int i = 0; i < StaticClassForGlobalInfo.categoriesList.size(); i++) {
                if (StaticClassForGlobalInfo.categoriesList.get(i).getName() == rootCtgr) {

                    ArrayList<Category.SubCategory> subCategoriesList = StaticClassForGlobalInfo.categoriesList.get(i).getSubCategoriesList();
                    llContainerFrCtgr.removeAllViews();
                    setUpTheHeaderBackbutton(false);
                    updateCtgrBoxHeader(rootCtgr);

                    for (int j = 0; j < subCategoriesList.size(); j++) {

                        View inflatedRow = inflater.inflate(R.layout.infalte_vertical_rows_fr_ctgr_box_of_item_details_taking_frag, llContainerFrCtgr, false);
                        llContainerFrCtgr.addView(inflatedRow);

                        TextView tv = (TextView) inflatedRow.findViewById(R.id.tv_ctgr_name);
                        ImageView iv = (ImageView) inflatedRow.findViewById(R.id.iv_ctgr_indicator);

                        tv.setText(subCategoriesList.get(j).getName());
                        //making the iv with arrows if the subctgr have subsubctgrs
                        if (subCategoriesList.get(j).isHaveSubSubCatgr()) {
                            iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_svg));
                        } else {
                            iv.setImageDrawable(getResources().getDrawable(R.drawable.radio_bt));
                        }



                        int finalJ = j;
                        inflatedRow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mCategoryName = subCategoriesList.get(finalJ).getName();
                                subCtgr = mCategoryName;
                                mCategoryPath = rootCtgr + "/" + subCtgr;

                                updateCtgrBoxHeader(mCategoryName);

                              /*  if (mCategoryPath.indexOf(subCategoriesList.get(finalJ).getName()) == -1) {
                                    // above if is to check if the same ctgr is clicked twice or not...because if it is...then the ctgrpath will already have the string i am adding below
                                    mCategoryPath = rootCtgr + "/" + subCtgr;
                                    mParam1CategoryName = subCategoriesList.get(finalJ).getName();
                                }
*/
                                if (subCategoriesList.get(finalJ).isHaveSubSubCatgr()) {

                                    loadCategorylayoutsInTheSidebarWithAnimation();
                                } else {

                                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_svg));
                                    setRadioBtToAllOtherRowsWhichHadThemOriginally(inflatedRow);
                                }
                            }
                        });
                    }

                }
            }
        }
        //below if checks if we are at level 3 that is of subsubctgr..so it will show the subsubctgrlist
        if (mCategoryPath.indexOf('/') != -1 && mCategoryPath.indexOf("//") == -1) {

            //I have only have the name of the subcategory and where it is on the ctgr level
            //since its at two,first  ,I will just iterate through each ctgr at one level to find it whome it is subctgr of
            //after finding it i will hust load the subsubctgr under it
            for (int i = 0; i < StaticClassForGlobalInfo.categoriesList.size(); i++) {
                if (StaticClassForGlobalInfo.categoriesList.get(i).isHaveSubCatgr() == true) {

                    for (int j = 0; j < StaticClassForGlobalInfo.categoriesList.get(i).getSubCategoriesList().size(); j++) {
                        if (subCtgr == StaticClassForGlobalInfo.categoriesList.get(i).getSubCategoriesList().get(j).getName()) {
                            //we have got the subctgr in the ctgrpath
                            ArrayList<Category.SubCategory.SubSubCategory> subsubCategoriesList = StaticClassForGlobalInfo.categoriesList.get(i).getSubCategoriesList().get(j).getSubSubCategoryList();
                            llContainerFrCtgr.removeAllViews();
                            setUpTheHeaderBackbutton(false);
                            updateCtgrBoxHeader(subCtgr);

                            for (int k = 0; k < subsubCategoriesList.size(); k++) {
                                View inflatedRow = inflater.inflate(R.layout.infalte_vertical_rows_fr_ctgr_box_of_item_details_taking_frag, llContainerFrCtgr, false);
                                llContainerFrCtgr.addView(inflatedRow);

                                TextView tv = (TextView) inflatedRow.findViewById(R.id.tv_ctgr_name);
                                ImageView iv = (ImageView) inflatedRow.findViewById(R.id.iv_ctgr_indicator);

                                tv.setText(subsubCategoriesList.get(k).getName());
                                iv.setImageDrawable(getResources().getDrawable(R.drawable.radio_bt));


                                int finalK = k;
                                inflatedRow.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mCategoryName = subsubCategoriesList.get(finalK).getName();
                                        subSubCtgr = mCategoryName;
                                        mCategoryPath = rootCtgr + "/" + subCtgr + "//" + subSubCtgr;

                                        updateCtgrBoxHeader(mCategoryName);
                                        iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_svg));
                                        setRadioBtToAllOtherRowsWhichHadThemOriginally(inflatedRow);

                                    }
                                });
                            }
                        }
                    }
                }
            }
        }

    }

    private void setUpTheHeaderBackbutton(boolean makeThehdrBtVisiblityGone) {
//        makeThehdrBtVisiblityGone is true when the list of ctgr is shown ..so logically we cant go back than that

        ImageButton btGoBackwards = (ImageButton) containerViewGroup.findViewById(R.id.id_fr_bt_bottom_sheet_back);
        if (makeThehdrBtVisiblityGone == true) {
            btGoBackwards.setVisibility(View.GONE);
        } else {
            btGoBackwards.setVisibility(View.VISIBLE);

            btGoBackwards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //below if means if the ctgr box is showing the subctgrlist
                    if (mCategoryPath != "null initially" && mCategoryPath.indexOf("/") == -1) {


                        mCategoryPath = "null initially";
                        //showing rotation in baackward direction
                        LinearLayout ll_container_side_bar = (LinearLayout) containerViewGroup.findViewById(R.id.container_fr_ctgr);
                        TranslateAnimation animate = new TranslateAnimation(0, ll_container_side_bar.getWidth(), 0, 0);
                        animate.setDuration(300);
                        ll_container_side_bar.startAnimation(animate);
                        animate.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                loadCategorylayoutsInTheSidebar();

                                TranslateAnimation animate = new TranslateAnimation(-ll_container_side_bar.getWidth(), 0, 0, 0);
                                animate.setDuration(300);
                                ll_container_side_bar.startAnimation(animate);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });


                    } else  if ( mCategoryPath.indexOf("/") != -1) {

                        mCategoryPath = rootCtgr ;
                        //showing rotation in baackward direction
                        LinearLayout ll_container_side_bar = (LinearLayout) containerViewGroup.findViewById(R.id.container_fr_ctgr);
                        TranslateAnimation animate = new TranslateAnimation(0, ll_container_side_bar.getWidth(), 0, 0);
                        animate.setDuration(300);
                        ll_container_side_bar.startAnimation(animate);
                        animate.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                loadCategorylayoutsInTheSidebar();

                                TranslateAnimation animate = new TranslateAnimation(-ll_container_side_bar.getWidth(), 0, 0, 0);
                                animate.setDuration(300);
                                ll_container_side_bar.startAnimation(animate);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });


                    }
                }
            });

        }
    }

    private void updateCtgrBoxHeader(String mCategoryNamee) {
        TextView tvHeaderOfCtgrBox =(TextView)containerViewGroup.findViewById(R.id.id_br_bottom_sheet_tv_header);
        tvHeaderOfCtgrBox.setText(mCategoryNamee);


    }

    private void setRadioBtToAllOtherRowsWhichHadThemOriginally(View inflatedRow) {
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
//===================//now check if image is selected from storage/gallery amd the single and multiple img picking was allowed
            if (requestCode == PICKUP_MULTIPLE_IMAGE_FROM_STORAGE && resultCode == Activity.RESULT_OK && data != null) {
                // Get the Image from data

                //below id detetcs when single image is detetcted
                if (data.getData() != null) {

                    Bitmap bitmap = null;
                    Uri imageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                    singleImageBitmap = bitmap;

                    mPresenter.onSingleImageSelectedOrCaptured(singleImageBitmap);

                } else {
                    //below id detetcs when single image is detetcted
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Bitmap> mArrayBitmap = new ArrayList<Bitmap>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            Bitmap bitmap = null;
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            singleImageBitmap = bitmap;
                            mArrayBitmap.add(singleImageBitmap);
                        }
                        multipleImageBitmap = mArrayBitmap;
                        Log.i("LOG_TAG", "Selected Images" + multipleImageBitmap.size());
                        if (multipleImageBitmap.size() <= allowedImagesAmountForPickup) {
                            mPresenter.onMultipleImagesSelectedFromGallery(multipleImageBitmap);
                        } else {
                            showToast("images selected are more than allowed ie..5");
                            Log.i(TAG, "images selected are more than allowed ");
                        }
                    }
                }
                //===================//now check if image is captured from camera
            } else if (requestCode == CAPTURE_IMAGE_FROM_CAMERA && resultCode == Activity.RESULT_OK && data != null) {

                Bitmap bitmap = null;
                try {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    singleImageBitmap = bitmap;
                    mPresenter.onSingleImageSelectedOrCaptured(singleImageBitmap);
                } catch (Exception e) {
                    showToast("Some problem occured");
                    Log.e("Camera", e.toString());
                }
//===================//now check if image is selected from storage amd the single img picking was allowed due to edit button click
            } else if (requestCode == PICKUP_SINGLE_IMAGE_FROM_STORAGE && resultCode == Activity.RESULT_OK && data != null) {

                Bitmap bitmap = null;
                try {
                    Uri imageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    singleImageBitmap = bitmap;
                    mPresenter.onEditBtOfAnyImageViewIsClickedAndBitmapHasReturned(singleImageBitmap, indexOfIvOfWhichEditBtWasClicked);
                } catch (Exception e) {
                    showToast("Some problem occured");
                    Log.e("Camera", e.toString());
                }
            } else {
                showToast("No Images Picked");
            }
        } catch (Exception e) {
            showToast("Something went wrong");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onBackButtonPressed() {

        getFragmentManager().beginTransaction().remove(ItemsDetailsTakingFragment.this).commit();
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
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

}
