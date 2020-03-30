package io.shubh.e_commver1.ItemsDetailsTakingFragment.Interactor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.shubh.e_commver1.ItemToSellDetailsActivity;
import io.shubh.e_commver1.Models.ItemsForSale;
import io.shubh.e_commver1.seller_items_list;

public class ItemsDetailsTakingInteractorImplt implements ItemsDetailsTakingInteractor {

    FirebaseFirestore db;
    CallbacksToPresnter mPresenter;
    StorageReference mStorageRef;

    String TAG ="Item Details Taking Ans uploading Fragemnt";

    @Override
    public void init(CallbacksToPresnter mPresenter) {
        db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        this.mPresenter = mPresenter;
    }

    @Override
    public void getLastItemUploadedItemNo(SeparateSecondCallbackToPresnterForSystemUpdate l) {
        DocumentReference pg = db.collection("variables").document("id no for items for sale(last item uploaded))");
        pg.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    String Order_id = (String) doc.get("id no");

                    l.onFinishedGettingTheIdNo(Order_id);


                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });



    }


    //this below function is a recursive function
    @Override
    public void uploadImagesInStorageWithArgAsCallbackFunction(int i, String idForThisItem, ArrayList<String> dwnldImageUrls, ArrayList<Bitmap> bitmaps, SeparateCallbackToPresnterForSystemUpdate l) {

        if (i <= bitmaps.size()) {

            StorageReference filepath = mStorageRef.child("items for sale pics folder").child(String.valueOf(idForThisItem)).child(String.valueOf(i));

            //converting image to bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmaps.get(i-1).compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                //  filepath.getDownloadUrl();

               dwnldImageUrls.add( taskSnapshot.getStorage().getDownloadUrl().toString());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            });

        }else{
            l.onFinishedUploadingImages(dwnldImageUrls);
        }

    }

    @Override
    public void uploadItemFunctionWithArgAsCallbackFunction(ItemsForSale item, SeparateThirdCallbackToPresnterAfterItemUploaded l) {

        db.collection("items for sale").document(item.getOrder_id())
                .set(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        l.onFinishedUploadingItem();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }


    @Override
    public void updateLastItemUploadedIdVar(String idForThisItem, SeparateForthCallbackToPresnterAfterIIdUpdation l) {
        Map<String, Object> user_node_data = new HashMap<>();
        user_node_data.put("id no", idForThisItem);

        db.collection("variables").document("id no for items for sale(last item uploaded))")
                .set(user_node_data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");

                    l.onFinishedUpdatingId();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);

                    }
                });
    }

}
