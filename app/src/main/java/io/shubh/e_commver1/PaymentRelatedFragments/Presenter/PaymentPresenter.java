package io.shubh.e_commver1.PaymentRelatedFragments.Presenter;

import android.content.Context;

import io.shubh.e_commver1.Models.Order;

public interface PaymentPresenter {



     void startPayment(int totalPaymentAmount, Context context);

     void onSucessOfPayment(Order order, String s);

     void onFailiureOfPayment(Order order, String s);
}
