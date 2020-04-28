package io.shubh.e_commver1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import io.shubh.e_commver1.CategoryItems.View.CategoryItemsFragment;
import io.shubh.e_commver1.SellerDashboard.View.SellerDashboardFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SellerConfirmationFragment extends Fragment {
    //this fragment doesnt have factory methods like ''newinstance' method ..and bundle args passed
    View containerViewGroup;

    public SellerConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        containerViewGroup = inflater.inflate(R.layout.fragment_seller_confirmation, container, false);

        attachOnBackBtPressedlistener();


        Button btLater = (Button) containerViewGroup.findViewById(R.id.bt_later);
        btLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager().beginTransaction()
                        //both parameters for instantiating the fragment will be same as at rootl level of ctgr tree ,the name of ctgr and path is same
                        .add(R.id.drawerLayout, new SellerDashboardFragment())
                        .commit();

                //I have commented the below code because I m using tokens instead of topics for now
                //for the purpose of notifs but ..Tokens are also neccessary in case I nedd to give a
                //announcement kind of notif to every seller..but since this app is not have real purpose tus commented for now
                
               /* //Todo- this subscription below is invoked each time seller confirmation fragment is open
                //todo -so make the user become a seller for one time opnly...in the future
                //since the user has chose to become seller ..we need it to be subscribed to the notification meant for sellers
                FirebaseMessaging.getInstance().subscribeToTopic("notificationsForSellers").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "some thing went wrong", Toast.LENGTH_LONG).show();

                    }
                });*/
            }
        });

        ImageButton btCloseFrag = (ImageButton) containerViewGroup.findViewById(R.id.btCloseFrag);
        btCloseFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackButtonPressed();

            }
        });


        return containerViewGroup;
    }


    //below function is for catching back button pressed
    private void attachOnBackBtPressedlistener() {
        containerViewGroup.setFocusableInTouchMode(true);
        containerViewGroup.requestFocus();
        containerViewGroup.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    onBackButtonPressed();
                    return true;
                }
                return false;
            }
        });
    }

    private void onBackButtonPressed() {
        //since the fragment is about to be destroyed..I will check if this fragemnt is the ony one opened or not
        //if its the only one opened ..that means after closing it the mainactivty will appear ..so I have to enable the drawer layout open on swipe for the activty

       /* List<Fragment> fragments = getFragmentManager().getFragments();
        if(fragments.size()==1){
            drawerLayoutSttatic.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }*/

        //now closing this activty
        getFragmentManager().beginTransaction().remove(SellerConfirmationFragment.this).commit();
    }

}
