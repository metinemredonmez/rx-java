package com.example.rxandretroapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rxandretroapp.R
import com.example.rxandretroapp.model.CryptoModel

class RecyclerViewAdaptor(
    private val cryptoList: List<CryptoModel>,
    private val listener: Listener
) : RecyclerView.Adapter<RecyclerViewAdaptor.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    class RowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cryptoModel: CryptoModel, listener: Listener) {
            itemView.findViewById<TextView>(R.id.currencyTextView).text = cryptoModel.currency
            itemView.findViewById<TextView>(R.id.priceTextView).text = cryptoModel.price
            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_layout, parent, false)
        return RowHolder(itemView)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val cryptoModel = cryptoList[position]
        holder.bind(cryptoModel, listener)
    }

    override fun getItemCount() = cryptoList.size
}