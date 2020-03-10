package com.example.zexplore.network;

import com.example.zexplore.model.AccountBaseResponse;
import com.example.zexplore.model.AccountClass;
import com.example.zexplore.model.AccountEdit;
import com.example.zexplore.model.AccountModel;
import com.example.zexplore.model.Bvn;
import com.example.zexplore.model.City;
import com.example.zexplore.model.Country;
import com.example.zexplore.model.Login;
import com.example.zexplore.model.LoginResponse;
import com.example.zexplore.model.Occupation;
import com.example.zexplore.model.SaveAccountResponse;
import com.example.zexplore.model.State;
import com.example.zexplore.model.SubmitForm;
import com.example.zexplore.model.Title;
import com.example.zexplore.model.ZenithBaseResponse;
import com.example.zexplore.model.formresponse.AccountResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetworkInterface {

    @POST("Security/authUser")
    Single<Response<LoginResponse>> login(@Body Login login);

    @POST("accounts/VerifySingleBVN")
    Single<Bvn> verifyBvn(@Header("Authorization") String token, @Body Bvn bvn);

    @GET("accounts/Titles")
    Single<Title> getTitles(@Header("Authorization") String token);

    @GET("accounts/States")
    Single<State> getStates(@Header("Authorization") String token);

    @GET("accounts/Occupations")
    Single<Occupation> getOccupations(@Header("Authorization") String token);

    @GET("accounts/Countries")
    Single<Country> getCountries(@Header("Authorization") String token);

    @GET("accounts/Cities")
    Single<City> getCities(@Header("Authorization") String token);

    @GET("accounts/AccountClass")
    Single<ZenithBaseResponse<List<AccountClass>>> getAccountClass(@Header("Authorization") String token);

    @POST("accounts/CreateAccount")
    Single<ZenithBaseResponse> createAccount(@Header("Authorization") String token,@Body SubmitForm form);

    @GET("accounts/GetAccountNumber/{batchId}")
    Single<ZenithBaseResponse> checkAccountStatus(@Header("Authorization") String token,@Path("batchId") String batchId);

    @POST("accounts/SaveAccount")
    Single<Response<LoginResponse>> saveAccount(@Header("Authorization") String token, @Body SubmitForm form);

    @GET("accounts/VerifyReferenceID/{Ref_Id}")
    Single<Response<LoginResponse>> verifyReferenceId(@Header("Authorization") String token, @Path("Ref_Id") String RefId);

    @GET("accounts/GetAccountsByRsmID/{RsmID}/{Status}")
    Single<Response<AccountBaseResponse<List<AccountModel>>>> getAccountsByRsmID(@Header("Authorization") String token, @Path("RsmID") String rsmID, @Path("Status") String status);

    @GET("accounts/GetAccountByRefID/{Ref_Id}")
    Single<Response<SaveAccountResponse>> GetAccountDetailsByRefID(@Header("Authorization") String token, @Path("Ref_Id") String rsmID);


    @GET("accounts/GetAccountByRefID/{Ref_Id}")
    Single<Response<AccountResponse>> getAccountByReferenceId(@Header("Authorization") String token, @Path("Ref_Id") String RefId);

}
