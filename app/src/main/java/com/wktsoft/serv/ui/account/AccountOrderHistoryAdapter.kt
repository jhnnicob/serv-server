package com.wktsoft.serv.ui.account

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wktsoft.serv.databinding.ViewHolderAccountBinding
import com.wktsoft.serv.databinding.ViewHolderOrderHistoryBinding
import com.wktsoft.serv.util.ActionItemListener

class AccountOrderHistoryAdapter: RecyclerView.Adapter<AccountOrderHistoryAdapter.ViewHolder>() {

    private lateinit var list: List<Map<String, String>>

    private var actionListener : ActionItemListener? = null

    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewHolderOrderHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val map: Map<String, String> = list[position]
        holder.binding.textViewDate.text = map["date"]
        holder.binding.textViewOrderNumber.text = map["orderNumber"]
        holder.binding.textViewOrderPrice.text = map["price"]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ViewHolderOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setList(list : List<Map<String,String>>) {
        this.list = list
    }

    fun setActionItemListener(actionListener : ActionItemListener) {
        this.actionListener = actionListener
    }
}