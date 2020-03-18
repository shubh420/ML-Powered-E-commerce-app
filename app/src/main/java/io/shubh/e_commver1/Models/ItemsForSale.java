package io.shubh.e_commver1.Models;

import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemsForSale implements Serializable {


    String category , description ,item_price , name ,root_category ,seller_id ,sub_category ,sub_sub_category ;
    int order_id ;
    long time_of_upload ;



   // List<String> listOfImageURLs = new ArrayList<String>();

    public ItemsForSale() {
    }

    @PropertyName("category")
    public String getCategory() {
        return category;
    }
    @PropertyName("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @PropertyName("description")
    public String getDescription() {
        return description;
    }
    @PropertyName("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @PropertyName("item price")
    public String getItem_price() {
        return item_price;
    }
    @PropertyName("item price")
    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }
    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("root category")
    public String getRoot_category() {
        return root_category;
    }
    @PropertyName("root category")
    public void setRoot_category(String root_category) {
        this.root_category = root_category;
    }

    @PropertyName("seller id")
    public String getSeller_id() {
        return seller_id;
    }
    @PropertyName("seller id")
    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    @PropertyName("sub category")
    public String getSub_category() {
        return sub_category;
    }
    @PropertyName("sub category")
    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    @PropertyName("sub sub category")
    public String getSub_sub_category() {
        return sub_sub_category;
    }
    @PropertyName("sub sub category")
    public void setSub_sub_category(String sub_sub_category) {
        this.sub_sub_category = sub_sub_category;
    }

    @PropertyName("order id")
    public int getOrder_id() {
        return order_id;
    }
    @PropertyName("order id")
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @PropertyName("time of upload")
    public long getTime_of_upload() {
        return time_of_upload;
    }
    @PropertyName("time of upload")
    public void setTime_of_upload(long time_of_upload) {
        this.time_of_upload = time_of_upload;
    }


    //TODO -change the method of storing images url in document..right now ,I store thrm inside of
    // nested collection insdie a document..but change it to array field..and then uncommentize the below later
  /*  @PropertyName("uploaded images urls")
    public List<String> getListOfImageURLs() {
        return listOfImageURLs;
    }
    @PropertyName("uploaded images urls")
    public void setListOfImageURLs(List<String> listOfImageURLs) {
        this.listOfImageURLs = listOfImageURLs;
    }*/

  /* // @PropertyName("uploaded images urls")
    public List<String> getListOfImageURLs() {
        return listOfImageURLs;
    }
    //@PropertyName("uploaded images urls")
    public void setListOfImageURLs(List<String> listOfImageURLs) {
        this.listOfImageURLs = listOfImageURLs;
    }*/
}
