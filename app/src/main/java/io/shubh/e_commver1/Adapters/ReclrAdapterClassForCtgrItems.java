package io.shubh.e_commver1.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.shubh.e_commver1.CategoryItems.View.CategoryItemsFragment;
import io.shubh.e_commver1.ItemDetailPage.View.ItemDetailFragment;
import io.shubh.e_commver1.Models.ItemsForSale;
import io.shubh.e_commver1.R;
import io.shubh.e_commver1.SearchResultsActivity;

public class ReclrAdapterClassForCtgrItems extends RecyclerView.Adapter<ReclrAdapterClassForCtgrItems.ViewHolder> {
    private List<ItemsForSale> dataForItemArrayList;
    private Context context;
   /* private CategoryItemsFragment categoryItemsFragment;
    private FragmentActivity activity*/;



    public ReclrAdapterClassForCtgrItems( Context context, List<ItemsForSale> dataForItems) {
        this.context = context;
        this.dataForItemArrayList = dataForItems;
       /* this.categoryItemsFragment = categoryItemsFragment;
        this.activity = activity;*/


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_Title;
        TextView tv_item_price;

        ImageView iv_item_image;

        ImageButton edit_bt;

        public ViewHolder(View view) {
            super(view);

            tv_item_Title = (TextView) view.findViewById(R.id.id_tv_title_fR_rclr_item_ctgr_item_list);
            tv_item_price = (TextView) view.findViewById(R.id.id_tv_price_fR_rclr_item_ctgr_item_list);

            iv_item_image = (ImageView) view.findViewById(R.id.id_iv_fR_rclr_item_ctgr_item_list);

            edit_bt = (ImageButton) view.findViewById(R.id.id_bt_save_fR_rclr_item_ctgr_item_list);
        }
    }

    @Override
    public ReclrAdapterClassForCtgrItems.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reclr_item_fr_ctgr_items_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReclrAdapterClassForCtgrItems.ViewHolder holder, int position) {

        holder.tv_item_Title.setText(dataForItemArrayList.get(position).getName());
        holder.tv_item_price.setText("₹" + dataForItemArrayList.get(position).getItem_price());

        Glide.with(context).load(dataForItemArrayList.get(position).getListOfImageURLs().get(0)).centerCrop().into(holder.iv_item_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
               itemDetailFragment.passData(dataForItemArrayList.get(position));

                Log.i("!!!!!", context.getClass().getSimpleName());
               if(context.getClass().getSimpleName().equals("SearchResultsActivity")){

                           ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                           .add( R.id.ll_search_results_container, itemDetailFragment)
                           .commit();
               }else{
                   ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                           .add( R.id.drawerLayout, itemDetailFragment)
                           .commit();
               }



            }
        });


    }


    @Override
    public int getItemCount() {
        return dataForItemArrayList.size();
    }


}