package com.example.zexplore.viewmodel;

import android.app.Application;

import com.example.zexplore.model.Login;
import com.example.zexplore.model.LoginResponse;
import com.example.zexplore.repository.LoginRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import okhttp3.Response;

import static com.example.zexplore.util.Constant.ERROR_CODE;
import static com.example.zexplore.util.Constant.SUCCESS_CODE;

/**
 * Created by Ehigiator David on 21/03/2019.
 * Modified by Shaba Michael on 27/03/2019
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 * michael.shaba@cyberspace.net.ng
 */
public class LoginViewModel extends ViewModel{

    private LoginRepository repository;
    private LiveData<LoginResponse> response;

    public enum AuthenticationState {

        UNAUTHENTICATED,        // Initial stateOfResident, the user needs to authenticate
        AUTHENTICATED,          // The user has authenticated successfully
        INVALID_AUTHENTICATION  // Authentication failed
    }

   public static final MutableLiveData<AuthenticationState> authenticationState =
            new MutableLiveData<>();

    String username = "";

    public LoginViewModel() {
        // When Main Activity is launched, by default the user is unauthenticated
        authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
        username = "";
        repository = new LoginRepository();
    }

    public LiveData<LoginResponse> authenticate(String username, String password) {

        Login login = new Login();
        login.setUsername(username);
        login.setPassword(password);
        return repository.login(login);

    }

    public void refuseAuthentication() {
        authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
    }
}
