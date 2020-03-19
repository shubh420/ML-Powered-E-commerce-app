package io.shubh.e_commver1.CategoryItems.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.shubh.e_commver1.CategoryItems.Interactor.CategoryItemsInteractorImplt;
import io.shubh.e_commver1.CategoryItems.Presenter.CategoryItemsPresenter;
import io.shubh.e_commver1.CategoryItems.Presenter.CategoryItemsPresenterImplt;
import io.shubh.e_commver1.Models.Category;
import io.shubh.e_commver1.Models.ItemsForSale;
import io.shubh.e_commver1.R;
import io.shubh.e_commver1.StaticClassForGlobalInfo;
import io.shubh.e_commver1.Utils.VerticalTextView;
import io.shubh.e_commver1.reclr_adapter_class_for_ctgr_items;

/**
 * A simple {@link Fragment} subclass.

 * to handle interaction events.
 * Use the {@link CategoryItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryItemsFragment extends Fragment implements CategoryItemsView   {

    private static final String ARG_PARAM1_Category_Name = "param1";
   // private static final String ARG_PARAM2_Level_Of_Category_Name = "param2";
    private static final String ARG_PARAM2_Category_Path = "param2";

    //below var will have just the name if this frag is asked to open catgr only(like from mainactivty)
    //If its opened to show some subcatgr on the very start ,then it will need to have passed the whole path
    private String mParam1CategoryName;
    private String mParam2CategoryPath;

    //private int mParam1LevelOfCategory;
  //  private String mParam2;
    View containerViewGroup;


  CategoryItemsPresenter mPresenter;
    TextView tv_header;

    TextView tv_catgr_dierctory;
    TextView tv_sub_catgr_dierctory;
    TextView tv_sub_sub_catgr_dierctory;

    TextView tv_first_slash_directry;
    TextView tv_second_slash_directry;

    LayoutInflater inflater;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ArrayList<ItemsForSale> itemsList;
    reclr_adapter_class_for_ctgr_items adapter;
    String nameOfCtgrforWhichDataIsDetected;

    String rootCtgr ;
    String subCtgr;
    String subSubCtgr;


    public CategoryItemsFragment() {
        // Required empty public constructor
    }


    public static CategoryItemsFragment newInstance(String param1  ,String param2 ) {
        CategoryItemsFragment fragment = new CategoryItemsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1_Category_Name, param1);
        args.putString(ARG_PARAM2_Category_Path, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1CategoryName = getArguments().getString(ARG_PARAM1_Category_Name);
            mParam2CategoryPath = getArguments().getString(ARG_PARAM2_Category_Path);
           // mParam2 = getArguments().getString(ARG_PARAM2);
            rootCtgr=mParam1CategoryName;


            //always do presenter related work at last in Oncreate
            mPresenter = new CategoryItemsPresenterImplt(this, new CategoryItemsInteractorImplt() {
            } , mParam1CategoryName, mParam2CategoryPath);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//this below line and return line is must for everytime inside oncreateview funcytions
        containerViewGroup=inflater.inflate(R.layout.fragment_category_items, container, false);;

        this.inflater=inflater;
        attachOnBackBtPressedlistener();

        DoUiWork();

        nameOfCtgrforWhichDataIsDetected=mParam1CategoryName;
        callForPresenterToGetCtgrItems(false);

        // Inflate the layout for this fragment
        return containerViewGroup;
    }




    private void DoUiWork() {



        //  container_for_directory_tvs =(LinearLayout)findViewById(R.id.id_fr_ll_container_fr_categories);
        tv_catgr_dierctory = (TextView) containerViewGroup.findViewById(R.id.id_fr_tv_catgr_directory);
        tv_sub_catgr_dierctory = (TextView) containerViewGroup.findViewById(R.id.id_fr_tv_sub_catgr_directory);
        tv_sub_sub_catgr_dierctory = (TextView) containerViewGroup.findViewById(R.id.id_fr_tv_sub_sub_catgr_directory);
        tv_first_slash_directry = (TextView) containerViewGroup.findViewById(R.id.id_fr_tv_first_slash);
        tv_second_slash_directry = (TextView) containerViewGroup.findViewById(R.id.id_fr_tv_scnd_slash);

        tv_catgr_dierctory.setPaintFlags(tv_sub_catgr_dierctory.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); //making tv underlined
        tv_sub_catgr_dierctory.setVisibility(View.GONE);
        tv_sub_catgr_dierctory.setPaintFlags(tv_sub_catgr_dierctory.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); //making tv underlined
        tv_sub_sub_catgr_dierctory.setVisibility(View.GONE);
        tv_sub_sub_catgr_dierctory.setPaintFlags(tv_sub_catgr_dierctory.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); //making tv underlined
        tv_first_slash_directry.setVisibility(View.GONE);
        tv_second_slash_directry.setVisibility(View.GONE);

        tv_header = (TextView) containerViewGroup.findViewById(R.id.id_fr_tv_header);



        recyclerView = (RecyclerView) containerViewGroup.findViewById(R.id.id_fr_recycler_view_ctgr_items_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
         gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        recyclerView.setLayoutManager(gridLayoutManager);

         itemsList = new ArrayList<>();
         adapter = new reclr_adapter_class_for_ctgr_items(getContext(), itemsList);
        recyclerView.setAdapter(adapter);

        setUpCtgrSideBar();
//-------------------------------------------------------------------------------------------------

        updateHeaderTvAndCtgrStrip();
loadCategorylayoutsInTheSidebar();
    }

    private void setUpCtgrSideBar() {
       /* RelativeLayout rlContainer = (RelativeLayout)containerViewGroup.findViewById(R.id.rl_root_container_of_side_strip);

        final ViewTreeObserver observer= rlContainer.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rlContainer.getWidth();
                Log.i("######", "width "+rlContainer.getWidth());
              //  observer.removeGlobalOnLayoutListener(this);
            }
        });*/

    }

    private void loadCategorylayoutsInTheSidebar() {

        LinearLayout ll_container_side_bar = (LinearLayout)containerViewGroup.findViewById(R.id.container_fr_ctgr);


        //below if checks if path has the / and // char in it or not ...-1 means it doesnt ..so it checks if we
        //are at level 1 ...that is of ctgrs
        if(mParam2CategoryPath.indexOf('/')==-1 && mParam2CategoryPath.indexOf("//")==-1 ) {
          //  tv_header.setText(mParam1CategoryName);
            //I have only have the name of the category and where it is on the ctgr level
            //since its at one ,I will just iterate through each ctgr at one level to find it
            //after finding it i will hust load the subctgr under it

            for(int i=0 ; i<StaticClassForGlobalInfo.categoriesList.size() ;i++){
                if(StaticClassForGlobalInfo.categoriesList.get(i).getName()== mParam1CategoryName){
                    //we have gor the catgr //now  loading its subctgrs if it have
                    if(StaticClassForGlobalInfo.categoriesList.get(i).isHaveSubCatgr() ){

                        ArrayList<Category.SubCategory> subCategoriesList  =StaticClassForGlobalInfo.categoriesList.get(i).getSubCategoriesList();
                        ll_container_side_bar.removeAllViews();
                        for(int j=0 ; j< subCategoriesList.size() ; j++){

                            View inflated = inflater.inflate(R.layout.vertical_textviews_for_to_be_inflated, ll_container_side_bar, false);
                            ll_container_side_bar.addView(inflated);

                            TextView tv = (TextView) inflated.findViewById(R.id.vertical_tv);

                            //making the tv underlined if the subctgrs have subsubctgrs
                            if(subCategoriesList.get(j).isHaveSubSubCatgr()) {
                                tv.setText(subCategoriesList.get(j).getName());
                                tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); //making tv underlined

                            }else{
                                tv.setText(subCategoriesList.get(j).getName());
                            }
                            int finalJ = j;
                            inflated.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    subCtgr= subCategoriesList.get(finalJ).getName();
                                    if(mParam2CategoryPath.indexOf(subCategoriesList.get(finalJ).getName())==-1) {
                                        // above if is to check if the same ctgr is clicked twice or not...because if it is...then the ctgrpath will already have the string i am adding below
                                        mParam2CategoryPath = rootCtgr + "/" + subCtgr;
                                        mParam1CategoryName = subCategoriesList.get(finalJ).getName();
                                    }

                                    if(subCategoriesList.get(finalJ).isHaveSubSubCatgr()) {


                                        loadCategorylayoutsInTheSidebar();
                                        updateHeaderTvAndCtgrStrip();
                                        //load the recyclr grid view below
                                        emptyTheRecyclerView();

                                        nameOfCtgrforWhichDataIsDetected=mParam1CategoryName;
                                        callForPresenterToGetCtgrItems(false);

                                    }else{
                                        updateHeaderTvAndCtgrStrip();
                                        emptyTheRecyclerView();

                                        nameOfCtgrforWhichDataIsDetected=mParam1CategoryName;
                                        callForPresenterToGetCtgrItems(false);
                              //          loadCategorylayoutsInTheSidebar();

                                    }

                                }
                            });

                        }

                    }
                }
            }


        }   //below if checks if we are at level 2 that is of subctgr
            else if(mParam2CategoryPath.indexOf('/')!=-1 && mParam2CategoryPath.indexOf("//")==-1){

            //I have only have the name of the subcategory and where it is on the ctgr level
            //since its at two,first  ,I will just iterate through each ctgr at one level to find it whome it is subctgr of
            //after finding it i will hust load the subsubctgr under it
            for(int i=0 ; i<StaticClassForGlobalInfo.categoriesList.size() ;i++){
                if(StaticClassForGlobalInfo.categoriesList.get(i).isHaveSubCatgr()==true){

                    for(int j =0;j<StaticClassForGlobalInfo.categoriesList.get(i).getSubCategoriesList().size() ;j++){
                        if(mParam1CategoryName == StaticClassForGlobalInfo.categoriesList.get(i).getSubCategoriesList().get(j).getName()){
                            //we have got the subctgr in the ctgrpath
                            ArrayList<Category.SubCategory.SubSubCategory> subsubCategoriesList  =StaticClassForGlobalInfo.categoriesList.get(i).getSubCategoriesList().get(j).getSubSubCategoryList();
                            ll_container_side_bar.removeAllViews();
                            for(int k=0 ; k< subsubCategoriesList.size() ; k++){

                                View inflated = inflater.inflate(R.layout.vertical_textviews_for_to_be_inflated, ll_container_side_bar, false);
                                ll_container_side_bar.addView(inflated);

                                TextView tv = (TextView) inflated.findViewById(R.id.vertical_tv);
                                tv.setText(subsubCategoriesList.get(k).getName());

                                int finalK = k;
                                inflated.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View view) {

                                        removeColorFromOtherCtgrTvsInSideBarIfAny(tv ,ll_container_side_bar);
                                        tv.setTextColor(getResources().getColor(R.color.colorSecondary));

                                       subSubCtgr= subsubCategoriesList.get(finalK).getName();
                                            mParam2CategoryPath = rootCtgr + "/" + subCtgr+"//"+subSubCtgr;
                                            mParam1CategoryName = subsubCategoriesList.get(finalK).getName();


                                        //loadCategorylayoutsInTheSidebar();
                                        updateHeaderTvAndCtgrStrip();

                                        emptyTheRecyclerView();
                                        nameOfCtgrforWhichDataIsDetected=mParam1CategoryName;
                                        callForPresenterToGetCtgrItems(false);

                                    }
                                });
                            }


                        }
                    }
                }
            }



           /* int index1 =mParam1CategoryName.indexOf('/');
            int index2 =mParam1CategoryName.indexOf("//");
            tv_header.setText(mParam1CategoryName.substring(index1+1,index2));*/
        }    //below if checks if we are at level 3 that is of subsubctgr
            else if(mParam2CategoryPath.indexOf('/')!=-1 && mParam2CategoryPath.indexOf("//")!=-1){
            /*int index2 =mParam1CategoryName.indexOf("//");
            tv_header.setText(mParam1CategoryName.substring(index2+1));*/
        }

    }


    private void removeColorFromOtherCtgrTvsInSideBarIfAny(TextView tv, LinearLayout ll_container_side_bar) {

        final int childCount = ll_container_side_bar.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RelativeLayout rlContainingOtherTv = (RelativeLayout) ll_container_side_bar.getChildAt(i);
            TextView otherTv = (TextView)rlContainingOtherTv.findViewById(R.id.vertical_tv);
            otherTv.setTextColor(getResources().getColor(R.color.colorLightGrayForTextViews));
        }


    }

    private void emptyTheRecyclerView() {

        itemsList.clear();
        adapter.notifyDataSetChanged();
    }

    private void updateHeaderTvAndCtgrStrip() {
        //below code is for setting text on the header tv
        tv_header.setText(mParam1CategoryName);

//below code is for setting text at the categroy path strip...
        //below if checks if path has the / and // char in it or not ...-1 means it doesnt ..so it checks if we
        //are at level 1 ...that is of ctgrs
        if(mParam2CategoryPath.indexOf('/')==-1 && mParam2CategoryPath.indexOf("//")==-1 ) {
            tv_catgr_dierctory.setText(mParam1CategoryName);

            //below if checks if we are at level 2 that is of subctgr
        }else if(mParam2CategoryPath.indexOf('/')!=-1 && mParam2CategoryPath.indexOf("//")==-1) {
            int index1 = mParam2CategoryPath.indexOf('/');
            tv_catgr_dierctory.setText(mParam2CategoryPath.substring(0, index1));

            tv_first_slash_directry.setVisibility(View.VISIBLE);
            tv_sub_catgr_dierctory.setVisibility(View.VISIBLE);

            tv_sub_catgr_dierctory.setText(mParam1CategoryName);

            //below if checks if we are at level 3 that is of subsubctgr
        }else if(mParam2CategoryPath.indexOf('/')!=-1 && mParam2CategoryPath.indexOf("//")!=-1) {
            int index1 = mParam2CategoryPath.indexOf('/');
            tv_catgr_dierctory.setText(mParam2CategoryPath.substring(0, index1));

            int index2 = mParam2CategoryPath.indexOf("//");
            tv_first_slash_directry.setVisibility(View.VISIBLE);
            tv_sub_catgr_dierctory.setVisibility(View.VISIBLE);
            tv_sub_catgr_dierctory.setText(mParam2CategoryPath.substring(index1 + 1, index2));


            tv_second_slash_directry.setVisibility(View.VISIBLE);
            tv_sub_sub_catgr_dierctory.setVisibility(View.VISIBLE);
            tv_sub_sub_catgr_dierctory.setText(mParam1CategoryName);
        }

    }

    private void callForPresenterToGetCtgrItems(boolean ifItsALoadMorecall) {

        mPresenter.getItemsFromFirebase(mParam1CategoryName,mParam2CategoryPath ,ifItsALoadMorecall);
    }

    @Override
    public void onGettingCtgrItemsFromPrsntr(List<ItemsForSale> retrivedShorterItemList, Boolean listNotEmpty, String ctgrName) {

        if(nameOfCtgrforWhichDataIsDetected==ctgrName) {

            this.itemsList.addAll(retrivedShorterItemList);
            adapter.notifyDataSetChanged();


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    if (itemsList.isEmpty() == false && itemsList.size()>5) {
                        //below is done because this
                        if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == itemsList.size() - 1) {
                            //bottom of list!
                            callForPresenterToGetCtgrItems(true);
                        }
                    }
                }
            });
        }
    }



   /* @Override
    public void onCategoryButtonsClicked(int levelOfCategory , String name) {

    }*/


    @Override
    public void switchActivity(int i) {
     //   progressBar.setVisibility(android.view.View.GONE);

      /*  if(i==1) {
            Intent in = new Intent(Activity.this, MainActivity.class);
            startActivity(in);
        } else if(i==2){
            //  showToast("not logged in");
            Intent in = new Intent(Activity.this, WelcomeActivity.class);
            startActivity(in);
        }*/
    }

    @Override
    public Context getContext(boolean getActvityContext) {

       /* if(getActvityContext==true){
            return this;
        }
        return this.getApplicationContext();*/
       return null;
    }

    @Override
    public void showProgressBar(boolean b) {
        /*if(b==true) {
            progressBar.setVisibility(android.view.View.VISIBLE);
        }else {
            progressBar.setVisibility(android.view.View.INVISIBLE);

        }*/
    }

    @Override
    public void ShowSnackBarWithAction(String msg, String actionName) {
      /*  LinearLayout ll_Root = (LinearLayout)containerViewGroup.findViewById(R.id.layoutsplash);
        Snackbar snackbar = Snackbar
                .make(ll_Root , msg, BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction(actionName, new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View view) {
                        mPresenter.LoginRelatedWork();

                    }
                });

        snackbar.show();*/
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show    ();

    }


    //below function is for catching back button pressed
    private void attachOnBackBtPressedlistener() {
        containerViewGroup.setFocusableInTouchMode(true);
        containerViewGroup.requestFocus();
        containerViewGroup.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    onBackButtonPressed();
                    return true;
                }
                return false;
            }
        });
    }

    private void onBackButtonPressed() {
        getFragmentManager().beginTransaction().remove(CategoryItemsFragment.this).commit();
    }


}
