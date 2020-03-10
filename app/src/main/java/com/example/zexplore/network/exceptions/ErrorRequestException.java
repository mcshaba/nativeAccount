package com.example.zexplore.network.exceptions;

import retrofit2.Response;

public class ErrorRequestException extends Exception {

    private Response response;

    public ErrorRequestException(Response response){
        this.response = response;
    }

    public Response getResponse(){
        return response;
    }
}
