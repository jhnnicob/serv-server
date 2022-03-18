package com.wktsoft.serv.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wktsoft.serv.R
import com.wktsoft.serv.databinding.ViewHolderOrdersBinding
import com.wktsoft.serv.databinding.ViewHolderProductItemBinding
import com.wktsoft.serv.ui.order.OrderListAdapter
import com.wktsoft.serv.util.ActionItemListener
import com.wktsoft.serv.util.ResourceUtil

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private lateinit var list: List<Map<String, String>>

    private var actionListener: ActionItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewHolderProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val map: Map<String, String> = list[position]
        holder.binding.textViewName.text = map["name"]
        holder.binding.textViewCode.text = map["code"]
        holder.binding.textViewPrice.text = map["price"]
        holder.binding.textViewUnit.text = map["unit"]
        holder.binding.constraintLayoutContainer.setOnClickListener {
            actionListener?.onClickItem(map["qty"])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ViewHolderProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setList(list: List<Map<String, String>>) {
        this.list = list
    }

    fun setActionItemListener(actionListener: ActionItemListener) {
        this.actionListener = actionListener
    }
}