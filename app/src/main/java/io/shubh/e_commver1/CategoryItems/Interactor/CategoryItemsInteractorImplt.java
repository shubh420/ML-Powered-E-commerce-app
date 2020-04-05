package io.shubh.e_commver1.CategoryItems.Interactor;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.shubh.e_commver1.Models.ItemsForSale;

public class CategoryItemsInteractorImplt implements CategoryItemsInteractor {

    FirebaseFirestore db ;
    CallbacksToPresnter mPresenter;
    //int noOfItemsRetrivedTillNo=0;

    int idodLastItemRetrived=0;
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
    public void getTheFirstItemDocumentAsAReferenceForStartAtFunct(String ctgrName, String ctgrPath,String rootCtgr, String subCtgr, String subSubCtgr, boolean ifItsALoadMorecall) {
        Log.i("******", "first call is made here for: "+ctgrPath+"-------------------------");
        ArrayList<ItemsForSale> itemsList = new ArrayList<>();

        if(ifItsALoadMorecall==false){
              //  this.ctgrPath=  ctgrPath ;

                Query query = null;
                if (ctgrPath.indexOf('/') == -1) {
                    //means ctgrpath has ctgr only..no subctgr or subsubctgr
                    //this means we need to get all documents which belong to this ctgr
                    query = db.collection("items for sale")
                            .whereEqualTo("root category", ctgrPath)
                            .orderBy("order id", Query.Direction.ASCENDING)
                            .limit(1);
                } else if (ctgrPath.indexOf('/') != -1 && ctgrPath.indexOf("//") == -1) {
                    //means ctgrpath has subctgr no subsubctgr
                    //this means we need to get all documents which belong to this subctgr
                    query = db.collection("items for sale")
                            .whereEqualTo("sub category", ctgrName)
                            .whereEqualTo("root category", rootCtgr)
                            .orderBy("order id", Query.Direction.ASCENDING)
                            .limit(1);
                } else if (ctgrPath.indexOf('/') != -1 && ctgrPath.indexOf("//") != -1) {
                    //means ctgrpath has subsubctgr
                    //this means we need to get all documents which belong to this subsubctgr
                    query = db.collection("items for sale")
                            .whereEqualTo("sub sub category", ctgrName)
                            .whereEqualTo("root category", rootCtgr)
                            .whereEqualTo("sub category", subCtgr)
                            .orderBy("order id", Query.Direction.ASCENDING)
                            .limit(1);
                }

                query
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult() != null) {


                                        if (task.getResult().size() != 0) {
                                            List<ItemsForSale> list = task.getResult().toObjects(ItemsForSale.class);

                                            itemsList.add(list.get(0));
                                            Log.i("***SizeOfo/pOf1stCall:", String.valueOf(itemsList.size()));

                                            idodLastItemRetrived=itemsList.get(0).getOrder_id();

                                            Log.i("******id", String.valueOf(itemsList.get(0).getOrder_id()));
                                            Log.i("******name", String.valueOf(itemsList.get(0).getName()));
                                            //adding image urls maually now -TODO make a arraylist field for it in firestore document later


                                            getItemsFromFirebaseWithResultsOnSeparateCallback(ctgrName,ctgrPath,rootCtgr,subCtgr,subSubCtgr,false  ,itemsList);
                                        } else {
                                            //TODO-show toast of no items found
                                            Log.i("***", "first call result has 0 size ");
                                            mPresenter.onFinishedGettingItems(itemsList, false, ctgrName ,ifItsALoadMorecall);

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

        }else{
            getItemsFromFirebaseWithResultsOnSeparateCallback( ctgrName ,ctgrPath ,rootCtgr,subCtgr,subSubCtgr,true ,itemsList);
        }
    }

    @Override
    public void getItemsFromFirebaseWithResultsOnSeparateCallback( String  ctgrName, String  ctgrPath,String rootCtgr, String subCtgr, String subSubCtgr, boolean ifItsALoadMorecall , ArrayList<ItemsForSale> itemsList ) {

       // ArrayList<ItemsForSale> list_of_data_objects__for_adapter = new ArrayList<>();
        Log.i("****", "2ndcall is made for:"+ctgrPath+"------------------------");

                this.ctgrPath = ctgrPath;
                int pageSize=0;
                if(ifItsALoadMorecall==true){pageSize=6;}else{pageSize=5;}

                Query query = null;
                if (ctgrPath.indexOf('/') == -1) {
                    //means ctgrpath has ctgr only..no subctgr or subsubctgr
                    //this means we need to get all documents which belong to this ctgr
                    query = db.collection("items for sale")
                            .whereEqualTo("root category", ctgrPath)
                            .orderBy("order id", Query.Direction.ASCENDING)
                            .startAfter(idodLastItemRetrived)//<------This below function decides after which document ,all other documents need to be fetched
                    //for first time I will pass it the very first function,after that the last document i have
                            //Also the field of orderBy and startafter should be same
                            .limit(pageSize);

                } else if (ctgrPath.indexOf('/') != -1 && ctgrPath.indexOf("//") == -1) {
                    //means ctgrpath has subctgr no subsubctgr
                    //this means we need to get all documents which belong to this subctgr
                    query = db.collection("items for sale")
                            .whereEqualTo("sub category", ctgrName)
                            .whereEqualTo("root category", rootCtgr)
                            .orderBy("order id", Query.Direction.ASCENDING)
                            .startAfter(idodLastItemRetrived)//<------This below function decides after which document ,all other documents need to be fetched
                            //for first time I will pass it the very first function,after that the last document i have
                            .limit(pageSize);
                } else if (ctgrPath.indexOf('/') != -1 && ctgrPath.indexOf("//") != -1) {
                    //means ctgrpath has subsubctgr
                    //this means we need to get all documents which belong to this subsubctgr
                    query = db.collection("items for sale")
                            .whereEqualTo("sub sub category", ctgrName)
                            .whereEqualTo("root category", rootCtgr)
                            .whereEqualTo("sub category", subCtgr)
                            .orderBy("order id", Query.Direction.ASCENDING)
                            .startAfter(idodLastItemRetrived)//<------This below function decides after which document ,all other documents need to be fetched
                            //for first time I will pass it the very first function,after that the last document i have
                            .limit(pageSize);
                }

                query
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult() != null) {
                                      //  Log.i("******", String.valueOf(task.getResult().size()));


                                        if (task.getResult().size() != 0) {

                                            List<ItemsForSale> list = task.getResult().toObjects(ItemsForSale.class);

                                            itemsList.addAll(list);

                                            Log.i("***SizeOfo/pOf2ndCall:", String.valueOf(itemsList.size()));
                                            for(int i=0;i<itemsList.size();i++) {
                                                Log.i("******id", String.valueOf(itemsList.get(i).getOrder_id()));
                                                Log.i("******name", itemsList.get(i).getName());
                                            }
                                            idodLastItemRetrived=itemsList.get(itemsList.size()-1).getOrder_id();
                                            mPresenter.onFinishedGettingItems(itemsList ,true, ctgrName,ifItsALoadMorecall);


                                            //adding image urls maually now -TODO make a arraylist field for it in firestore document later

                                        } else {

                                            if(itemsList.isEmpty()){
                                                if(ifItsALoadMorecall==true){
                                                    //TODO-showToast..of No more items found  ..after scrolling to bottom
                                                    mPresenter.showToast("No more Items found");
                                                }
                                            }else {
                                                Log.i("****", "Size is 0 of 2nd call ,but it has reached the 2nd call so so it has one item so display it");
                                                mPresenter.onFinishedGettingItems(itemsList, true, ctgrName ,ifItsALoadMorecall);
                                            }
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
