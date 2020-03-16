package io.shubh.e_commver1.Splash.Interactor;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import io.shubh.e_commver1.Splash.Interactor.SplashInteractor;

public class SplashInteractorImplt implements SplashInteractor {


  /*  FirebaseFirestore db ;
    CallbacksToPresnter mPresenter;


    @Override
    public void init(CallbacksToPresnter mPresenter) {
        db = FirebaseFirestore.getInstance();
        this.mPresenter =mPresenter;
    }

    @Override
    public void checkIfUserAlreadyExistsInDatabase() {

    }

    @Override
    public void checkForSystemUpdate(final SeparateCallbackToPresnterForSystemUpdate l) {


        DocumentReference docRef = db.collection("variables").document("system update");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        boolean b =  document.getBoolean("system update available");
                        l.onFinishedGettingItems(b);
                        Log.d("&&&", "system update is : " + b);

                    } else {
                        Log.d("LOGGER", "No such document as system update available");
                    }
                } else {
                    Log.d("LOGGER", "get failed with system update", task.getException());
                }
            }
        });




    }
*/

}
