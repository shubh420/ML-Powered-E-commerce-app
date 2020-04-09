package io.shubh.e_commver1.ItemDetailPage.Interactor;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import io.shubh.e_commver1.Models.BagItem;

public class ItemDetailInteractorImplt implements ItemDetailInteractor {

    FirebaseFirestore db ;
    CallbacksToPresnter mPresenter;



    @Override
    public void init(CallbacksToPresnter mPresenter) {
        db = FirebaseFirestore.getInstance();
        this.mPresenter =mPresenter;
    }

    @Override
    public void uploadBagItemWithArgAsCallbackFunction(BagItem bagItem, SeparateCallbackToPresnterAfterBagItemUpload l) {

        //naming the below document name have an advantage that ..if the user bags an item again which he has prev did
        //then the old doc in firestore  will get overwrited with the enw one
        String bagItemDocument=bagItem.getUserId()+bagItem.getItemId();

        db.collection("bag or cart items").document(bagItemDocument)
                .set(bagItem)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        l.onFinishedUploading(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        l.onFinishedUploading(false);
                    }
                });


    }


}
