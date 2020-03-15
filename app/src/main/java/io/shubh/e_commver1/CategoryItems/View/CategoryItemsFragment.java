package io.shubh.e_commver1.CategoryItems.View;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import io.shubh.e_commver1.CategoryItems.Interactor.CategoryItemsInteractorImplt;
import io.shubh.e_commver1.CategoryItems.Presenter.CategoryItemsPresenter;
import io.shubh.e_commver1.CategoryItems.Presenter.CategoryItemsPresenterImplt;
import io.shubh.e_commver1.Models.Category;
import io.shubh.e_commver1.R;
import io.shubh.e_commver1.StaticClassForGlobalInfo;

/**
 * A simple {@link Fragment} subclass.

 * to handle interaction events.
 * Use the {@link CategoryItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryItemsFragment extends Fragment implements CategoryItemsView {

    private static final String ARG_PARAM1_Category_Name_Or_Path = "param1";
    private static final String ARG_PARAM2_Level_Of_Category_Name = "param2";

    //below var will have just the name if this frag is asked to open catgr only(like from mainactivty)
    //If its opened to show some subcatgr on the very start ,then it will need to have passed the whole path
    private String mParam1CategoryNameOrPath;
    //level of catgr
    //1->ctgr
    //2->sub-ctgr
    //3->sub-sub-ctgr
    private int mParam1LevelOfCategory;
  //  private String mParam2;
    ViewGroup containerViewGroup;


  CategoryItemsPresenter mPresenter;
    TextView tv_header;

    TextView tv_catgr_dierctory;
    TextView tv_sub_catgr_dierctory;
    TextView tv_sub_sub_catgr_dierctory;

    TextView tv_first_slash_directry;
    TextView tv_second_slash_directry;


    public CategoryItemsFragment() {
        // Required empty public constructor
    }


    public static CategoryItemsFragment newInstance(String param1  ,int param2 ) {
        CategoryItemsFragment fragment = new CategoryItemsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1_Category_Name_Or_Path, param1);
        args.putInt(ARG_PARAM2_Level_Of_Category_Name, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1CategoryNameOrPath = getArguments().getString(ARG_PARAM1_Category_Name_Or_Path);
           // mParam2 = getArguments().getString(ARG_PARAM2);


            DoUiWork();

            //always do presenter related work at last in Oncreate
            mPresenter = new CategoryItemsPresenterImplt(this, new CategoryItemsInteractorImplt() {
            } , mParam1CategoryNameOrPath , mParam1LevelOfCategory);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        containerViewGroup=container;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_items, container, false);
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

        //below code is for setting text on the header tv
        if(mParam1LevelOfCategory==1) {
            tv_header.setText(mParam1CategoryNameOrPath);
        }else if(mParam1LevelOfCategory==2){
            int index1 =mParam1CategoryNameOrPath.indexOf('/');
            int index2 =mParam1CategoryNameOrPath.indexOf("//");
            tv_header.setText(mParam1CategoryNameOrPath.substring(index1+1,index2));
        }else if(mParam1LevelOfCategory==3){
            int index2 =mParam1CategoryNameOrPath.indexOf("//");
            tv_header.setText(mParam1CategoryNameOrPath.substring(index2+1));
        }

//below code is for the categroy path strip
        if(mParam1LevelOfCategory==1) {
            tv_catgr_dierctory.setText(mParam1CategoryNameOrPath);
        }else if(mParam1LevelOfCategory==2){
            tv_catgr_dierctory.setText(mParam1CategoryNameOrPath);
            tv_first_slash_directry.setVisibility(View.VISIBLE);
            int index1 =mParam1CategoryNameOrPath.indexOf('/');
            int index2 =mParam1CategoryNameOrPath.indexOf("//");
            tv_sub_catgr_dierctory.setText(mParam1CategoryNameOrPath.substring(index1+1,index2));
        }else if(mParam1LevelOfCategory==3){
            tv_catgr_dierctory.setText(mParam1CategoryNameOrPath);
            tv_first_slash_directry.setVisibility(View.VISIBLE);
            int index1 =mParam1CategoryNameOrPath.indexOf('/');
            int index2 =mParam1CategoryNameOrPath.indexOf("//");
            tv_sub_catgr_dierctory.setText(mParam1CategoryNameOrPath.substring(index1+1,index2));
   tv_second_slash_directry.setVisibility(View.VISIBLE);
            tv_sub_sub_catgr_dierctory.setText(mParam1CategoryNameOrPath.substring(index2+1));
        }
//-------------------------------------------------------------------------------------------------

loadCategorylayoutsInTheSidebar();
    }

    private void loadCategorylayoutsInTheSidebar() {

        LinearLayout ll_container_side_bar = (LinearLayout)containerViewGroup.findViewById(R.id.container_fr_ctgr);

        if(mParam1LevelOfCategory==1) {
          //  tv_header.setText(mParam1CategoryNameOrPath);
            //I have only have the name of the category and where it is on the ctgr level
            //since its at one ,I will just iterate through each ctgr at one level to find it
            //after finding it i will hust load the subctgr under it

            for(int i=0 ; i<StaticClassForGlobalInfo.categoriesList.size() ;i++){
                if(StaticClassForGlobalInfo.categoriesList.get(i).getName()==mParam1CategoryNameOrPath){
                    //we have gor the catgr //now  loading its subctgrs if it have
                    if(StaticClassForGlobalInfo.categoriesList.get(i).isHaveSubCatgr() ){

                        ArrayList<Category.SubCategory> subCategoriesList  =StaticClassForGlobalInfo.categoriesList.get(i).getSubCategoriesList();
                        for(int j=0 ; j< subCategoriesList.size() ; j++){

                            View inflated = LayoutInflater.from(getContext()).inflate(R.layout.vertical_textviews_for_to_be_inflated, ll_container_side_bar, false);

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

                                    if(subCategoriesList.get(finalJ).isHaveSubSubCatgr()) {
                                        mParam1CategoryNameOrPath = subCategoriesList.get(finalJ).getName();
                                        mParam1LevelOfCategory = 2;

                                        loadCategorylayoutsInTheSidebar();
                                        //load the recyclr grid view below
                                        getTheCtgrDataAndLoadIntoGridView();
                                    }else{
                                        getTheCtgrDataAndLoadIntoGridView();
                                    }

                                }
                            });

                        }

                    }
                }
            }


        }else if(mParam1LevelOfCategory==2){

            //I have only have the name of the subcategory and where it is on the ctgr level
            //since its at two,first  ,I will just iterate through each ctgr at one level to find it whome it is subctgr of
            //after finding it i will hust load the subsubctgr under it
            for(int i=0 ; i<StaticClassForGlobalInfo.categoriesList.size() ;i++){
                if(StaticClassForGlobalInfo.categoriesList.get(i).isHaveSubCatgr()==true){
                    for(int j =0;j<StaticClassForGlobalInfo.categoriesList.get(i).getSubCategoriesList().size() ;j++){
                        if(mParam1CategoryNameOrPath == StaticClassForGlobalInfo.categoriesList.get(i).getSubCategoriesList().get(j).getName()){
                            //we have got the subctgr in the ctgrpath
                            ArrayList<Category.SubCategory.SubSubCategory> subsubCategoriesList  =StaticClassForGlobalInfo.categoriesList.get(i).getSubCategoriesList().get(j).getSubSubCategoryList();

                            for(int k=0 ; k< subsubCategoriesList.size() ; k++){

                                View inflated = LayoutInflater.from(getContext()).inflate(R.layout.vertical_textviews_for_to_be_inflated, ll_container_side_bar, false);

                                TextView tv = (TextView) inflated.findViewById(R.id.vertical_tv);
                                tv.setText(subsubCategoriesList.get(k).getName());

                                int finalK = k;
                                inflated.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        mParam1CategoryNameOrPath=subsubCategoriesList.get(finalK).getName();
                                        mParam1LevelOfCategory=2;

                                        loadCategorylayoutsInTheSidebar();
                                        getTheCtgrDataAndLoadIntoGridView();
                                    }
                                });
                            }


                        }
                    }
                }
            }



           /* int index1 =mParam1CategoryNameOrPath.indexOf('/');
            int index2 =mParam1CategoryNameOrPath.indexOf("//");
            tv_header.setText(mParam1CategoryNameOrPath.substring(index1+1,index2));*/
        }else if(mParam1LevelOfCategory==3){
            /*int index2 =mParam1CategoryNameOrPath.indexOf("//");
            tv_header.setText(mParam1CategoryNameOrPath.substring(index2+1));*/
        }

    }

    private void getTheCtgrDataAndLoadIntoGridView() {
    }


    @Override
    public void onCategoryButtonsClicked(int levelOfCategory , String name) {

    }


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


}
