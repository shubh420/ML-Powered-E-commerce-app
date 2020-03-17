package io.shubh.e_commver1.Main.Presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import io.shubh.e_commver1.CategoriesObjectClass;
import io.shubh.e_commver1.ClassForMainActvityItemReclrDATAObject;
import io.shubh.e_commver1.Main.Interactor.MainInteractor;
import io.shubh.e_commver1.Main.View.MainView;
import io.shubh.e_commver1.Models.Category;
import io.shubh.e_commver1.StaticClassForGlobalInfo;

public class MainPresenterImplt implements MainPresenter, MainInteractor.CallbacksToPresnter {

    private MainView mainView;
    private MainInteractor mInteractor;

    FirebaseFirestore db;

    List<String> categories_names;
    List<List<CategoriesObjectClass>> super_nested_list_of_categories;
    List<String> category_order;

    ArrayList<ClassForMainActvityItemReclrDATAObject> listOfDataObjectsForAdapter = new ArrayList<>();


    public MainPresenterImplt(MainView splashview , MainInteractor mSplashInteractor) {
       this.mainView=splashview;
       this.mInteractor = mSplashInteractor;

       mInteractor.init(this);
        db = FirebaseFirestore.getInstance();

        mainView.showProgressBar(true);
        getAllCatgrFromFirestoreAndStoreItInStaticGlobalInfoClass();


    }

    private void getAllCatgrFromFirestoreAndStoreItInStaticGlobalInfoClass() {

        mInteractor.getAllCatgrFromFirestoreWithArgAsCallbackFunction(new MainInteractor.SeparateCallbackToPresnterAfterGettingcategories() {
            @Override
            public void onFinishedGettingCategories(ArrayList<Category> categoriesList) {

                Log.i("&&&&", "categories retrived" + categoriesList.toString());
                printCategories(categoriesList);

                StaticClassForGlobalInfo.categoriesList=categoriesList;

                makeDataObjectListForReclrViewAdapter(categoriesList);

                mainView.showProgressBar(false);
                mainView.loadtheUI();
                mainView.setReclrViewToshowCtgrs(listOfDataObjectsForAdapter);

            }
        });


        //=============================================================



    }

    private void makeDataObjectListForReclrViewAdapter(ArrayList<Category> categoriesList) {

        for(int i=0;i<categoriesList.size() ; i++){

            ClassForMainActvityItemReclrDATAObject reclrDATAObject =new ClassForMainActvityItemReclrDATAObject();
            reclrDATAObject.setItem_image_url(categoriesList.get(i).getImageURL());
            reclrDATAObject.setItem_title(categoriesList.get(i).getName());

            listOfDataObjectsForAdapter.add(reclrDATAObject);
        }
    }

    private void printCategories(ArrayList<Category> categoriesList) {


        for(int i=0;i<categoriesList.size() ; i++){

            Log.i("****", "Catgr name :"+categoriesList.get(i).getName());
            if(categoriesList.get(i).isHaveSubCatgr()) {
                Log.i("****", "Catgr name :" + categoriesList.get(i).getName() + "have sub catgrs");


                for (int j = 0; j < categoriesList.get(i).getSubCategoriesList().size(); j++) {
                    Log.i("****", "     Catgr name " + categoriesList.get(i).getName() + "has subctgr" +
                            categoriesList.get(i).getSubCategoriesList().get(j).getName());

                    if(categoriesList.get(i).getSubCategoriesList().get(j).isHaveSubSubCatgr()) {
                        Log.i("****", "sub Catgr name :" +   categoriesList.get(i).getSubCategoriesList().get(j).getName()
                                + " have sub sub catgrs");


                        for (int k = 0; k < categoriesList.get(i).getSubCategoriesList().get(j).getSubSubCategoryList().size(); k++) {
                            Log.i("****", "   sub Catgr name" + categoriesList.get(i).getSubCategoriesList().get(j).getName() +
                                    " has subsubctgr" + categoriesList.get(i).getSubCategoriesList().get(j).getSubSubCategoryList().get(k).getName());

                        }
                    }

                }
            }
        }
    }


    @Override
    public void LoginRelatedWork() {

    }

    @Override
    public void checkIfUserIsASellerOrNot() {

        mainView.showProgressBarOfDrwrBtSwitchToSeller(true);

        if (StaticClassForGlobalInfo.isLoggedIn == true){

           mInteractor.checkIfUserisASellerOrNot(new MainInteractor.SeparateCallbackToPresnterAftercheckingIfUserASellerOrNot() {
               @Override
               public void onFinishedChecking(Boolean result) {
                   if (result == true) {
                       mainView.switchActivity(2);
                       mainView.showProgressBarOfDrwrBtSwitchToSeller(false);
                   } else {
                       mainView.switchActivity(3);
                       mainView.showProgressBarOfDrwrBtSwitchToSeller(false);
                   }
               }
           });
        }else {
         //   Toast.makeText(MainActivity.this, "Not Logged In ...make a dialog appear later having 2 buttons ,one for login and other as 'cancel'", Toast.LENGTH_SHORT).show();
        mainView.showToast("Not Logged In");
        mainView.showProgressBarOfDrwrBtSwitchToSeller(false);
        //TODO .make a dialog appear later having 2 buttons ,one for login and other as 'cancel'
        }
    }


    @Override
    public void onFinishedCheckingSomething1() {
//this is call from interactor
    }


}