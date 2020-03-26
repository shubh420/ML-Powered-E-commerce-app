package io.shubh.e_commver1.ItemsDetailsTakingFragment.Interactor;

import com.google.firebase.firestore.FirebaseFirestore;

public class ItemsDetailsTakingInteractorImplt implements ItemsDetailsTakingInteractor {

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
    public void checkSomethingInDatabaseWithArgAsCallbackFunction(SeparateCallbackToPresnterForSystemUpdate l) {

        //after checking something ..communicating the results back to presenter ..we have a call back
        l.onFinishedCheckingSystemUpdate(true);
    }



}