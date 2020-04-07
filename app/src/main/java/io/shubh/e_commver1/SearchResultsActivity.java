package io.shubh.e_commver1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.shubh.e_commver1.Adapters.ReclrAdapterClassForCtgrItems;
import io.shubh.e_commver1.ItemDetailPage.View.ItemDetailFragment;
import io.shubh.e_commver1.Main.View.MainActivity;
import io.shubh.e_commver1.Models.ItemsForSale;

public class SearchResultsActivity extends AppCompatActivity {

    String searchQuery;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    int sizeOfSearchresult;
    int pageNoForMlFeature;
    ArrayList<ItemsForSale> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        db = FirebaseFirestore.getInstance();


        searchQuery = getIntent().getExtras().getString("string");
        Log.i("&&& name recieved", searchQuery);
        pageNoForMlFeature = getIntent().getExtras().getInt("page no");

        init();
        searchDatabaseForThisQuery();
        setSearchViewWork();
    }

    private void init() {

        searchQuery = searchQuery.replaceFirst(",", "");
        Log.i("&&&", "superstring after replacing first " + searchQuery);

        TextView tvSearchText = (TextView) findViewById(R.id.tv_search_text);
        tvSearchText.setText("Showing results for :" + searchQuery);

        if (pageNoForMlFeature == 1) {
            searchQuery = searchQuery.replace("  ", "");
        }
        Log.i("&&&", "superstring after replacing all ',' :" + searchQuery);
    }

    private void setSearchViewWork() {


        CardView cv_search = (CardView) findViewById(R.id.cv_search);
        TextView tvSearchBox = (TextView) findViewById(R.id.edt_search_text);
        // tvSearchBox.setText(searchQuery);

        cv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchResultsActivity.this, SearchActivity.class));
                finish();

            }
        });
    }

    private void searchDatabaseForThisQuery() {

        String[] searchQueries = getAllWordsFromSringInArray();
        Log.i("&&&", "search wuery list :" + Arrays.asList(searchQueries).toString());


        ArrayList<ItemsForSale> list_of_data_objects__for_adapter = new ArrayList<>();
        final int[] size_of_items = new int[1];

        //first getting all the items in the whole database
        {
            db.collection("items for sale")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {

                                    int i = 0;
                                    size_of_items[0] = task.getResult().size();


                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        String itemName = (String) document.get("name");
                                        for (int j = 0; j < searchQueries.length; j++) {
                                            Log.i("&&&&", " indivisual seatch query:" + searchQueries[j]);
                                            if (itemName.contains(searchQueries[j]) || itemName.toLowerCase().contains(searchQueries[j].toLowerCase())) {
                                                Log.d("&&&&", "true");


                                                List<ItemsForSale> list = task.getResult().toObjects(ItemsForSale.class);



                                      /*          //retriving only these three things for now...later more feilds will be retrived specifically
                                                data_object.se(document.getId());
                                                data_object.setItem_title((String) document.get("name"));
                                                data_object.setItem_price((String) document.get("item price"));*/

//adding dummy data into this position in master list...later this will be updated with correct data
                                                list_of_data_objects__for_adapter.add(list.get(i));
                                                Log.i("@@@", "list size :" + list.get(i).getName());
                                                //  retrive_all_the_item_image_url_in_a_list(document.getId(), i, data_object, list_of_data_objects__for_adapter);


                                            }
                                        }

                                        sizeOfSearchresult = i;
                                        if (sizeOfSearchresult == 0) {
                                           //TODO- show the below toast later
                                            // Toast.makeText(SearchResultsActivity.this, "No Items Found", Toast.LENGTH_SHORT).show();
                                        }
                                        i++;

                                        set_up_the_recycler_grid_view(list_of_data_objects__for_adapter);
                                    }


                                }

                            } else {
                                Log.w("&&&&", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }

    }

    private String[] getAllWordsFromSringInArray() {

     /*  String[] words = searchQuery.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            // You may want to check for a non-word character before blindly
            // performing a replacement
            // It may also be necessary to adjust the character class
            words[i] = words[i].replaceAll("[^\\w]", "");

            Log.i("&&&&", "getAllWordsFromSringInArray: ");
        }*/

        if (pageNoForMlFeature == 1) {
            ArrayList<String> listOfSearchQueries = new ArrayList<>();
            String[] arrSplit = searchQuery.split(",");
            int lastIndex = 0;
            for (int i = 0; i < searchQuery.length(); i++) {
                if (searchQuery.charAt(i) == ',') {
                    listOfSearchQueries.add(searchQuery.substring(lastIndex, i));
                    Log.i("&&&&", "test" + searchQuery.substring(lastIndex, i));
                    lastIndex = i + 1;
                }
                listOfSearchQueries.add(searchQuery.substring(lastIndex, searchQuery.length() - 1));

                // Log.i("&&&&", "getAllWordsFromSringInArray: ");
                return arrSplit;
            }
        } else if (pageNoForMlFeature == 2) {
            ArrayList<String> listOfSearchQueries = new ArrayList<>();
            String[] arrSplit = searchQuery.split(" ");
     /*  int lastIndex = 0;
       for (int i = 0; i < searchQuery.length(); i++) {
           if (searchQuery.charAt(i) == ' ') {
               listOfSearchQueries.add(searchQuery.substring(lastIndex, i));
               Log.i("&&&&", "test" + searchQuery.substring(lastIndex, i));
               lastIndex = i + 1;
           }
           listOfSearchQueries.add(searchQuery.substring(lastIndex, searchQuery.length() - 1));

           // Log.i("&&&&", "getAllWordsFromSringInArray: ");
       }*/
            if (arrSplit.length == 0) {
                arrSplit[0] = searchQuery;
            }
            return arrSplit;
        }

        return null;
    }

    /*private void retrive_all_the_item_image_url_in_a_list(String document_id, int index_for_list, ClassForCategoryItemReclrDATAObject data_object, ArrayList<ClassForCategoryItemReclrDATAObject> list_of_data_objects__for_adapter) {

        //HERE I AM RETRIVNG ALL THE IMAGES

        //getting all the documents whose have a feild 'item id' as this item's called from

        final ArrayList<String>[] images_URLs = new ArrayList[]{new ArrayList<>()};
        ArrayList<Integer> imagesorder = new ArrayList<>();

        db.collection("items for sale").document(document_id).collection("uploaded images urls")
                .whereEqualTo("order no", Integer.valueOf(document_id)) // <-- This line
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("&&&&&", document.getId() + " => " + document.getData());

                                images_URLs[0].add((String) document.get("url"));
                                imagesorder.add(((Long) document.get("order no")).intValue());
                            }


                            //TODO-sort the Image url according to their order here
                            images_URLs[0] = sort_the_url_into_order(images_URLs[0], imagesorder);

                            //now adding the first Image url
                            data_object.setItem_image_url(images_URLs[0].get(0));

                            data_object.setItem_all_images_list(images_URLs);

                            //all the work related to this specific item's data object is complete adding it to final list at correct place
                            list_of_data_objects__for_adapter.set(index_for_list, data_object);

                            //if this index is 1 less then the total size
                            //that will mean that this is the last thread ans
                            //now all the data is retrived Completely thus calling for setting up the list
                            if (index_for_list == sizeOfSearchresult - 1) {
                                set_up_the_recycler_grid_view(list_of_data_objects__for_adapter);

                            }
                        } else {
                            Log.d("&&&&&", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
*/

/*    private ArrayList<String> sort_the_url_into_order(ArrayList<String> images_url, ArrayList<Integer> imagesorder) {
        int i, key, j;
        String key2;
        for (i = 1; i < imagesorder.size(); i++) {
            key = imagesorder.get(i);
            key2 = images_url.get(i);
            j = i - 1;

        *//* Move elements of arr[0..i-1], that are
        greater than key, to one position ahead
        of their current position *//*
            while (j >= 0 && imagesorder.get(j) > key) {
                imagesorder.set(j + 1, imagesorder.get(j));
                images_url.set(j + 1, images_url.get(j));
                j = j - 1;
            }
            imagesorder.set(j + 1, key);
            images_url.set(j + 1, key2);
        }
        return images_url;
    }*/

    private void set_up_the_recycler_grid_view(ArrayList<ItemsForSale> list_of_data_objects__for_adapter) {
        //now executing the UI part
        recyclerView = (RecyclerView) findViewById(R.id.id_fr_recycler_view_search_items_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.removeAllViews();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchResultsActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        // set a GridLayoutManager with 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        //data_list_for_adapter = list_of_data_objects__for_adapter;
        ReclrAdapterClassForCtgrItems adapter = new ReclrAdapterClassForCtgrItems(SearchResultsActivity.this, list_of_data_objects__for_adapter);
        recyclerView.setAdapter(adapter);


      /*  if (list_of_data_objects__for_adapter.size() == 0) {
            Toast.makeText(this, "No Items found", Toast.LENGTH_SHORT).show();
        }*/

    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(SearchResultsActivity.this, MainActivity.class);
        startActivity(in);


        //just adding an animatiion here whic makes it go with animation sliding to right
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }
}
