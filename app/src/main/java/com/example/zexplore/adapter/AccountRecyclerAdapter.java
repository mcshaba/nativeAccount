package com.example.zexplore.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zexplore.R;
import com.example.zexplore.listener.FormItemClickListener;
import com.example.zexplore.listener.ResponseListener;
import com.example.zexplore.model.AccountModel;
import com.example.zexplore.repository.AccountRepository;
import com.example.zexplore.util.AppSettings;
import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.zexplore.fragment.CreateAccountFragment.IS_EDIT_MODE;

/**
 * Created by Ehigiator David on 02/05/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */
public class AccountRecyclerAdapter extends RecyclerView.Adapter<AccountRecyclerAdapter.AccountViewHolder> {
    private List<AccountModel> accountModels;
    private List<AccountModel> allAccountModels;

    private static final Random RANDOM = new Random();
    private FormItemClickListener formItemClickListener;
    private Context context;
    String token;

    public void setFormItemClickListener(FormItemClickListener formItemClickListener) {
        this.formItemClickListener = formItemClickListener;
    }

    public AccountRecyclerAdapter(List<AccountModel> accountModels, Context context) {
        this.accountModels = accountModels;

        this.context = context;
        AppSettings appSettings;
        appSettings = AppSettings.getInstance(context);

        if (appSettings != null)
            token = "Bearer " + appSettings.getUser().getToken();
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(view);
    }


    public void filter(String text) {

        if (accountModels == null || allAccountModels == null)
            return;

        accountModels.clear();
        if (text.isEmpty()) {


            accountModels.addAll(allAccountModels);
        } else {
            text = text.toLowerCase();
            for (AccountModel item : allAccountModels) {
                if (item.getStatus().toLowerCase().contains(text) || item.getStatus().toLowerCase().contains(text)) {
                    accountModels.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        final AccountModel accountModel = accountModels.get(position);
//        holder.attachementLabel.setVisibility(View.INVISIBLE);
//        holder.attachementImageView.setVisibility(View.INVISIBLE);
        holder.attachementLabel.setText("Files Attached to profile");
        holder.nameTextView.setText(accountModel.getAccountName());

        holder.numberDateTextView.setText(String.format("Phone: %s", accountModel.getPhoneNumber()));
        switch (accountModel.getStatus()) {
            case "Saved":
                holder.accountStatusImageView.setImageResource(R.drawable.ic_triangle);
                holder.attachementLabel.setVisibility(View.VISIBLE);
                holder.attachementImageView.setVisibility(View.VISIBLE);
                break;
            case "Pending":
                holder.accountStatusImageView.setImageResource(R.drawable.ic_rectangle);
                break;
            case "Completed":
                holder.nameTextView.setText(String.format("%s (A/C: %s)", accountModel.getAccountName(), accountModel.getAccountNumber()));
                holder.accountStatusImageView.setImageResource(R.drawable.ic_oval);
                holder.verifyAccountChip.setVisibility(View.GONE);
                holder.verifyAccountChip.setVisibility(View.GONE);
                holder.editAccountChip.setVisibility(View.GONE);
                break;
            default:
                holder.accountStatusImageView.setImageResource(R.drawable.ic_rectangle_pink);
                break;
        }
        holder.editAccountChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IS_EDIT_MODE = true;

                Bundle bundle = new Bundle();
                bundle.putString("referenceId", accountModel.getRefId());
                Navigation.findNavController(view).navigate(R.id.createAccountFragment, bundle);

            }
        });
        holder.verifyAccountChip.setOnClickListener(v -> {
            holder.verifyAccountChip.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);

            AccountRepository.getInstance().verifyAccountsByReferenceId(token, accountModel.getRefId(), new ResponseListener<String>() {
                @Override
                public void onSuccess(String data) {
                    holder.verifyAccountChip.setVisibility(View.VISIBLE);
                    holder.progressBar.setVisibility(View.GONE);
                    displayMessage(holder.itemView, data);
                }

                @Override
                public void onError(String message) {
                    holder.verifyAccountChip.setVisibility(View.VISIBLE);
                    holder.progressBar.setVisibility(View.GONE);

                    displayMessage(holder.itemView, message);

                }
            });
        });

    }


    private void displayMessage(View view, String message) {
        int marginSide = 0;
        int marginBottom = 550;


        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
//        snackbar.setAction("Cancel", v -> snackbar.dismiss());
        // Changing message text color
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.amberWarning));

        View snackbarView = snackbar.getView();

        TextView snackTextView = (TextView) snackbarView
                .findViewById(R.id.snackbar_text);
        snackTextView.setMaxLines(3);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();


        params.setMargins(
                params.leftMargin + marginSide,
                params.topMargin,
                params.rightMargin + marginSide,
                params.bottomMargin + marginBottom
        );

        snackbarView.setLayoutParams(params);
        snackbar.show();
    }


    public void setData(List<AccountModel> newData) {
        this.accountModels = newData;
        allAccountModels = new ArrayList<>();
        allAccountModels.addAll(accountModels);
        notifyDataSetChanged();
    }

    public AccountModel getItem(int position) {
        return accountModels.get(position);
    }

    @Override
    public int getItemCount() {
        return accountModels == null ? 0 : accountModels.size();
    }


    class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View mView;

        TextView nameTextView;
        TextView numberDateTextView;
        ImageView accountStatusImageView;
        ImageView attachementImageView;
        TextView attachementLabel;
        Chip verifyAccountChip;
        Chip editAccountChip;
        ProgressBar progressBar;

        private AccountViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            nameTextView = mView.findViewById(R.id.nameTextView);

            numberDateTextView = mView.findViewById(R.id.numberDateTextView);
            accountStatusImageView = mView.findViewById(R.id.accountStatusImageView);
            attachementImageView = mView.findViewById(R.id.attachementImageView);
            attachementLabel = mView.findViewById(R.id.attachementLabel);
            verifyAccountChip = mView.findViewById(R.id.verifyAccountChip);
            progressBar = mView.findViewById(R.id.progress_bar);
            editAccountChip = mView.findViewById(R.id.editAccountChip);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if(formItemClickListener != null){
//                formItemClickListener.onClick(view, getAdapterPosition(), context);
//            }
        }
    }
}
