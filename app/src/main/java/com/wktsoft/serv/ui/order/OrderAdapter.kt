package com.wktsoft.serv.ui.order

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.wktsoft.serv.R
import com.wktsoft.serv.databinding.ViewHolderOrderItemBinding
import com.wktsoft.serv.util.ResourceUtil

class OrderAdapter: RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private lateinit var list: List<Map<String, String>>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewHolderOrderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val map: Map<String, String> = list[position]
        holder.binding.editTextProduct.setText(map["name"])
        holder.binding.textViewDetail.text = map["detail"]
        holder.binding.textViewPrice.text = map["price"]
        holder.binding.constraintLayoutProductContainer.setOnClickListener {
            if (holder.binding.constraintLayoutBody.isVisible) {
                holder.binding.constraintLayoutBody.visibility = GONE
            } else {
                holder.binding.constraintLayoutBody.visibility = VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ViewHolderOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setList(list : List<Map<String,String>>) {
        this.list = list
    }
}