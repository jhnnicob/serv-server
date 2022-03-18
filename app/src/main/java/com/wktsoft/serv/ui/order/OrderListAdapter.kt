package com.wktsoft.serv.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wktsoft.serv.R
import com.wktsoft.serv.databinding.ViewHolderOrdersBinding
import com.wktsoft.serv.util.ActionItemListener
import com.wktsoft.serv.util.ResourceUtil

class OrderListAdapter : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    private lateinit var list: List<Map<String, String>>

    private var actionListener : ActionItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewHolderOrdersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val map: Map<String, String> = list[position]
        holder.binding.textViewName.text = map["name"]
        holder.binding.textViewDetail.text = map["detail"]
        holder.binding.textViewPrice.text = map["price"]
        val status : String? = map["status"]
        holder.binding.textViewStatus.text = status
        if (status.equals("draft", ignoreCase = true)) {
            ResourceUtil.getInstance()?.getColor(R.color.colorRose)?.let {
                holder.binding.textViewStatus.setTextColor(
                    it
                )
            }
        } else  if (status.equals("sent", ignoreCase = true)) {
            ResourceUtil.getInstance()?.getColor(R.color.colorPrimary)?.let {
                holder.binding.textViewStatus.setTextColor(
                    it
                )
            }
        } else  if (status.equals("confirm", ignoreCase = true)) {
            ResourceUtil.getInstance()?.getColor(R.color.colorMint)?.let {
                holder.binding.textViewStatus.setTextColor(
                    it
                )
            }
        }
        holder.binding.constraintLayoutContainer.setOnClickListener {
            actionListener?.onClickItem(map["qty"])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ViewHolderOrdersBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setList(list: List<Map<String, String>>) {
        this.list = list
    }

    fun setActionItemListener(actionListener: ActionItemListener) {
        this.actionListener = actionListener
    }
}


