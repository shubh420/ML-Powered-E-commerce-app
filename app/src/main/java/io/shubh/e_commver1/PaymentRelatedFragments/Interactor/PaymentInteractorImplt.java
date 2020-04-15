package io.shubh.e_commver1.PaymentRelatedFragments.Interactor;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.shubh.e_commver1.Models.Order;
import io.shubh.e_commver1.StaticClassForGlobalInfo;

public class PaymentInteractorImplt implements PaymentInteractor {

    FirebaseFirestore db;
    CallbacksToPresnter mPresenter;

    String TAG = "####";

    @Override
    public void init(CallbacksToPresnter mPresenter) {
        db = FirebaseFirestore.getInstance();
        this.mPresenter = mPresenter;
    }


    public void getLastOrderIdWithArgAsCallbackFunction(SeparateCallbackToPresnterAfterGettingLastOrderId l) {

        DocumentReference pg = db.collection("variables").document("id no for order(last order uploaded))");
        pg.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    String orderId = (String) doc.get("id no");

                    l.onFinished(true, orderId);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Log.e(TAG, "onFailure: " + e.getMessage());
                String emptyString = null;
                l.onFinished(false, emptyString);
            }
        });
    }


    @Override
    public void uploadOrderInDatabaseWithArgAsCallbackFunction(Order order, SeparateCallbackToPresnterAfterOrderUpload l) {

        db.collection("orders").document(String.valueOf(order.getOrderId()))
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        l.onFinished(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error uploading image", e);
                l.onFinished(false);

            }
        });

    }


    //below function is recursive
    @Override
    public void setSubItemsInsubCollectionWithArgAsCallbackFunction(int orderOfCall, Order order, ArrayList<Order.SubOrderItem> subOrderItems, SeparateCallbackToPresnterAfterUploadingSubCollections l) {

        if (orderOfCall < subOrderItems.size()) {

            db.collection("orders").document(order.getOrderId()).collection("sub order items").document(subOrderItems.get(orderOfCall).getItemId())
                    .set(subOrderItems.get(orderOfCall))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("####", "ityem uploadwd ");

                            if(orderOfCall !=subOrderItems.size() -1 ){
                                setSubItemsInsubCollectionWithArgAsCallbackFunction(orderOfCall+1,order,subOrderItems ,l);
                            }else{
                                l.onFinished(true);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error uploading image", e);
                            l.onFinished(false);
                        }
                    });
        } else {
            l.onFinished(true);
        }
    }

    @Override
    public void updateLastOrderIdWithArgAsCallbackFunction(String finalOrderId, SeparateCallbackToPresnterAfterUpdatingLastOrdreId l) {
        Map<String, Object> user_node_data = new HashMap<>();
        user_node_data.put("id no", finalOrderId);

        db.collection("variables").document("id no for order(last order uploaded))")
                .set(user_node_data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");

                        l.onFinished(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                        l.onFinished(false);
                    }
                });
    }

    @Override
    public void deleteAllBagItemsWithArgAsCallbackFunction(Order order, SeparateCallbackToPresnterAfterDeletingBagItems l) {

        WriteBatch writeBatch = db.batch();

        for (int i=0;i<order.getBagItems().size();i++){
            DocumentReference documentReference = db.collection("bag or cart items").document(order.getBagItems().get(i).getUserId() +order.getBagItems().get(i).getItemId());
            writeBatch.delete(documentReference);
        }

        writeBatch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
       l.onFinished(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                l.onFinished(true);
            }
        });
    }


}