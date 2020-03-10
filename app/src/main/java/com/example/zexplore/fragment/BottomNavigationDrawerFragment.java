package com.example.zexplore.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.zexplore.R;
import com.example.zexplore.helper.CustomBottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * Created by Ehigiator David on 20/03/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */


public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    RelativeLayout pending_view;

    public static String BROADCAST_ACCOUNT_FILTER = "BROADCAST_ACCOUNT_FILTER";

    public BottomNavigationDrawerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home_bottomsheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        pending_view = view.findViewById(R.id.pending_view);
        pending_view.setOnClickListener(v -> sendMessageFilter("Pending"));

        view.findViewById(R.id.clear_view).setOnClickListener(v -> sendMessageFilter(""));
        view.findViewById(R.id.completed_view).setOnClickListener(v -> sendMessageFilter("Completed"));
        view.findViewById(R.id.save_view).setOnClickListener(v -> sendMessageFilter("Saved"));
        view.findViewById(R.id.rejected_view).setOnClickListener(v -> sendMessageFilter("Rejected"));


    }


    private void sendMessageFilter(String message) {
        if (getContext() == null)
            return;
        dismiss();

        Intent accountFilterIntent = new Intent(BROADCAST_ACCOUNT_FILTER);
        accountFilterIntent.putExtra("DATA", message);

        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(accountFilterIntent);
    }
}
