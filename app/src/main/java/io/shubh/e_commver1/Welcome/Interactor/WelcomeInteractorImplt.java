package io.shubh.e_commver1.Welcome.Interactor;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WelcomeInteractorImplt implements WelcomeInteractor {

    FirebaseFirestore db ;
    CallbacksToPresnter mPresenter;
    String TAG ="&&&&";


    @Override
    public void push_the_uid_to_users_node(Context context) {

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        //below code checks if the uid is alraedy in database or not to know whether its the first time login otr not

        db.collection("users").document(currentFirebaseUser.getUid())
        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        //user has alraedy used logged to this app in past
                        Log.d(TAG, "Document exists!");

                        mPresenter.onFinishedPushingTheUidToUsersNode();


                    } else {

                        //user is first time login

                        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);

                        Log.d(TAG, "user does not exist!");

                        Map<String, Object> user_node_data = new HashMap<>();
                        user_node_data.put("uid", currentFirebaseUser.getUid());
                        user_node_data.put("name", acct.getDisplayName());
                        user_node_data.put("email", acct.getEmail());
                        //below value is false by default
                        user_node_data.put("is a seller also ?", false);

                        db.collection("users").document(currentFirebaseUser.getUid())
                                .set(user_node_data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i(TAG, "DocumentSnapshot successfully written!");

                                        mPresenter.onFinishedPushingTheUidToUsersNode();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

    }

    @Override
    public void init(CallbacksToPresnter mPresenter) {
        db = FirebaseFirestore.getInstance();
        this.mPresenter =mPresenter;
    }

 /*   @Override
    public void checkSomethingInDatabase() {



        //after checking something ..communicating the results back to presenter ..we have a call back
       // mPresenter.onFinishedCheckingSomething1();
    }

    @Override
    public void getItemsFromFirebaseWithResultsOnSeparateCallback(SeparateCallbackToPresnterForSystemUpdate l) {

        //after checking something ..communicating the results back to presenter ..we have a call back
        l.onFinishedGettingItems(true);
    }
*/


}
