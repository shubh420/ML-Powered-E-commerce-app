package io.shubh.e_commver1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseModelOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;
import com.google.firebase.ml.custom.model.FirebaseCloudModelSource;
import com.google.firebase.ml.custom.model.FirebaseLocalModelSource;
import com.google.firebase.ml.custom.model.FirebaseModelDownloadConditions;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

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

import io.shubh.e_commver1.Adapters.CustomPagerAdapterForSearchFragment;
import io.shubh.e_commver1.Main.View.MainActivity;


public class SearchActivity extends AppCompatActivity {

    int pageNoForMlFeature;

    String TAG = "SearchActivity";



    private FirebaseModelInterpreter mInterpreter;
    /**
     * Name of the model file hosted with Firebase.
     */
    private static final String HOSTED_MODEL_NAME = "cloud_model_1";
    private static final String LOCAL_MODEL_ASSET = "mobilenet_v1_1.0_224_quant.tflite";
    /**
     * Number of results to show in the UI.
     */
    private static final int RESULTS_TO_SHOW = 3;
    /**
     * Dimensions of inputs.
     */
    private static final int DIM_BATCH_SIZE = 1;
    private static final int DIM_PIXEL_SIZE = 3;
    private static final int DIM_IMG_SIZE_X = 224;
    private static final int DIM_IMG_SIZE_Y = 224;

    /* Preallocated buffers for storing image data. */
    private final int[] intValues = new int[DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y];
    private Bitmap mSelectedImage;

    /**
     * Data configuration of input & output data of model.
     */
    private FirebaseModelInputOutputOptions mDataOptions;

    /**
     * Labels corresponding to the output of the vision model.
     */
    private List<String> mLabelList;
    /**
     * Name of the label file stored in Assets.
     */
    private static final String LABEL_PATH = "labels.txt";

    private final PriorityQueue<Map.Entry<String, Float>> sortedLabels =
            new PriorityQueue<>(
                    RESULTS_TO_SHOW,
                    new Comparator<Map.Entry<String, Float>>() {
                        @Override
                        public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float>
                                o2) {
                            return (o1.getValue()).compareTo(o2.getValue());
                        }
                    });

    private Uri imageUri;
    TextView tv_output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       /* SearchViewInit();


        mSelectedImage =getBitmapFromAsset(this, "tennis.jpg");

        initViews();
        initCustomModel();*/
        //below function do the magic
       // runModelInference();


    }

  /*  private void SearchViewInit() {
        SearchView searchView =(SearchView)findViewById(R.id.searchview);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                startActivity(new Intent(SearchActivity.this, SearchResultsActivity.class));
                finish();

                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                String strName = null;
                i.putExtra("string", query);
                startActivity(i);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }


        });
//----------------------------------------------------------------

        CustomPagerAdapterForSearchFragment adapter = new CustomPagerAdapterForSearchFragment();
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);


    }

    private void initViews() {

        //LinearLayout bt_cmaera =(LinearLayout)findViewById(R.id.bt_camera);
        LinearLayout bt_gallery =(LinearLayout)findViewById(R.id.bt_gallery);


       *//* bt_cmaera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);

                pageNoForMlFeature = 1;
            }
        });*//*

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
        LinearLayout bt_cmaera2 =(LinearLayout)findViewById(R.id.bt_camera2);
     //   LinearLayout bt_gallery2 =(LinearLayout)findViewById(R.id.bt_gallery2);


        bt_cmaera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 2);

                 pageNoForMlFeature = 2;
            }
        });

    *//*    bt_gallery2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // pickIntent.setType("image/* video/*");
                pickIntent.setType("image/*");

                startActivityForResult(pickIntent, 3);

                pageNoForMlFeature = 2;
            }
        });*//*


    }

    private void initCustomModel() {
        mLabelList = loadLabelList(this);

        int[] inputDims = {DIM_BATCH_SIZE, DIM_IMG_SIZE_X, DIM_IMG_SIZE_Y, DIM_PIXEL_SIZE};
        int[] outputDims = {DIM_BATCH_SIZE, mLabelList.size()};
        try {
            mDataOptions =
                    new FirebaseModelInputOutputOptions.Builder()
                            .setInputFormat(0, FirebaseModelDataType.BYTE, inputDims)
                            .setOutputFormat(0, FirebaseModelDataType.BYTE, outputDims)
                            .build();
            FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions
                    .Builder()
                    .requireWifi()
                    .build();
            FirebaseCloudModelSource cloudSource = new FirebaseCloudModelSource.Builder
                    (HOSTED_MODEL_NAME)
                    .enableModelUpdates(true)
                    .setInitialDownloadConditions(conditions)
                    .setUpdatesDownloadConditions(conditions)  // You could also specify
                    // different conditions
                    // for updates
                    .build();
            FirebaseLocalModelSource localSource =
                    new FirebaseLocalModelSource.Builder("asset")
                            .setAssetFilePath(LOCAL_MODEL_ASSET).build();
            FirebaseModelManager manager = FirebaseModelManager.getInstance();
            manager.registerCloudModelSource(cloudSource);
            manager.registerLocalModelSource(localSource);
            FirebaseModelOptions modelOptions =
                    new FirebaseModelOptions.Builder()
                            .setCloudModelName(HOSTED_MODEL_NAME)
                            .setLocalModelName("asset")
                            .build();
            mInterpreter = FirebaseModelInterpreter.getInstance(modelOptions);
        } catch (FirebaseMLException e) {
            showToast("Error while setting up the model");
            e.printStackTrace();
        }
    }

    *//**
     * Reads label list from Assets.
     *//*
    private List<String> loadLabelList(AppCompatActivity activity) {
        List<String> labelList = new ArrayList<>();
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(activity.getAssets().open
                             (LABEL_PATH)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                labelList.add(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed to read label list.", e);
        }
        return labelList;
    }


    private void runModelInference() {
        if (mInterpreter == null) {
            Log.e(TAG, "Image classifier has not been initialized; Skipped.");
            return;
        }
        // Create input data.
        ByteBuffer imgData = convertBitmapToByteBuffer(mSelectedImage, mSelectedImage.getWidth(),
                mSelectedImage.getHeight());

        try {
            FirebaseModelInputs inputs = new FirebaseModelInputs.Builder().add(imgData).build();
            // Here's where the magic happens!!
            mInterpreter
                    .run(inputs, mDataOptions)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            Log.e(TAG,     e.getMessage() );
                            showToast("Error running model inference");
                        }
                    })
                    .continueWith(
                            new Continuation<FirebaseModelOutputs, List<String>>() {
                                @Override
                                public List<String> then(Task<FirebaseModelOutputs> task) {


                                    byte[][] labelProbArray = task.getResult()
                                            .<byte[][]>getOutput(0);
                                    List<String> topLabels = getTopLabels(labelProbArray);
                                    if(pageNoForMlFeature==1){
                                    // startActivity(new Intent(SearchActivity.this, SearchResultsActivity.class));


                                    Log.i("&&&&", topLabels.get(topLabels.size() - 1));
                                    String name = extractTheNamesWithoutProbabilityScoreFromTheList(topLabels);
                                    Log.i("&&&& name extracting", name);

                                    Intent ii = new Intent(SearchActivity.this, SearchResultsActivity.class);

                                    ii.putExtra("string", name);
                                    //page no indicates the ml kit feature
                                    ii.putExtra("page no", 1);
                                    startActivity(ii);

                                }
                                    return topLabels;

                                }
                            });
        } catch (FirebaseMLException e) {
            e.printStackTrace();
            showToast("Error running model inference");
        }

    }

    private String extractTheNamesWithoutProbabilityScoreFromTheList(List<String> labels) {

        String stringContainingAllLabels="";
        for(int i=0 ; i<labels.size() ; i++) {
            int iend = labels.get(i).indexOf(":"); //this finds the first occurrence of ":"

         stringContainingAllLabels=stringContainingAllLabels+"," + labels.get(i).substring(0 , iend);

        }
        Log.i("&&&&&", "Super string containing all labels : " +stringContainingAllLabels);

       return      stringContainingAllLabels;

    }

    *//**
     * Gets the top labels in the results.
     *//*
    private synchronized List<String> getTopLabels(byte[][] labelProbArray) {
        for (int i = 0; i < mLabelList.size(); ++i) {
            sortedLabels.add(
                    new AbstractMap.SimpleEntry<>(mLabelList.get(i), (labelProbArray[0][i] &
                            0xff) / 255.0f));
            if (sortedLabels.size() > RESULTS_TO_SHOW) {
                sortedLabels.poll();
            }
        }
        List<String> result = new ArrayList<>();
        final int size = sortedLabels.size();
        for (int i = 0; i < size; ++i) {
            Map.Entry<String, Float> label = sortedLabels.poll();
            result.add(label.getKey() + ":" + label.getValue());
        }
        Log.d(TAG, "labels: " + result.toString());
        return result;
    }
    *//**
     * Writes Image data into a {@code ByteBuffer}.
     *//*
    private synchronized ByteBuffer convertBitmapToByteBuffer(
            Bitmap bitmap, int width, int height) {
        ByteBuffer imgData =
                ByteBuffer.allocateDirect(
                        DIM_BATCH_SIZE * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE);
        imgData.order(ByteOrder.nativeOrder());
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, DIM_IMG_SIZE_X, DIM_IMG_SIZE_Y,
                true);
        imgData.rewind();
        scaledBitmap.getPixels(intValues, 0, scaledBitmap.getWidth(), 0, 0,
                scaledBitmap.getWidth(), scaledBitmap.getHeight());
        // Convert the image to int points.
        int pixel = 0;
        for (int i = 0; i < DIM_IMG_SIZE_X; ++i) {
            for (int j = 0; j < DIM_IMG_SIZE_Y; ++j) {
                final int val = intValues[pixel++];
                imgData.put((byte) ((val >> 16) & 0xFF));
                imgData.put((byte) ((val >> 8) & 0xFF));
                imgData.put((byte) (val & 0xFF));
            }
        }
        return imgData;
    }

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();


        InputStream is;
        Bitmap bitmap = null;
        try {
            is = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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

                        runModelInference();

                  //      imageView.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
            case 1:
                if (resultCode == Activity.RESULT_OK) {

                    Bitmap bitmap = null;
                    try {
                        Uri imageUri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                        mSelectedImage = bitmap;

                        runModelInference();

                   //     imageView.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
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
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
            case 3:
                if (resultCode == Activity.RESULT_OK) {

                    Bitmap bitmap = null;
                    try {
                        Uri imageUri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                        mSelectedImage = bitmap;

                        runImgTextDetection(bitmap);

                        //     imageView.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }



    }


    private void runImgTextDetection(Bitmap bitmap) {


            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionTextRecognizer textRecognizer =
                    FirebaseVision.getInstance().getOnDeviceTextRecognizer();
            textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    if (pageNoForMlFeature == 2) {
                        processTxt(firebaseVisionText);

                        //   startActivity(new Intent(SearchActivity.this, SearchResultsActivity.class));


                        Log.i("&&&&&&&&", "got firebaseVisiontext ");
                        Log.i("&&&&&&&&", firebaseVisionText.getText());

             *//*       Log.i("&&&&",topLabels.get(topLabels.size()-1) );
                    String name= extractTheNamesWithoutProbabilityScoreFromTheList(topLabels);
                    Log.i("&&&&",name);*//*

                        Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                        String strName = null;
                        i.putExtra("string", firebaseVisionText.getText());
                        //page no indicates the ml kit feature
                        i.putExtra("page no", 2);
                        startActivity(i);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });




    }

    private void processTxt(FirebaseVisionText firebaseVisionText) {
        Log.d("&&&&&&&&", "got firebaseVisiontext ");
        Log.d("&&&&&&&&", firebaseVisionText.getText());

    }


    @Override
    public void onBackPressed() {
        Intent in = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(in);


        //just adding an animatiion here whic makes it go with animation sliding to right
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }*/
}
