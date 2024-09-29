package com.example.rxandretroapp.services

import com.example.rxandretroapp.model.CryptoModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {
    // GET POST PUT DELETE
    // https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    // BASE_URL -> https://raw.githubusercontent.com/

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    //  fun getData(): Call<List<CryptoModel>>
    fun getData(): Observable<List<CryptoModel>>


}