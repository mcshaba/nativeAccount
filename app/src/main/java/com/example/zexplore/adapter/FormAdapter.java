package com.example.zexplore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.zexplore.R;
import com.example.zexplore.listener.FormItemClickListener;
import com.example.zexplore.model.Form;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.util.Constant;
import com.example.zexplore.util.CryptLib;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Shaba Michael on 2/04/2019.
 * Cyberspace Limited
 * michael.shaba@cyberspace.net.ng
 */

public class FormAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Form> formList;
    private Context context;
    private FormItemClickListener formItemClickListener;
    private int type;

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_FILL = 1;

    public void setFormItemClickListener(FormItemClickListener formItemClickListener) {
        this.formItemClickListener = formItemClickListener;
    }

    public FormAdapter(Context context, int type){
        this.formList = new ArrayList<>();
        this.context = context;
        this.type = type;
    }

    public void updateData(List<Form> formList){
        this.formList.clear();
        this.formList.addAll(formList);
        notifyDataSetChanged();
    }

    public Form getItem(int position){
        return formList.get(position);
    }

    public void removeData(int position){
        this.formList.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (formList.size() == 0){
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_FILL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_FILL:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
                ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);
                itemView.setOnClickListener(itemViewHolder);
                return itemViewHolder;
            case VIEW_TYPE_EMPTY:
                View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_view, parent, false);
                return new EmptyViewHolder(emptyView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);
        switch (viewType){
            case VIEW_TYPE_FILL:
                ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
                Form form = formList.get(position);

                if (form.getFirstName() == null && form.getMiddleName() == null && form.getLastName() == null){
                    itemViewHolder.fullName.setHeight(0);
                } else {
                    try {
                        CryptLib _crypt = new CryptLib();
                        String key = CryptLib.SHA256(Constant.ENCRYPT_KEY, 32); //32 bytes = 256 bit
                        String iv = Constant.INNITIALISING_VECTOR_KEY; //16 bytes = 128 bit
                        String accountName = _crypt.decrypt(form.getAccountName(), key, iv);
                        itemViewHolder.fullName.setText(accountName);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                itemViewHolder.accountType.setText(form.getAccountTypeName());
                itemViewHolder.createdOn.setText(AppUtility.setDateString(form.getCreatedOn()));

                if (type != 0){
                    itemViewHolder.status.setText(getStatus(form.getStatus()));
                    itemViewHolder.status.setTextColor(context.getResources().getColor(getStatusColor(
                            form.getStatus())));
                }

                if (type == 0) itemViewHolder.icon.setImageResource(R.drawable.ic_triangle);
                if (type == 1) itemViewHolder.icon.setImageResource(R.drawable.ic_rectangle);
                if (type == 2) itemViewHolder.icon.setImageResource(R.drawable.ic_oval);
                if (type == 3) itemViewHolder.icon.setImageResource(R.drawable.ic_rectangle_pink);

                break;
            case VIEW_TYPE_EMPTY:
                EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;

                if (type == 0)emptyViewHolder.message.setText("No Saved Forms!");
                if (type == 1)emptyViewHolder.message.setText("No Completed Forms!");
                if (type == 2)emptyViewHolder.message.setText("No Sent Forms!");
                if (type == 3)emptyViewHolder.message.setText("No Rejected Forms!");

                break;
        }
    }

    private String getStatus(int status){
        switch (status){
            case 0:
                return "Pending";
            case 1:
                return "Approved";
            case 2:
                return "Rejected";
            default:
                return "Pending";
        }
    }

    private int getStatusColor(int status){
        switch (status){
            case 0:
                return R.color.colorAccent;
            case 1:
                return R.color.colorSurface;
            default:
                return R.color.colorAccent;
        }
    }

    @Override
    public int getItemCount() {
        if (formList.size() == 0){
            return 1;
        } else {
            return formList.size();
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView fullName,accountType,createdOn,status ;
        ImageView icon,overflow;

        ItemViewHolder(View view) {
            super(view);
            fullName = itemView.findViewById(R.id.nameTextView);
            accountType = itemView.findViewById(R.id.attachementLabel);
            createdOn = itemView.findViewById(R.id.numberDateTextView);
//            status = itemView.findViewById(R.id.status);
            icon = itemView.findViewById(R.id.attachementImageView);
//            overflow= itemView.findViewById(R.id.overflow);


            view.setOnClickListener(this);
            overflow.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (formItemClickListener != null){
                switch (view.getId()){
//                    case R.id.overflow:
//                        formItemClickListener.onClick(view, getAdapterPosition(), context);
//                        break;
                    default:
                        formItemClickListener.onClick(view, getAdapterPosition(), context);
                        break;
                }
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
