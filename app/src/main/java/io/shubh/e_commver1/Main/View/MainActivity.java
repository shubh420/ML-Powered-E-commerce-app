package io.shubh.e_commver1.Main.View;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import io.shubh.e_commver1.ClassForMainActvityItemReclrDATAObject;
import io.shubh.e_commver1.Main.Interactor.MainInteractorImplt;
import io.shubh.e_commver1.Main.Presenter.MainPresenter;
import io.shubh.e_commver1.Main.Presenter.MainPresenterImplt;
import io.shubh.e_commver1.R;
import io.shubh.e_commver1.SearchActivity;
import io.shubh.e_commver1.StaticClassForGlobalInfo;
import io.shubh.e_commver1.Welcome.View.WelcomeActivity;
import io.shubh.e_commver1.reclr_adapter_class_for_main_activity_items;
import io.shubh.e_commver1.seller_confirmation_activity;
import io.shubh.e_commver1.seller_items_list;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter mPresenter;

    //below 2 var for on back pressed
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;


    RecyclerView recyclerView;

    //below is the loading placeholder view
    ShimmerFrameLayout mShimmerViewContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DoUiWork();


        mPresenter = new MainPresenterImplt(this, new MainInteractorImplt());


    }

    private void DoUiWork() {

      //  progressBar = (ProgressBar) findViewById(R.id.id_fr_prggrs_bar_main_activity);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        navigationDrawerSetUp();
        setSearchViewWork();




    }

    @Override
    public void loadtheUI() {

        set_nav_dr_button_setup();

        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.id_fr_recycler_view_main_activity_items_list);
        recyclerView.setVisibility(View.VISIBLE);



    }


    private void setSearchViewWork() {
 CardView cv_search =(CardView)findViewById(R.id.cv_search);
 cv_search.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         startActivity(new Intent(MainActivity.this, SearchActivity.class));
         finish();

     }
 });

    }

    private void set_nav_dr_button_setup() {

        LinearLayout myProfileButton = (LinearLayout) findViewById(R.id.id_fr_nav_bt_profile);
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                finish();
                //do nothing
            }
        });
//=----------------------------------------

        LinearLayout switchToSellerbutton = (LinearLayout) findViewById(R.id.id_fr_nav_bt_switch);
        switchToSellerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.checkIfUserIsASellerOrNot();
            }
        });
//------------------------------------
//doinf a bit changes in ui first ..setting logou icon if user is logged in and vice versa

        TextView tv_fr_button_signin_in_navdr = (TextView)findViewById(R.id.id_fr_tv_nav_login_);
        ImageView iv_fr_icon_button_signin_in_navdr = (ImageView) findViewById(R.id.id_fr_iv_nav_login_icon);
        LinearLayout Login_or_logout_button = (LinearLayout) findViewById(R.id.id_fr_nav_bt_login_or_logout);

        if(StaticClassForGlobalInfo.isLoggedIn ==true){
            tv_fr_button_signin_in_navdr.setText("Logout");
            iv_fr_icon_button_signin_in_navdr.setImageResource(R.drawable.logout_icon_);
        }else{
            //by default both have login text and image ..so no changes
        }
        //doing some ui work now....the default height for this button linearlayout is different from others button because ll here is depending on child's heighth  and its icon aint android studio rather imported from web so just copying the height of prev button nad setting to it
      //  iv_fr_icon_button_signin_in_navdr.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,switchToSellerbutton.getHeight()));

         //doing actual backend work
        Login_or_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(StaticClassForGlobalInfo.isLoggedIn ==true){
                        //make user sign out and redirect to welcome screen in case he wants to switch id

                    FirebaseAuth.getInstance().signOut();

                    startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                    finish();

                    //animation for sliding activity
                    overridePendingTransition(R.anim.right_out, R.anim.left_in);
                }else{
                    startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                    finish();

                    overridePendingTransition(R.anim.right_out, R.anim.left_in);
                }

            }
        });

    }

    private void navigationDrawerSetUp() {



        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        final CoordinatorLayout content = (CoordinatorLayout) findViewById(R.id.content);



        //removing the feature where the main content of activity gets dark as the drawer opoens
        drawerLayout.setScrimColor(Color.TRANSPARENT);

        //this beolw ondrawer slide lets us push the main content when the drawer opens ...
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                content.setTranslationX(slideX);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

//-----------------------------
       ImageButton menu =(ImageButton)findViewById(R.id.id_fr_menu_bt);
       menu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               //below logic is commentisized because one the drawer is open ..then any touch outside of drwer will closes the drawer ...
              /* if(is_nav_drawer_open == false){
                   is_nav_drawer_open=true;
                   drawerLayout.openDrawer(Gravity.LEFT);

               }else if(is_nav_drawer_open == true){
                   is_nav_drawer_open=false;
                   drawerLayout.closeDrawer(Gravity.LEFT);

               }*/
               drawerLayout.openDrawer(Gravity.LEFT);

           }
       });


//Setting up the name ,email in drawer if user is not guest user.......do profile pic later

        if(StaticClassForGlobalInfo.isLoggedIn !=false) {
            TextView tv_name = (TextView) findViewById(R.id.tv_for_nav_dr_name);
            TextView tv_email = (TextView) findViewById(R.id.tv_for_nav_dr_email);

            tv_name.setText(StaticClassForGlobalInfo.UserName);
            tv_email.setText(StaticClassForGlobalInfo.UserEmail);
        }
    }




    @Override
    public void onBackPressed() {
        //below code is for "click two time to exit the application"
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            finishAffinity();
            System.exit(0);
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }
        mBackPressed = System.currentTimeMillis();

        //just adding an animatiion here whic makes it go with animation sliding to right
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }


    @Override
    public void switchActivity(int i) {
       // progressBar.setVisibility(android.view.View.GONE);

        if(i==1) {
            Intent in = new Intent(MainActivity.this, MainActivity.class);
            startActivity(in);
        } else if(i==2){

            Intent in = new Intent(MainActivity.this, seller_items_list.class);
            startActivity(in);

            //animation for sliding activity
          //  overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } else if(i==3){
            Intent in = new Intent(MainActivity.this, seller_confirmation_activity.class);
            startActivity(in);

            //animation for sliding activity
            //  overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
         //   progressBar.setVisibility(android.view.View.VISIBLE);
            mShimmerViewContainer.startShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.VISIBLE);
        }else {
          //  progressBar.setVisibility(android.view.View.INVISIBLE);
            mShimmerViewContainer.stopShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.GONE);

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
                    //    mPresenter.LoginRelatedWork();

                    }
                });

        snackbar.show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show    ();

    }

    @Override
    public void setReclrViewToshowCtgrs(ArrayList<ClassForMainActvityItemReclrDATAObject> listOfDataObjectsForAdapter) {
        //now executing the UI part
        recyclerView = (RecyclerView) findViewById(R.id.id_fr_recycler_view_main_activity_items_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.removeAllViews();

      /*  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CategoryItemsActivity.this);
        recyclerView.setLayoutManager(layoutManager);*/

        // set a GridLayoutManager with 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView




        //data_list_for_adapter = list_of_data_objects__for_adapter;
        reclr_adapter_class_for_main_activity_items adapter = new reclr_adapter_class_for_main_activity_items(MainActivity.this, listOfDataObjectsForAdapter);
        recyclerView.setAdapter(adapter);


        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appBarLayout);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(-1)) {
                    appBarLayout.setElevation(50f);
                } else {
                    appBarLayout.setElevation(0f);
                }
            }
        });



        if (listOfDataObjectsForAdapter.size() == 0) {
            Toast.makeText(this, "No Categories found", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void showProgressBarOfDrwrBtSwitchToSeller(boolean b) {

        ImageView iv_icon_bt_drwr_switch_to_seller =(ImageView)findViewById(R.id.iv_icon_bt_drwr_switch_to_seller);
        ProgressBar progressBar_fr_bt_switch_to_seller = (ProgressBar) findViewById(R.id.progressBar_fr_bt_switch_to_seller);
        progressBar_fr_bt_switch_to_seller.setLayoutParams(new LinearLayout.LayoutParams(iv_icon_bt_drwr_switch_to_seller.getWidth(), iv_icon_bt_drwr_switch_to_seller.getHeight()));

        if(b==true) {
            progressBar_fr_bt_switch_to_seller.setVisibility(View.VISIBLE);

            iv_icon_bt_drwr_switch_to_seller.setVisibility(View.GONE);
        }else{

            progressBar_fr_bt_switch_to_seller.setVisibility(View.GONE);

            iv_icon_bt_drwr_switch_to_seller.setVisibility(View.VISIBLE);
        }
    }
}
