package io.shubh.e_commver1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.fxn.utility.PermUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.isapanah.awesomespinner.AwesomeSpinner;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ItemToSellDetailsActivity extends AppCompatActivity {

    FirebaseFirestore db;
    //AwesomeSpinner my_spinner;
    String category_selected_from_Bottomsheet;


    Boolean[] array_whether_image_view_have_image = {false, false, false, false, false};
    String[] image_uri_S = {"default", "default", "default", "default", "default"};
    private StorageReference mStorageRef;

    ProgressDialog progressDialog;

    BottomSheetBehavior behavior;
    CoordinatorLayout coordinatorLayout;

    String TAG = "%%%%%%%%%%%";

    Boolean is_bottom_sheet_expanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_to_sell_details);

        progressDialog = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

      /*  Button bt_fr_choose_images = (Button) findViewById(R.id.id_fr_bt_choose_images);


        bt_fr_choose_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (no_of_occupied_imageviews() != 5) {
                    Options options = Options.init()
                            .setRequestCode(100)                                                 //Request code for activity results
                            .setCount(5 - no_of_occupied_imageviews())                                                         //Number of images to restict selection count
                            .setFrontfacing(false)                                                //Front Facing camera on start
                            .setImageQuality(ImageQuality.HIGH)                                  //Image Quality
                            //        .setPreSelectedUrls(returnValue)                                     //Pre selected Image Urls
                            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)           //Orientaion
                            .setPath("/e-comm/images");                                             //Custom Path For Image Storage

                    Pix.start(ItemToSellDetailsActivity.this, options);
                } else {
                    Toast.makeText(ItemToSellDetailsActivity.this, "only 5 images can be selected. Long press on image to delete it.", Toast.LENGTH_LONG).show();

                }
            }
        });

        //===========================
        //        my_spinner = (AwesomeSpinner) findViewById(R.id.my_spinner);
        //  populate_bottom_sheet_with_firebase_data_catgr();

        setup_done_button();


        //--------------------------

        setupCategorySelectionStuff();*/

//==================================testing


    }

}
