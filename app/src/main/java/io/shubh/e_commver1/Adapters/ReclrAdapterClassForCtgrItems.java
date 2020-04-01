package io.shubh.e_commver1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.shubh.e_commver1.Models.ItemsForSale;
import io.shubh.e_commver1.R;

public class ReclrAdapterClassForCtgrItems extends RecyclerView.Adapter<ReclrAdapterClassForCtgrItems.ViewHolder> {
    private List<ItemsForSale> dataForItemArrayList;
    private Context context;



    public ReclrAdapterClassForCtgrItems(Context context, List<ItemsForSale> dataForItems) {
        this.context = context;
        this.dataForItemArrayList = dataForItems;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_item_Title;
        TextView tv_item_price;

        ImageView iv_item_image;

        ImageButton edit_bt;

        public ViewHolder(View view) {
            super(view);

            tv_item_Title = (TextView)view.findViewById(R.id.id_tv_title_fR_rclr_item_ctgr_item_list);
            tv_item_price = (TextView)view.findViewById(R.id.id_tv_price_fR_rclr_item_ctgr_item_list);

            iv_item_image = (ImageView)view.findViewById(R.id.id_iv_fR_rclr_item_ctgr_item_list);

            edit_bt =(ImageButton) view.findViewById(R.id.id_bt_save_fR_rclr_item_ctgr_item_list);
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
        holder.tv_item_price.setText("₹"+dataForItemArrayList.get(position).getItem_price());

        Glide.with(context).load(dataForItemArrayList.get(position).getListOfImageURLs().get(0)).centerCrop().into(holder.iv_item_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    /*    viewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareBody = dataForItemArrayList.get(i).getVidTitle() + "\n\nSee this titled video at this app in play store" + "\n\nhttps://play.google.com/store/apps/details?id=io.shubh.BeautyApp";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                view.getContext().startActivity(Intent.createChooser(sharingIntent, "Share"));
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return dataForItemArrayList.size();
    }



}