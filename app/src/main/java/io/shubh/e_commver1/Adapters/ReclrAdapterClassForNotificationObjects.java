package io.shubh.e_commver1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.shubh.e_commver1.ItemDetailPage.View.ItemDetailFragment;
import io.shubh.e_commver1.Models.NotifcationObject;
import io.shubh.e_commver1.MyOrders.View.MyOrdersFragment;
import io.shubh.e_commver1.R;
import io.shubh.e_commver1.SellerDashboard.View.SellerDashboardFragment;
import io.shubh.e_commver1.Utils.InterfaceForClickCallbackFromAnyAdaptr;

public class ReclrAdapterClassForNotificationObjects extends RecyclerView.Adapter<ReclrAdapterClassForNotificationObjects.ViewHolder> {
    private List<NotifcationObject> dataForItemArrayList;
    private Context context;
    Context applicationContext;
  //  private boolean ifInitiatedFromSelelrdashboard = false;
    private InterfaceForClickCallbackFromAnyAdaptr interfaceForClickCallbackFromCtgrAdaptr;
    /* private FragmentActivity activity*/;


    public ReclrAdapterClassForNotificationObjects(InterfaceForClickCallbackFromAnyAdaptr interfaceForClickCallbackFromCtgrAdaptr, Context context,     Context applicationContext,List<NotifcationObject> dataForItems) {
        this.context = context;
        this.applicationContext = applicationContext;
        this.dataForItemArrayList = dataForItems;
      //  this.ifInitiatedFromSelelrdashboard = ifInitiatedFromSelelrdashboard;
        this.interfaceForClickCallbackFromCtgrAdaptr = interfaceForClickCallbackFromCtgrAdaptr;
        /*   this.activity = activity;*/


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvContent;
        TextView tvTime;

        ImageView iv;
        RelativeLayout rlContainerReclrItem;

        public ViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvContent = (TextView) view.findViewById(R.id.tvContent);
            tvTime = (TextView) view.findViewById(R.id.tvTime);

            iv = (ImageView) view.findViewById(R.id.iv);
            rlContainerReclrItem = (RelativeLayout) view.findViewById(R.id.rlContainerReclrItem);
        }
    }

    @Override
    public ReclrAdapterClassForNotificationObjects.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reclr_item_fr_notification_item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReclrAdapterClassForNotificationObjects.ViewHolder holder, int position) {

        holder.tvTitle.setText(dataForItemArrayList.get(position).getTitle());
        holder.tvContent.setText( dataForItemArrayList.get(position).getContent());
        holder.tvTime.setText(getDateFromUnix( dataForItemArrayList.get(position).getTime()));

        Glide.with(applicationContext).load(dataForItemArrayList.get(position).getImage_url()).centerCrop().into(holder.iv);

        //todo-  change the color of the holder item  if the notif item hasnt already been read..do this later when app used for real purpose
      //  if(dataForItemArrayList.get(position).isHasItBeenRead()== true){

            holder.rlContainerReclrItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dataForItemArrayList.get(position).getType().equals("1")){

                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                                .add(R.id.drawerLayout, new SellerDashboardFragment())
                                .commit();

                    }else  if(dataForItemArrayList.get(position).getType().equals("2")){

                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                                .add(R.id.drawerLayout, new MyOrdersFragment())
                                .commit();
                    }

                    //todo- on an item click make the interactor set the status of the notif as read ..do this when app is used forr eal purppose

                }
            });


    }


    @Override
    public int getItemCount() {
        return dataForItemArrayList.size();
    }


    public String getDateFromUnix(Long unix) {
        long unixSeconds = unix;
// convert seconds to milliseconds
        Date date = new java.util.Date(unixSeconds * 1000L);
// the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMMM ");
// give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

}