package com.example.rxandretroapp.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rxandretroapp.R
import com.example.rxandretroapp.adapter.RecyclerViewAdaptor
import com.example.rxandretroapp.model.CryptoModel
import com.example.rxandretroapp.services.CryptoAPI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdaptor.Listener {

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: List<CryptoModel> = listOf()
    private var recyclerViewAdapter: RecyclerViewAdaptor? = null
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        compositeDisposable = CompositeDisposable()

        // RecyclerView setup
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recyclerView).layoutManager = layoutManager

        // Load data
        loadData()
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(CryptoAPI::class.java)

        compositeDisposable?.add(
            retrofit.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { cryptoList -> handleResponse(cryptoList) },
                    { throwable -> throwable.printStackTrace() }
                )
        )
    }

    private fun handleResponse(cryptoList: List<CryptoModel>) {
        cryptoModels = cryptoList
        cryptoModels.let {
            recyclerViewAdapter = RecyclerViewAdaptor(cryptoModels, this)
            findViewById<RecyclerView>(R.id.recyclerView).adapter = recyclerViewAdapter
        }
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        // Handle item click
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}