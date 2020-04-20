package io.shubh.e_commver1.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import io.shubh.e_commver1.Models.Category;

public final class Utils {
    //  public static boolean isLoggedIn =false;
    public static boolean isUserLoggedIn() {
        boolean isLoggedIn = false;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //user is signed in
            isLoggedIn = true;
        } else {
            //user is not signed in
            isLoggedIn = false;
        }
        return isLoggedIn;
    }

}
