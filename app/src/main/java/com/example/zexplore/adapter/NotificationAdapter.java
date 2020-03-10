package com.example.zexplore.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zexplore.R;
import com.example.zexplore.listener.NotificationItemClickListener;
import com.example.zexplore.model.Notification;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.util.Constant;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Shaba Michael on 2/04/2019.
 * Cyberspace Limited
 * michael.shaba@cyberspace.net.ng
 */
public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Notification> notifications;
    private Context context;
    private NotificationItemClickListener itemClickListener;

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_FILL = 1;

    public void setItemClickListener(NotificationItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public NotificationAdapter(Context context) {
        this.context = context;
        this.notifications = new ArrayList<>();
    }

    public void updateData(List<Notification> notifications){
        if (notifications != null){
            this.notifications.clear();
            this.notifications.addAll(notifications);
        }
        notifyDataSetChanged();
    }

    public void removeData(int position){
        this.notifications.remove(position);
        notifyItemChanged(position);
    }

    public Notification getItem(int position){
        return notifications.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (notifications.size() == 0){
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_FILL;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_FILL:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
                return new ItemViewHolder(itemView);
            case VIEW_TYPE_EMPTY:
                View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_view, parent, false);
                return new EmptyViewHolder(emptyView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case VIEW_TYPE_FILL:
                ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
                Notification notification = notifications.get(position);

                itemViewHolder.title.setText(notification.getTitle());
                itemViewHolder.message.setText(notification.getMessage());
                itemViewHolder.createdOn.setText(AppUtility.setDateString(
                        notification.getCreatedOn()
                ));

//                if (notification.isStatus() == 0){
//                    itemViewHolder.cardView.setCardBackgroundColor(
//                            context.getResources().getColor(R.color.colorGrayLight));
//                } else {
//                    itemViewHolder.cardView.setCardBackgroundColor(
//                            context.getResources().getColor(R.color.cardview_light_background));
//                }

                switch (notification.getType()){
                    case Constant.SAVED_FORM:
                        itemViewHolder.icon.setImageResource(R.drawable.ic_rectangle);
                        break;
                    case Constant.COMPLETED_FORM:
                        itemViewHolder.icon.setImageResource(R.drawable.ic_oval);
                        break;
                    case Constant.SENT_FORM:
                        itemViewHolder.icon.setImageResource(R.drawable.ic_triangle);
                        break;
                    case Constant.REJECTED_FORM:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            itemViewHolder.icon.setImageResource(R.drawable.ic_rectangle_pink);
                        }else {
                            itemViewHolder.icon.setImageResource(R.drawable.ic_rectangle_pink);
                        }
                        break;
                }

                break;
            case VIEW_TYPE_EMPTY:
                EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
                emptyViewHolder.message.setText("No Notifications Yet!");
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (notifications.size() == 0){
            return 1;
        } else {
            return notifications.size();
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView icon;
        TextView title;
        TextView message;
        TextView createdOn;
        RelativeLayout cardView;


        ItemViewHolder(View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.accountStatusImageView);
            title= itemView.findViewById(R.id.nameTextView);
//            message= itemView.findViewById(R.id.message);
            createdOn= itemView.findViewById(R.id.numberDateTextView);
            cardView= itemView.findViewById(R.id.account_card_layout);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null){
                itemClickListener.onClick(view, getAdapterPosition());
            }
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder{
        TextView message;

        EmptyViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.emptyTextView);

        }
    }
}
