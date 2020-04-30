package io.shubh.e_commver1.SearchPage;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.transition.Slide;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import com.google.firebase.ml.common.FirebaseMLException;


import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import com.google.firebase.ml.vision.label.FirebaseVisionLabel;

import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import io.shubh.e_commver1.Adapters.CustomPagerAdapterForItemDetailImageViewsPager;
import io.shubh.e_commver1.Adapters.CustomPagerAdapterForSearchFragment;
import io.shubh.e_commver1.Main.View.MainActivity;
import io.shubh.e_commver1.Models.BagItem;
import io.shubh.e_commver1.Models.ItemsForSale;
import io.shubh.e_commver1.Notification.Interactor.NotificationInteractorImplt;
import io.shubh.e_commver1.Notification.Presenter.NotificationPresenter;
import io.shubh.e_commver1.Notification.Presenter.NotificationPresenterImplt;
import io.shubh.e_commver1.Notification.View.NotificationFragment;
import io.shubh.e_commver1.R;
import io.shubh.e_commver1.SearchActivity;
import io.shubh.e_commver1.SearchPage.View.SearchResultsFragment;
import io.shubh.e_commver1.SearchResultsActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    String TAG = "SearchFragment";
    View containerViewGroup;
    LayoutInflater inflater;
    int pageNoForMlFeature;
    private Bitmap mSelectedImage;
    BottomSheetBehavior behaviorBotttomSheet;

    //  NotificationPresenter mPresenter;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        containerViewGroup = inflater.inflate(R.layout.fragment_search, container, false);
        this.inflater = inflater;

        /*mPresenter = new NotificationPresenterImplt(this, new NotificationInteractorImplt() {
        });*/


        DoUiWork();


        // Inflate the layout for this fragment
        return containerViewGroup;
    }

    private void DoUiWork() {

        //initializations here
        SearchViewInit();
        doPagerWork();
        initViews();
        setupBottomSheet();
        //initCustomModel();

        //---setups here
        //attachOnBackBtPressedlistener();


        //  setUpToolbar();
    }

    private void setupBottomSheet() {


        CoordinatorLayout rootView = (CoordinatorLayout) containerViewGroup.findViewById(R.id.cl_root);
        View inflatedBottomSheetdialog = inflater.inflate(R.layout.bottom_sheet_fr_ml_object_detect_results_fr_search_frag, rootView, false);
        rootView.addView(inflatedBottomSheetdialog);

        behaviorBotttomSheet = BottomSheetBehavior.from(inflatedBottomSheetdialog);

        behaviorBotttomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);

        View dim_background_of_bottom_sheet = (View) containerViewGroup.findViewById(R.id.touch_to_dismiss_bottom_sheet_dim_background);
        dim_background_of_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                behaviorBotttomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });

        behaviorBotttomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
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
    }

    private void SearchViewInit() {
        SearchView searchView = (SearchView) containerViewGroup.findViewById(R.id.searchview);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.clearFocus();

                SearchResultsFragment searchResultsFragment= new SearchResultsFragment();
                searchResultsFragment.setLocalVariables(getListOfNameKeywordsFromSentence(query));
                searchResultsFragment.setEnterTransition(new Slide(Gravity.RIGHT));
                searchResultsFragment.setExitTransition(new Slide(Gravity.RIGHT));
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.drawerLayout, searchResultsFragment ,"SearchResultsFragment")
                        .addToBackStack(null)
                        .commit();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }


        });
//----------------------------------------------------------------
/*
        CustomPagerAdapterForSearchFragment adapter = new CustomPagerAdapterForSearchFragment();
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);*/


    }


    private void doPagerWork() {


        ViewPager viewPager = (ViewPager) containerViewGroup.findViewById(R.id.pager3);
        CustomPagerAdapterForSearchFragment adapter = new CustomPagerAdapterForSearchFragment();
        viewPager.setAdapter(adapter);


        TabLayout tabLayout = (TabLayout) containerViewGroup.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);

        //rlVpContainer = (RelativeLayout) containerViewGroup.findViewById(R.id.rl_viewpager_container);


    }

    private void initViews() {

        LinearLayout bt_cmaera = (LinearLayout) containerViewGroup.findViewById(R.id.bt_camera1);
        LinearLayout bt_gallery = (LinearLayout) containerViewGroup.findViewById(R.id.bt_gallery1);


        bt_cmaera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking if we have camera permission
                if (TedPermission.isGranted(getContext(), Manifest.permission.CAMERA)) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 0);

                    pageNoForMlFeature = 1;


                } else {
                    //call for getting permission
                    getCameraPermissions();
                }
            }
        });

        bt_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // pickIntent.setType("image/* video/*");
                pickIntent.setType("image/*");

                startActivityForResult(pickIntent, 1);

                pageNoForMlFeature = 1;
            }
        });

        //======================================================================================================
        //page 2 views initialisation
        LinearLayout bt_cmaera2 = (LinearLayout) containerViewGroup.findViewById(R.id.bt_camera22);
        LinearLayout bt_gallery2 = (LinearLayout) containerViewGroup.findViewById(R.id.bt_gallery22);


        bt_cmaera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 2);

                pageNoForMlFeature = 2;
            }
        });

        bt_gallery2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // pickIntent.setType("image/* video/*");
                pickIntent.setType("image/*");

                startActivityForResult(pickIntent, 3);

                pageNoForMlFeature = 2;
            }
        });


    }

    private void getCameraPermissions() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                showToast("Permission Granted");
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                showToast("Permission Denied\n" + deniedPermissions.toString());
            }


        };


        TedPermission.with(getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA)
                .check();


    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {


                    Bitmap bitmap = null;
                    try {
                        bitmap = (Bitmap) data.getExtras().get("data");

                        mSelectedImage = bitmap;

                        //   runModelInference();
                        runImageLabeling(bitmap);


                        //      imageView.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        // showToast("Failed to load");
                        Log.e("Camera", e.toString());
                    }
                }
            case 1:
                if (resultCode == Activity.RESULT_OK) {

                    Bitmap bitmap = null;
                    try {
                        Uri imageUri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);

                        mSelectedImage = bitmap;

                        //  runModelInference();
                        runImageLabeling(bitmap);
                        //     imageView.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        //  showToast("Failed to load");
                        Log.e("Camera", e.toString());
                    }
                }

                //--==================================================

                //Page 2 views initialisation
            case 2:
                if (resultCode == Activity.RESULT_OK) {


                    Bitmap bitmap = null;
                    try {
                        bitmap = (Bitmap) data.getExtras().get("data");

                        mSelectedImage = bitmap;

                        runImgTextDetection(bitmap);

                        //      imageView.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        //  showToast("Failed to load");
                        Log.e("Camera", e.toString());
                    }
                }
            case 3:
                if (resultCode == Activity.RESULT_OK) {

                    Bitmap bitmap = null;
                    try {
                        Uri imageUri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);

                        mSelectedImage = bitmap;

                        runImgTextDetection(bitmap);

                        //     imageView.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        //  showToast("Failed to load");
                        Log.e("Camera", e.toString());
                    }
                }
        }


    }

    private void runImgTextDetection(Bitmap bitmap) {
        //the below if is because both the function of mlkit get called at the same time no matter called for what
        if (pageNoForMlFeature == 2) {



            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionTextRecognizer textRecognizer =
                    FirebaseVision.getInstance().getOnDeviceTextRecognizer();
            textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {

                    SearchResultsFragment searchResultsFragment= new SearchResultsFragment();
                    searchResultsFragment.setLocalVariables(getListOfNameKeywordsFromSentence(firebaseVisionText.getText()));
                    searchResultsFragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    searchResultsFragment.setExitTransition(new Slide(Gravity.RIGHT));
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .add(R.id.drawerLayout, searchResultsFragment,"SearchResultsFragment" )
                            .addToBackStack(null)
                            .commit();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showToast("Some prob occured in identifying the text");
                }
            });
        }

    }




    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    private void runImageLabeling(Bitmap bitmap) {

        if (pageNoForMlFeature == 1) {
         /*   TensorflowMlkitObjectDetection tensorflowMlkitObjectDetection = new TensorflowMlkitObjectDetection((AppCompatActivity) getContext(), (InterfaceForCallbackOnGettingLabelsAfterObjectDetection) getContext());
            tensorflowMlkitObjectDetection.runModelInference(bitmap);*/

            FirebaseVisionLabelDetectorOptions options = new FirebaseVisionLabelDetectorOptions.Builder()

//Set the confidence threshold//

                    .setConfidenceThreshold(0.7f)
                    .build();

//Create a FirebaseVisionImage object//

            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

//Create an instance of FirebaseVisionLabelDetector//

            FirebaseVisionLabelDetector detector =
                    FirebaseVision.getInstance().getVisionLabelDetector(options);

            detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionLabel>>() {
                @Override

//Implement the onSuccess callback//

                public void onSuccess(List<FirebaseVisionLabel> labels) {

                    showTheResultsInBottomSheet(labels);
               /* for (FirebaseVisionLabel label : labels) {

//Display the label and confidence score in our TextView//

                    Log.i("!!!!",  label.getLabel() + "\n");
                    Log.i("!!!!",  label.getConfidence() + "\n\n");
                }*/
                }

//Register an OnFailureListener//

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //      mTextView.setText(e.getMessage());
                    Log.i("!!!!", "error");
                }
            });


//keeping these below statements before the above ml relted code ..hinders the ml code getting executed
            behaviorBotttomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);

        }
    }


    private void showTheResultsInBottomSheet(List<FirebaseVisionLabel> firebaseVisionLabels) {

        LinearLayout containerForDetectedItemsThroughMl = containerViewGroup.findViewById(R.id.containerForDetectedItemsThroughMl);
        containerForDetectedItemsThroughMl.removeAllViews();

        if(firebaseVisionLabels.size()==0){
            showToast("No Object Identified");
        }

        for (FirebaseVisionLabel label : firebaseVisionLabels) {
            View row = inflater.inflate(R.layout.infalte_rows_fr_ml_results_search_frag, containerForDetectedItemsThroughMl, false);
            containerForDetectedItemsThroughMl.addView(row);

            TextView tvItemName = (TextView) row.findViewById(R.id.tvItemName);
            TextView tvItemProbability = (TextView) row.findViewById(R.id.tvItemProbability);

            tvItemName.setText(label.getLabel());
            tvItemProbability.setText(String.valueOf( label.getConfidence()*100.0 +"%"));

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SearchResultsFragment searchResultsFragment= new SearchResultsFragment();
                    searchResultsFragment.setLocalVariables(getListOfNameKeywordsFromSentence(label.getLabel()));
                    searchResultsFragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    searchResultsFragment.setExitTransition(new Slide(Gravity.RIGHT));
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .add(R.id.drawerLayout, searchResultsFragment,"SearchResultsFragment" )
                            .addToBackStack(null)
                            .commit();



                }
            });

        }


    }


    private List<String> getListOfNameKeywordsFromSentence(String name) {


        String[] words = name.split("\\s+");
        List<String> wordsList = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            // You may want to check for a non-word character before blindly
            // performing a replacement
            // It may also be necessary to adjust the character class
            words[i] = words[i].replaceAll("[^\\w]", "");//removes any puctuation like ?,!

            wordsList.add(words[i]);
        }


        return wordsList;
    }


    public void closeFragment() {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null).remove(SearchFragment.this).commit();


    }


}


