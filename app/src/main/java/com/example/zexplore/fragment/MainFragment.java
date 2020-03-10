package com.example.zexplore.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.zexplore.R;
import com.example.zexplore.adapter.AccountRecyclerAdapter;
import com.example.zexplore.listener.FormItemClickListener;
import com.example.zexplore.model.AccountBaseResponse;
import com.example.zexplore.model.AccountModel;
import com.example.zexplore.model.LoginResponse;
import com.example.zexplore.service.ZxploreService;
import com.example.zexplore.util.AppSettings;
import com.example.zexplore.util.Constant;
import com.example.zexplore.util.CryptLib;
import com.example.zexplore.viewmodel.AccountViewModel;
import com.example.zexplore.viewmodel.LoginViewModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ehigiator David on 20/03/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    private LoginViewModel viewModel;

    private AccountViewModel accountViewModel;


    List<AccountModel> accounts;

    AccountRecyclerAdapter accountRecyclerAdapter;


    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;


    private View emptyView;

    private AppSettings appSettings;
    private String token;

    private static String ALL = "All";
    private static String COMPLETED = "Completed";
    private static String SAVED = "Saved";
    private static String PENDING = "Pending";
    private static String REJECTED = "Rejected";
    View view;

    private String currentStatus = ALL;
    NavController navController;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_main, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        recyclerView = view.findViewById(R.id.recyclerView);


//        emptyView = view.findViewById(R.id.emptyView);

        return view;


    }

    private void editForm(int position) {
        AccountModel account = accountRecyclerAdapter.getItem(position);

        String status = account.getStatus();
        if (status.equals("Saved")) {

            String refId = account.getRefId();
            getAccountByRefId(refId);
        }
//        Fragment fragment = null;
//        Bundle bundle = new Bundle();
//        fragment = new CreateAccountFragment();
//        if(fragment != null){
//            bundle.putSerializable("Form", account);
//            bundle.putBoolean("Editable", true);
//            fragment.setArguments(bundle);
//        }
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.output, fragment);
//        transaction.commit();


    }

    private void getAccountByRefId(String currentStatus) {


        Bundle bundle = new Bundle();
        bundle.putString("refid", currentStatus);
        navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_global_createAccountFragment, bundle);


    }

    @Override
    public void onStart() {
        super.onStart();
        FragmentManager manager = getChildFragmentManager();

        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        if (getContext() != null)
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(accountFilterListener, new IntentFilter(BottomNavigationDrawerFragment.BROADCAST_ACCOUNT_FILTER));

    }

    @Override
    public void onDestroy() {
        if (getContext() != null)
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(accountFilterListener);
        super.onDestroy();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);
        accountViewModel = ViewModelProviders.of(requireActivity()).get(AccountViewModel.class);


        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        final NavController navController = Navigation.findNavController(view);
        viewModel.authenticationState.observe(getViewLifecycleOwner(),
                authenticationState -> {
                    switch (authenticationState) {
                        case AUTHENTICATED:
                            sendAccount();
                            initializeUI();
                            // TODO: 21/03/2019 display saved accounts
                            break;
                        case UNAUTHENTICATED:
                            navController.navigate(R.id.loginFragment);
                            break;
                    }
                });


    }

    private BroadcastReceiver accountFilterListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("DATA");
            Log.d("Received data : ", data);

            if (accountRecyclerAdapter != null && data != null)
                accountRecyclerAdapter.filter(data);
        }
    };

    private void initializeUI() {
        appSettings = AppSettings.getInstance(requireActivity());

        if (appSettings != null)
            token = "Bearer " + appSettings.getUser().getToken();

        accounts = new ArrayList<>();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        accountRecyclerAdapter = new AccountRecyclerAdapter(accounts, Objects.requireNonNull(getContext()));

        recyclerView.setAdapter(accountRecyclerAdapter);


        swipeRefreshLayout.setOnRefreshListener(this::getAccountsFromServer);

        accountRecyclerAdapter.setFormItemClickListener((view, position, context) -> editForm(position));

        getAccountsFromServer();
    }

    private void getAccountsFromServer() {
//        emptyView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
        accountViewModel.getAccountsByRsmID(token, appSettings.getUser().getEmployeeId(), currentStatus).observe(requireActivity(), data ->
                {
                    swipeRefreshLayout.setRefreshing(false);

                    if (data != null) {
                        if (data.body() != null && data.body().getResponseData() != null && !data.body().getResponseData().isEmpty()) {
                            try {

                                CryptLib _crypt = new CryptLib();
                                String key = CryptLib.SHA256(Constant.ENCRYPT_KEY, 32); //32 bytes = 256 bit
                                String iv = Constant.INNITIALISING_VECTOR_KEY; //16 bytes = 128 bit

                                for (int i = 0; i < data.body().getResponseData().size(); i++) {

                                    String accountNumber = _crypt.decrypt(data.body().getResponseData().get(i).getAccountNumber(), key, iv);
                                    data.body().getResponseData().get(i).setAccountNumber(accountNumber);
                                    String accountName = _crypt.decrypt(data.body().getResponseData().get(i).getAccountName(), key, iv);
                                    data.body().getResponseData().get(i).setAccountName(accountName);
                                    String address1 = _crypt.decrypt(data.body().getResponseData().get(i).getAddress1(), key, iv);
                                    data.body().getResponseData().get(i).setAddress1(address1);
                                    String phoneNumber = _crypt.decrypt(data.body().getResponseData().get(i).getPhoneNumber(), key, iv);
                                    data.body().getResponseData().get(i).setPhoneNumber(phoneNumber);


                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            recyclerView.setVisibility(View.VISIBLE);
//                        emptyView.setVisibility(View.GONE);
                            accountRecyclerAdapter.setData(data.body() != null ? data.body().getResponseData() : accounts);
                        } else {
                            //show empty view & get error message
                            recyclerView.setVisibility(View.GONE);
//                        emptyView.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
    }

    private void sendAccount() {


        if (isLoggedIn()) {
//            startZenormService();
        }
    }

    private boolean isLoggedIn() {
        AppSettings appSettings = AppSettings.getInstance(requireActivity());
        if (appSettings.getLogin() != null)
            return true;
        else return false;
    }

    private void startZenormService() {
        Intent intent = new Intent(requireActivity(), ZxploreService.class);
        requireActivity().startService(intent);
    }

}
