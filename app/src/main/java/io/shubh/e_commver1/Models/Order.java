package io.shubh.e_commver1.Models;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    //below fields are the one which are used in app but are not sent to database
    @Exclude
    ArrayList<BagItem> bagItems;

    //------------------------------------------
    //below are sent to database
    AdressItem adressItem;

    long timeOfCreationOfOrder;
    long timeOfPaymentSuccessOfOrder;
    long timeOfCompletionOfDeliveryOfAllItems;

    String orderId;
    String buyerId;
    String transactionId;


    //if below var is 2 -that means ..order have payment done
    //if below var is 3 -that means ..all the items in order are delivered.
    //if below var is 0 -that means ..none of the above .
    int statusOfOrder = 0;


    public Order() {

    }

    ///below class is basically the indivisual items inside the collective  order
    //this class objects will be stored in the sub collection inside the order document
    public static class SubOrderItem {

        String itemAmount, itemId ,sellerId ,varietyName ,imageUrl , buyerId;;
        String selectedVarietyName = "null";
        long timeOfPackagedOfItem;
        long timeOfShippedOfItem;
        long timeOfDeliveryOfItem;
        long timeOfCancellationOfItem;

        //if below var is 2 -that means ..order have payment done
        //if below var is 3 -that means ..order is packaged and ready to be shipped .
        //if below var is 4 -that means ..order is shipped .
        //if below var is 5 -that means ..order is delivered .
        //if below var is 6 -that means ..order is cancelled by seller .
        //below var by default is 2 becuase ..this class objects will be created once the payment is done.
        int statusOfOrder = 2;


        @PropertyName("ItemAmount")
        public String getItemAmount() {
            return itemAmount;
        }
        @PropertyName("ItemAmount")
        public void setItemAmount(String itemAmount) {
            this.itemAmount = itemAmount;
        }

        @PropertyName("ItemId")
        public String getItemId() {
            return itemId;
        }
        @PropertyName("ItemId")
        public void setItemId(String itemId) {
            this.itemId = itemId;
        }



        @PropertyName("TimeOfPackagedOfItem")
        public long getTimeOfPackagedOfItem() {
            return timeOfPackagedOfItem;
        }
        @PropertyName("TimeOfPackagedOfItem")
        public void setTimeOfPackagedOfItem(long timeOfPackagedOfItem) {
            this.timeOfPackagedOfItem = timeOfPackagedOfItem;
        }

        @PropertyName("TimeOfShippedOfItem")
        public long getTimeOfShippedOfItem() {
            return timeOfShippedOfItem;
        }
        @PropertyName("TimeOfShippedOfItem")
        public void setTimeOfShippedOfItem(long timeOfShippedOfItem) {
            this.timeOfShippedOfItem = timeOfShippedOfItem;
        }

        @PropertyName("TimeOfDeliveryOfItem")
        public long getTimeOfDeliveryOfItem() {
            return timeOfDeliveryOfItem;
        }
        @PropertyName("TimeOfDeliveryOfItem")
        public void setTimeOfDeliveryOfItem(long timeOfDeliveryOfItem) {
            this.timeOfDeliveryOfItem = timeOfDeliveryOfItem;
        }

        @PropertyName("TimeOfCancellationOfItem")
        public long getTimeOfCancellationOfItem() {
            return timeOfCancellationOfItem;
        }
        @PropertyName("TimeOfCancellationOfItem")
        public void setTimeOfCancellationOfItem(long timeOfCancellationOfItem) {
            this.timeOfCancellationOfItem = timeOfCancellationOfItem;
        }

        @PropertyName("StatusOfOrder")
        public int getStatusOfOrder() {
            return statusOfOrder;
        }
        @PropertyName("StatusOfOrder")
        public void setStatusOfOrder(int statusOfOrder) {
            this.statusOfOrder = statusOfOrder;
        }

        @PropertyName("seller id")
        public String getSellerId() {
            return sellerId;
        }
        @PropertyName("seller id")
        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        @PropertyName("VarietyName")
        public String getVarietyName() {
            return varietyName;
        }
        @PropertyName("VarietyName")
        public void setVarietyName(String varietyName) {
            this.varietyName = varietyName;
        }

        @PropertyName("SelectedVarietyName")
        public String getSelectedVarietyName() {
            return selectedVarietyName;
        }
        @PropertyName("SelectedVarietyName")
        public void setSelectedVarietyName(String selectedVarietyName) {
            this.selectedVarietyName = selectedVarietyName;
        }

        @PropertyName("image url")
        public String getImageUrl() {
            return imageUrl;
        }
        @PropertyName("image url")
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @PropertyName("buyer id")
        public String getBuyerId() {
            return buyerId;
        }

        @PropertyName("buyer id")
        public void setBuyerId(String buyerId) {
            this.buyerId = buyerId;
        }

    }

    @Exclude
    public ArrayList<BagItem> getBagItems() {
        return bagItems;
    }
    @Exclude
    public void setBagItems(ArrayList<BagItem> bagItems) {
        this.bagItems = bagItems;
    }

    public void setAdressItem(AdressItem adressItem) {
        this.adressItem = adressItem;
    }

    public AdressItem getAdressItem() {
        return adressItem;
    }

    //--------------------------------------------------------------------------------


    @PropertyName("status of order")
    public int getStatusOfOrder() {
        return statusOfOrder;
    }

    @PropertyName("status of order")
    public void setStatusOfOrder(int statusOfOrder) {
        this.statusOfOrder = statusOfOrder;
    }


    @PropertyName("creation of order")
    public long getTimeOfCreationOfOrder() {
        return timeOfCreationOfOrder;
    }

    @PropertyName("creation of order")
    public void setTimeOfCreationOfOrder(long timeOfCreationOfOrder) {
        this.timeOfCreationOfOrder = timeOfCreationOfOrder;
    }


    @PropertyName("time of payment success of item")
    public long getTimeOfPaymentSuccessOfOrder() {
        return timeOfPaymentSuccessOfOrder;
    }

    @PropertyName("time of payment success of item")
    public void setTimeOfPaymentSuccessOfOrder(long timeOfPaymentSuccessOfOrder) {
        this.timeOfPaymentSuccessOfOrder = timeOfPaymentSuccessOfOrder;
    }

    @PropertyName("time of delivery of all item")
    public long getTimeOfCompletionOfDeliveryOfAllItems() {
        return timeOfCompletionOfDeliveryOfAllItems;
    }
    @PropertyName("time of delivery of all item")
    public void setTimeOfCompletionOfDeliveryOfAllItems(long timeOfCompletionOfDeliveryOfAllItems) {
        this.timeOfCompletionOfDeliveryOfAllItems = timeOfCompletionOfDeliveryOfAllItems;
    }


    @PropertyName("order id")
    public String getOrderId() {
        return orderId;
    }

    @PropertyName("order id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    @PropertyName("buyer id")
    public String getBuyerId() {
        return buyerId;
    }

    @PropertyName("buyer id")
    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }



    @PropertyName("transaction id")
    public String getTransactionId() {
        return transactionId;
    }

    @PropertyName("transaction id")
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}