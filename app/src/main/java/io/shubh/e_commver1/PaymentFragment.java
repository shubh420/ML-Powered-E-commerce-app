package io.shubh.e_commver1;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import io.shubh.e_commver1.Models.BagItem;
import io.shubh.e_commver1.Models.Order;


public class PaymentFragment extends Fragment  {

    View containerViewGroup;
    LayoutInflater inflater;

    boolean isThisFragCalledFromAddressFrag;
    Order order;
    int totalPaymentAmount;

    String TAG = "####";


    public void setLocalVariables(boolean isThisFragCalledFromAddressFrag, Order order) {
        this.isThisFragCalledFromAddressFrag = isThisFragCalledFromAddressFrag;
        //if above value is false then a null bag item onject is given to the below one
        this.order = order;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        containerViewGroup = inflater.inflate(R.layout.fragment_payment, container, false);
        this.inflater = inflater;


        doUIWork();

        return containerViewGroup;
    }



    private void doUIWork() {
    setUpTextViews();
setUpToolbar();
attachOnBackBtPressedlistener();
    }

    private void setUpToolbar() {
        ImageButton btCloseFrag = (ImageButton) containerViewGroup.findViewById(R.id.btCloseFrag);
        btCloseFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackButtonPressed();
            }
        });

    }

    private void setUpTextViews() {

        int totalAmount = 0;
        int shippingCharge   = 100;
        int total  = 0;

        for (int i = 0; i < order.getBagItems().size(); i++) {
            totalAmount =totalAmount+  (Integer.valueOf(order.getBagItems().get(i).getItemObject().getItem_price())
            *(Integer.valueOf(order.getBagItems().get(i).getItemAmount())));

        }

        total= totalAmount+ shippingCharge;
        totalPaymentAmount = total;

        TextView tvSubtotal = (TextView) containerViewGroup.findViewById(R.id.tvSubtotal);
        tvSubtotal.setText("₹"+String.valueOf(totalAmount));
        TextView tvDiscount = (TextView) containerViewGroup.findViewById(R.id.tvDiscount);
        tvDiscount.setText(String.valueOf(0)+"%");
        TextView tvShipping = (TextView) containerViewGroup.findViewById(R.id.tvShipping);
        tvShipping.setText("₹"+String.valueOf(shippingCharge));
        TextView tvTotal = (TextView) containerViewGroup.findViewById(R.id.tvTotal);
        tvTotal.setText("₹"+String.valueOf(total));


        Button startpayment = (Button) containerViewGroup.findViewById(R.id.btCheckout);
        startpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    }



    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        //final Fragment fragment = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "BlueApp Software");
            options.put("description", "App Payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            String payment = String.valueOf(totalPaymentAmount);
            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            //TODO- if login is by using phone number then ...give it below
            //   preFill.put("contact", "9958584589");
            preFill.put("email", StaticClassForGlobalInfo.UserEmail);

            options.put("prefill", preFill);
            co.open((Activity) getContext(), options);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            Log.e(TAG, "startPayment: "+e.getMessage() );
        }
    }

   //The success and failiure listeners for the payment are in the main activity ...
    //so main activty will call below functions on the event

    public  void onPaymentSuccessCallbackFromMainActivty(String s){
        Log.i(TAG, s);
    }

    public void onPaymentFailiureCallbackFromMainActivty(String s){
        Log.i(TAG, s);
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

        getFragmentManager().beginTransaction().remove(PaymentFragment.this).commit();


    }

}








