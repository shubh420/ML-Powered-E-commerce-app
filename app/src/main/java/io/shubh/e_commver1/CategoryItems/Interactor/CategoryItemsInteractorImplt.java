package io.shubh.e_commver1.CategoryItems.Interactor;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.shubh.e_commver1.CategoryItemsActivity;
import io.shubh.e_commver1.ClassForCategoryItemReclrDATAObject;
import io.shubh.e_commver1.Models.ItemsForSale;

public class CategoryItemsInteractorImplt implements CategoryItemsInteractor {

    FirebaseFirestore db ;
    CallbacksToPresnter mPresenter;
    int noOfItemsRetrivedTillNo=0;
    ArrayList<ItemsForSale> itemsList = new ArrayList<>();
    ItemsForSale lastItemRetrived=null;
    String  ctgrPath= null;

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
    public void getTheFirstItemDocumentAsAReferenceForStartAtFunct(String  ctgrName, String  ctgrPath) {
        Log.i("******", ctgrPath);
        itemsList.clear();

        if(lastItemRetrived==null)  {
            //the above if determines if its the first page of the recycler view.
            if(this.ctgrPath == null || this.ctgrPath != ctgrPath  ) {
                //the above if determines if a new call is made for different ctgr
                this.ctgrPath=  ctgrPath ;

                Query query = null;
                if (ctgrPath.indexOf('/') == -1) {
                    //means ctgrpath has ctgr only..no subctgr or subsubctgr
                    //this means we need to get all documents which belong to this ctgr
                    query = db.collection("items for sale")
                            .whereEqualTo("root category", ctgrPath)
                            .orderBy("time of upload", Query.Direction.ASCENDING)
                            .limit(1);
                } else if (ctgrPath.indexOf('/') != -1 && ctgrPath.indexOf("//") == -1) {
                    //means ctgrpath has subctgr no subsubctgr
                    //this means we need to get all documents which belong to this subctgr
                    query = db.collection("items for sale")
                            .whereEqualTo("sub category", ctgrName)
                            .orderBy("time of upload", Query.Direction.ASCENDING)
                            .limit(1);
                } else if (ctgrPath.indexOf('/') != -1 && ctgrPath.indexOf("//") != -1) {
                    //means ctgrpath has subsubctgr
                    //this means we need to get all documents which belong to this subsubctgr
                    query = db.collection("items for sale")
                            .whereEqualTo("sub sub category", ctgrName)
                            .orderBy("time of upload", Query.Direction.ASCENDING)
                            .limit(1);
                }

                query
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult() != null) {
                                        Log.i("******", String.valueOf(task.getResult().size()));


                                        if (task.getResult().size() != 0) {
                                            List<ItemsForSale> list = task.getResult().toObjects(ItemsForSale.class);

                                            itemsList.add(list.get(0));

                                            //adding image urls maually now -TODO make a arraylist field for it in firestore document later


                                            getItemsFromFirebaseWithResultsOnSeparateCallback(ctgrName,ctgrPath);
                                        } else {
                                            //TODO-show toast of no items found
                                        }


                                    }

                                } else {
                                    Log.e("CategoryItemsInteractor", "Error getting documents: ", task.getException());
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("CategoryItemsInteractor", "Error getting documents: ", e);


                            }
                        });
            }
        }else{
            getItemsFromFirebaseWithResultsOnSeparateCallback( ctgrName ,ctgrPath);
        }
    }

    @Override
    public void getItemsFromFirebaseWithResultsOnSeparateCallback( String  ctgrName, String  ctgrPath) {

       // ArrayList<ItemsForSale> list_of_data_objects__for_adapter = new ArrayList<>();


                this.ctgrPath = ctgrPath;

                Query query = null;
                if (ctgrPath.indexOf('/') == -1) {
                    //means ctgrpath has ctgr only..no subctgr or subsubctgr
                    //this means we need to get all documents which belong to this ctgr
                    query = db.collection("items for sale")
                            .whereEqualTo("root category", ctgrPath)
                            .orderBy("time of upload", Query.Direction.ASCENDING)
                            .limit(6)
                            .startAfter(lastItemRetrived);//<------This below function decides after which document ,all other documents need to be fetched
                    //for first time I will pass it the very first function,after that the last document i have

                } else if (ctgrPath.indexOf('/') != -1 && ctgrPath.indexOf("//") == -1) {
                    //means ctgrpath has subctgr no subsubctgr
                    //this means we need to get all documents which belong to this subctgr
                    query = db.collection("items for sale")
                            .whereEqualTo("sub category", ctgrName)
                            .orderBy("time of upload", Query.Direction.ASCENDING)
                            .limit(1)
                            .startAfter(lastItemRetrived);//<------This below function decides after which document ,all other documents need to be fetched
                    //for first time I will pass it the very first function,after that the last document i have

                } else if (ctgrPath.indexOf('/') != -1 && ctgrPath.indexOf("//") != -1) {
                    //means ctgrpath has subsubctgr
                    //this means we need to get all documents which belong to this subsubctgr
                    query = db.collection("items for sale")
                            .whereEqualTo("sub sub category", ctgrName)
                            .orderBy("time of upload", Query.Direction.ASCENDING)
                            .limit(1)
                            .startAfter(lastItemRetrived);//<------This below function decides after which document ,all other documents need to be fetched
                    //for first time I will pass it the very first function,after that the last document i have

                }

                query
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult() != null) {
                                        Log.i("******", String.valueOf(task.getResult().size()));


                                        if (task.getResult().size() != 0) {

                                            List<ItemsForSale> list = task.getResult().toObjects(ItemsForSale.class);

                                            for (int i = 0; i < list.size(); i++) {
                                                itemsList.add(list.get(i));

                                            }
                                            lastItemRetrived=list.get(list.size()-1);
                                            mPresenter.onFinishedGettingItems(list ,true);


                                            //adding image urls maually now -TODO make a arraylist field for it in firestore document later

                                        } else {
                                            //TODO-show toast of no items found
                                        }


                                    }

                                } else {
                                    Log.e("CategoryItemsInteractor", "Error getting documents: ", task.getException());
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("CategoryItemsInteractor", "Error getting documents: ", e);


                            }
                        });



    }



}
