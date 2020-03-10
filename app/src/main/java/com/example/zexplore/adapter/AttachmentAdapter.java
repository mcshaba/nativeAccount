package com.example.zexplore.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zexplore.R;
import com.example.zexplore.listener.AttachmentItemClickListener;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.util.AppUtility;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Shaba Michael on 2/04/2019.
 * Cyberspace Limited
 * michael.shaba@cyberspace.net.ng
 */
public class AttachmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Attachment> attachmentList;
    private Context context;
    private AttachmentItemClickListener attachmentItemClickListener;

    private int type;
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_FILL = 1;

    public void setAttachmentItemClickListener(AttachmentItemClickListener attachmentItemClickListener) {
        this.attachmentItemClickListener = attachmentItemClickListener;
    }

    public AttachmentAdapter(Context context, int type) {
        this.context = context;
        this.attachmentList = new ArrayList<>();
        this.type = type;
    }

    public void updateData(List<Attachment> attachmentList){
        if (attachmentList != null){
            this.attachmentList.clear();
            this.attachmentList.addAll(attachmentList);
        }
        notifyDataSetChanged();
    }

    public void removeData(int position){
        this.attachmentList.remove(position);
        notifyItemRemoved(position);
    }

    public Attachment getItem(int position){
        return attachmentList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (attachmentList.size() == 0){
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_FILL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_FILL:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_id_card_upload, parent, false);
                return new ItemViewHolder(itemView);
            case VIEW_TYPE_EMPTY:
                View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_id_card_upload, parent, false);
                return new EmptyViewHolder(emptyView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case VIEW_TYPE_FILL:
                ItemViewHolder viewHolder = (ItemViewHolder)holder;
                Attachment attachment = attachmentList.get(position);
                Uri uri = AppUtility.retrieveImage(attachment.getImage());
//                Glide.with(context).load(uri).into(viewHolder.thumbnail);
                break;
            case VIEW_TYPE_EMPTY:
                EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
//                if (type == 1)emptyViewHolder.message.setText("No ID Card Uploaded Yet!");
//                if (type == 2)emptyViewHolder.message.setText("No Utility Bill Uploaded Yet!");
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (attachmentList.size() == 0){
            return 1;
        } else {
            return attachmentList.size();
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        ItemViewHolder(View itemView) {
            super(itemView);
            ImageView thumbnail = itemView.findViewById(R.id.emptyImageView);
            //ImageView delete = itemView.findViewById(R.id.delete);

            thumbnail.setOnClickListener(this);
            //delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (attachmentItemClickListener != null){
                attachmentItemClickListener.onClick(view, getAdapterPosition());
            }
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder{

        EmptyViewHolder(View itemView) {
            super(itemView);
            //TextView message = itemView.findViewById(R.id.empty_message);

        }
    }

}
