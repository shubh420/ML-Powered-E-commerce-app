package io.shubh.e_commver1.Welcome.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;


import io.shubh.e_commver1.Main.View.MainActivity;
import io.shubh.e_commver1.R;
import io.shubh.e_commver1.Welcome.Interactor.WelcomeInteractorImplt;
import io.shubh.e_commver1.Welcome.Presenter.WelcomePresenter;
import io.shubh.e_commver1.Welcome.Presenter.WelcomePresenterImplt;

public class WelcomeActivity extends AppCompatActivity implements WelcomeView {

    private String TAG = "WelcomeActivity";


    FirebaseFirestore db ;

    ProgressDialog progressDialog;

    WelcomePresenter mPresenter;
    ImageView bt_for_login;
    ProgressBar progressBar_g_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        db = FirebaseFirestore.getInstance();





        DoUiWork();

        //always do presenter related work at last in Oncreate
        mPresenter = new WelcomePresenterImplt(this, new WelcomeInteractorImplt() );

    }

    private void DoUiWork() {
        /*progressDialog = new ProgressDialog(WelcomeActivity.this);
        progressDialog.setTitle("Wait a sec please");*/
         progressBar_g_signin =(ProgressBar)findViewById(R.id.progressBar_g_signin);
        progressBar_g_signin.setVisibility(View.INVISIBLE);

         bt_for_login = (ImageView) findViewById(R.id.id_fr_iv_as_bt_google_signin);
        bt_for_login.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
               mPresenter.onSignInButtonClicked();
            }
        });

        setUpEmailLoginButton();
        setUpNotNowButton();

    }

    private void setUpNotNowButton() {

        LinearLayout notNowButton = (LinearLayout) findViewById(R.id.id_fr_ll_as_bt_not_now);
        notNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             mPresenter.onClickingNotNowButton();

                //animation for sliding activity
            //    overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    private void setUpEmailLoginButton() {
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

     mPresenter.onGettingActivityResultOfGoogleSignIn(requestCode,data);
    }





    @Override
    public void switchActivity(int i) {
       // progressBar.setVisibility(android.view.View.GONE);

        if(i==1) {
            Intent in = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(in);

            //animation for sliding activity
         //   overridePendingTransition(R.anim.right_in, R.anim.left_out);


        } else if(i==2){
            //  showToast("not logged in");
      /*      Intent in = new Intent(WelcomeActivity.this, WelcomeActivity.class);
            startActivity(in);*/
        }
    }

    @Override
    public Context getContext(boolean getActvityContext) {

        if(getActvityContext==true){
            return this;
        }
        return this.getApplicationContext();
    }

    @Override
    public void showProgressBar(boolean b) {
        if(b==true) {
            progressBar_g_signin.setVisibility(android.view.View.VISIBLE);
            bt_for_login.setClickable(false);
        }else {
            progressBar_g_signin.setVisibility(android.view.View.INVISIBLE);
            bt_for_login.setClickable(true);
        }
    }

    @Override
    public void ShowSnackBarWithAction(String msg, String actionName) {
        LinearLayout ll_Root = (LinearLayout)findViewById(R.id.layoutsplash);
        Snackbar snackbar = Snackbar
                .make(ll_Root , msg, BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction(actionName, new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View view) {
                        mPresenter.onSignInButtonClicked();

                    }
                });

        snackbar.show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(WelcomeActivity.this, msg, Toast.LENGTH_LONG).show    ();

    }

    @Override
    public void startActivityForResultt(Intent signInIntent, int RC_SIGN_IN) {
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public String getStringFromRes(int resId) {
                return getString(R.string.default_web_client_id);

    }


}
