package com.example.zexplore.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.zexplore.R;
import com.example.zexplore.model.Login;
import com.example.zexplore.model.LoginResponse;
import com.example.zexplore.service.ZxploreService;
import com.example.zexplore.util.AppSettings;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.util.Constant;
import com.example.zexplore.util.CryptLib;
import com.example.zexplore.viewmodel.LoginViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.Objects;

import static com.example.zexplore.util.Constant.ERROR_CODE;
import static com.example.zexplore.util.Constant.SUCCESS_CODE;
import static com.example.zexplore.viewmodel.LoginViewModel.AuthenticationState.AUTHENTICATED;
import static com.example.zexplore.viewmodel.LoginViewModel.AuthenticationState.INVALID_AUTHENTICATION;

/**
 * Created by Ehigiator David on 20/03/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 * Modified by Shaba
 * michael.shaba@cyberspace.net.ng
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;
    private TextInputEditText usernameEditText;
    private ProgressDialog progressDialog;

    private TextInputEditText passwordEditText;

    private MaterialButton loginButton;
    private String emailValue, passwordValue;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);
        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        final NavController navController = Navigation.findNavController(view);


        usernameEditText = view.findViewById(R.id.username_edit_text);
        passwordEditText = view.findViewById(R.id.password_edit_text);


        loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(view1 -> {
            AppUtility.hideKeyboard(requireActivity());
            validateDate();

        });

        requireActivity().addOnBackPressedCallback(getViewLifecycleOwner(), () -> {
            viewModel.refuseAuthentication();
            navController.popBackStack(R.id.mainFragment, false);
            return true;
        });

        final View root = view;
        viewModel.authenticationState.observe(getViewLifecycleOwner(),
                authenticationState -> {
                    switch (authenticationState) {
                        case AUTHENTICATED:
                            navController.popBackStack();
                            break;
                        case INVALID_AUTHENTICATION:
//                            Snackbar.make(root,
//                                    R.string.invalid_credentials,
//                                    Snackbar.LENGTH_SHORT
//                            ).show();
                            break;
                    }
                });


    }

    private void validateDate() {
        boolean valid = true;
        passwordValue = passwordEditText.getText().toString();
        emailValue = usernameEditText.getText().toString();

        if(emailValue.length() ==0){
            valid = false;
            usernameEditText.setError("Email isd required");
        }

        if(passwordValue.length() == 0){
            valid = false;
            passwordEditText.setError("Password is Required");
        }
        if(valid){
            validateSubmission(Objects.requireNonNull(usernameEditText.getText()).toString().trim(),
                    Objects.requireNonNull(passwordEditText.getText()).toString().trim());

        }
    }

    private void validateSubmission(String username, String password) {
        if(AppUtility.isNetworkAvailable(requireActivity())){
            progressDialog = new ProgressDialog(requireContext());
            progressDialog.setMessage("Authenticating...");
            progressDialog.setCancelable(true);
            progressDialog.show();
            try {

                if(username.contains("@")){
                    username = username.substring(0, username.indexOf("@"));
                }
                CryptLib _crypt = new CryptLib();
                String key = CryptLib.SHA256(Constant.ENCRYPT_KEY, 32); //32 bytes = 256 bit
                String iv = Constant.INNITIALISING_VECTOR_KEY; //16 bytes = 128 bit


                String usernameEncryptString = _crypt.encrypt(username, key, iv);
                String passwordEncryptString = _crypt.encrypt(password, key, iv); //encrypt

                AppSettings appSettings = AppSettings.getInstance(requireActivity());

                LiveData<LoginResponse> response = viewModel.authenticate(usernameEncryptString, passwordEncryptString);
                response.observe(this, loginResponse -> {

                    if(loginResponse != null){
                        progressDialog.dismiss();
                        if(loginResponse.getMessage().equals("Loggin Successful") && loginResponse.getData().getResponseCode().equals(Constant.SUCCESS_CODE)) {
                            LoginViewModel.authenticationState.setValue(AUTHENTICATED);
                            appSettings.setLogin(loginResponse);
                            appSettings.setUser(loginResponse.getData().getUser());
                            appSettings.Save(requireContext());

                        } else if(!loginResponse.getStatus()){
                            LoginViewModel.authenticationState.setValue(INVALID_AUTHENTICATION);
                            AppUtility.okDialog(requireActivity(), "Login Failed", loginResponse.getData().getResponseMessage());
                            passwordEditText.setText("");
                        }


                    } else {
                        progressDialog.dismiss();
                        LoginViewModel.authenticationState.setValue(INVALID_AUTHENTICATION);
                        AppUtility.okDialog(requireActivity(), "Login Failed", "Time out Error");
                        passwordEditText.setText("");
                    }


                });

            } catch (Exception e) {
                e.printStackTrace();

            }
        }else {
            Toast.makeText(requireActivity(), "No Internet connection", Toast.LENGTH_LONG).show();
        }


    }

}
