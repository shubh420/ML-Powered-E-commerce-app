package io.shubh.e_commver1.Splash.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import io.shubh.e_commver1.Main.View.MainActivity;
import io.shubh.e_commver1.R;
import io.shubh.e_commver1.Splash.Interactor.SplashInteractorImplt;
import io.shubh.e_commver1.Splash.Presenter.SplashPresenter;
import io.shubh.e_commver1.Splash.Presenter.SplashPresenterImplt;
import io.shubh.e_commver1.Welcome.View.WelcomeActivity;

public class SplashActivity extends AppCompatActivity implements Splashview {

    ImageView splashimageicon;

    ProgressBar progressBar;

    Boolean isInternetConnected ;


    SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //todo-when the app is open and then the view bt of notif is clicked then this activty opens but it doesnt load further ..so fix that
        Bundle extras = getIntent().getExtras();
        String type;
        if (extras != null) {
            type = extras.getString("type");
            Log.i("##", "splash  from notif ..type is "+type);
            // and get whatever type user account id is
        }else{
            Log.i("##", "splash not from notif ");
        }


        DoUiWork();

        //always do presenter related work at last in Oncreate
        mPresenter = new SplashPresenterImplt(this, new SplashInteractorImplt() {
        });



    }

    private void DoUiWork() {
        splashimageicon=(ImageView)findViewById(R.id.imagesplash);

        splashimageicon.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.simpleanimation);

         progressBar =(ProgressBar)findViewById(R.id.id_fr_prggrs_bar_splash_screen);

        splashimageicon.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


                //no code her cause it delays the animation start

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        showProgreeBar(true);
                        mPresenter.LoginRelatedWork();
                    }
                }, 0);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //----------------------------------
       // changing the status bar and system navigation color
                //system key color change
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.parseColor("#8BC34A"));
        }

            if (Build.VERSION.SDK_INT >= 21) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#8BC34A"));
            }
    }



    @Override
    public void SwitchActivity(int i) {
        //progressBar.setVisibility(View.GONE);

        if(i==1) {
            Intent in = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(in);
        } else if(i==2){
            //  showToast("not logged in");
            Intent in = new Intent(SplashActivity.this, WelcomeActivity.class);
            startActivity(in);
        }
    }

    @Override
    public Context getContext() {

        return this.getApplicationContext();
    }

    @Override
    public void showProgreeBar(boolean b) {
progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void ShowSnackBarWithAction(String msg, String actionName) {
        LinearLayout ll_Root = (LinearLayout)findViewById(R.id.layoutsplash);
        Snackbar snackbar = Snackbar
                .make(ll_Root , msg, BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction(actionName, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.LoginRelatedWork();

                    }
                });

        snackbar.show();
    }

    @Override
    public void showToast(String you_are_logged_in_already) {
        Toast.makeText(SplashActivity.this, you_are_logged_in_already, Toast.LENGTH_LONG).show    ();

    }
}