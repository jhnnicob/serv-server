package com.wktsoft.serv.ui.account

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.wktsoft.serv.R
import com.wktsoft.serv.databinding.ViewHolderAccountBinding
import com.wktsoft.serv.util.ActionItemListener
import com.wktsoft.serv.util.UIUtil
import com.wktsoft.serv.widget.TextDrawable

class AccountsAdapter : RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {

    private lateinit var list: List<Map<String, String>>

    private var actionListener : ActionItemListener? = null

    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewHolderAccountBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val map: Map<String, String> = list[position]
        val name : String? = map["name"]
        holder.binding.textViewInitial.text = name?.first().toString()
        holder.binding.textViewName.text = name
        holder.binding.textViewAddress.text = map["address"]
        holder.binding.constraintLayoutContainer.setOnClickListener {
            actionListener?.onClickItem(map["customerId"])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ViewHolderAccountBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setList(list : List<Map<String,String>>) {
        this.list = list
    }

    fun setActionItemListener(actionListener : ActionItemListener) {
        this.actionListener = actionListener
    }
}