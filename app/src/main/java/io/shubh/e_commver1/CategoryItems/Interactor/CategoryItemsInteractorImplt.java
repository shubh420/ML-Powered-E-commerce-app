package io.shubh.e_commver1.CategoryItems.Interactor;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import io.shubh.e_commver1.CategoryItemsActivity;
import io.shubh.e_commver1.ClassForCategoryItemReclrDATAObject;

public class CategoryItemsInteractorImplt implements CategoryItemsInteractor {

    FirebaseFirestore db ;
    CallbacksToPresnter mPresenter;

    @Override
    public void init(CallbacksToPresnter mPresenter) {
        db = FirebaseFirestore.getInstance();
        this.mPresenter =mPresenter;
    }

    @Override
    public void checkSomethingInDatabase() {



        //after checking something ..communicating the results back to presenter ..we have a call back
        mPresenter.onFinishedCheckingSomething1();
    }

    @Override
    public void getItemsFromFirebaseWithArgAsCallbackFunction(SeparateCallbackToPresnterAfterGettingItemsForRclrView l , String  ctgrPath) {

        ArrayList<ClassForCategoryItemReclrDATAObject> list_of_data_objects__for_adapter = new ArrayList<>();


        db.collection("items for sale")
                .whereEqualTo("category",ctgrPath ) // <-- This    line
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            size_of_items[0] = task.getResult().size();
                            if( size_of_items[0]==0){
                                Toast.makeText(CategoryItemsActivity.this, "No Items Found", Toast.LENGTH_SHORT).show();
                            }

                            for (DocumentSnapshot document : task.getResult()) {

                                ClassForCategoryItemReclrDATAObject data_object = new ClassForCategoryItemReclrDATAObject();

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                itemID.add(document.getId());

                                //retriving only these three things for now...later more feilds will be retrived specifically
                                data_object.setItem_id(document.getId());
                                data_object.setItem_title((String) document.get("name"));
                                data_object.setItem_price((String) document.get("item price"));

                                Log.d("&&&&&&&&&&1", (String) document.get("description"));
                                // Log.d("&&&&&&&&&&2",data_object.getItem_Descrp());

                                //adding dummy data into this position in master list...later this will be updated with correct data
                                list_of_data_objects__for_adapter.add(i, new ClassForCategoryItemReclrDATAObject());

                                //     this below line is amibigiious because function called willr un on sepatrate thread ,,thus I will Add urlt to below list inside the called function when data is retrived completely
                                //    data_object.setItem_image_url((retrive_all_the_item_image_url_in_a_list(document.getId()));

                                retrive_all_the_item_image_url_in_a_list(document.getId(), i, data_object, size_of_items[0] , list_of_data_objects__for_adapter);

                                i++;
                            }


//TODO- I should also order the item list as per the order no(as they are in increasing order as per their timing).......Do later


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });




        //after checking something ..communicating the results back to presenter ..we have a call back
        l.onFinishedGettingItems(true);
    }



}
