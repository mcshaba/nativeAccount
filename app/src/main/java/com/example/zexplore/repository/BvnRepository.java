package com.example.zexplore.repository;

import com.example.zexplore.model.Bvn;
import com.example.zexplore.network.NetworkClient;
import com.example.zexplore.network.NetworkInterface;
import com.example.zexplore.util.Constant;

import io.reactivex.Single;

public class BvnRepository {

    private NetworkInterface networkInterface;

    public BvnRepository() {
        this.networkInterface = NetworkClient.getNetworkInterface(Constant.ZENITH_BASE_URL);
    }

    public Single<Bvn> verifyBvn(String token, Bvn bvn){
        return networkInterface.verifyBvn(token, bvn);
    }
}
