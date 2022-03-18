package com.wktsoft.serv.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wktsoft.serv.R
import com.wktsoft.serv.databinding.FragmentOrderListBinding
import com.wktsoft.serv.util.ActionItemListener
import com.wktsoft.serv.util.ItemOffsetDecoration
import com.wktsoft.serv.util.UIUtil


class OrderListFragment : Fragment() {

    private var _binding: FragmentOrderListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: OrderListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.navigation_filter -> {
                    toggleFilter()
                }
                R.id.navigation_new_order -> {
                    NavHostFragment.findNavController(this).navigate(R.id.orderFragment)
                }
                R.id.navigation_repeat_order,
                R.id.navigation_select_multiple -> {
                    Toast.makeText(requireContext(), "Not available at the moment", LENGTH_SHORT)
                        .show()
                }
            }
            true
        })


        adapter = OrderListAdapter()
        adapter.setActionItemListener(object : ActionItemListener {
            override fun onClickItem(obj: Any?) {
                val bundle = Bundle()
                bundle.putInt("orders", obj.toString().toInt())
                NavHostFragment.findNavController(this@OrderListFragment).navigate(
                    R.id.orderFragment,
                    bundle
                )
            }
        })

        val list: ArrayList<Map<String, String>> = ArrayList()
        val statuses = listOf("Sent", "Draft", "Confirm")
        for (i in 1..5) {
            val qty = (1..10).random()
            val map: HashMap<String, String> = HashMap()
            map["name"] = "Customer Name $i"
            map["detail"] = "$qty Items COD %-1-1"
            map["qty"] = "$qty"
            map["price"] = "50,000"
            map["status"] = statuses.shuffled().take(1)[0]
            list.add(map)
        }
        adapter.setList(list)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.addItemDecoration(
            ItemOffsetDecoration(
                UIUtil.convertDPStoPixel(
                    requireContext(),
                    4
                )
            )
        )
        binding.recyclerView.adapter = adapter

        binding.floatingActionButtonAddNewOrder.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.orderFragment)
        }

        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val fromPos: Int = viewHolder.adapterPosition
                    val toPos: Int = target.adapterPosition
                    // move item in `fromPos` to `toPos` in adapter.
                    return true // true if moved, false otherwise
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    TODO("Not yet implemented")
                }
            })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.setOnScrollChangeListener({ v, scrollX, scrollY, oldScrollX, oldScrollY ->

        })
    }

    private fun toggleFilter() {
        if (binding.cardViewFiltersContainer.isVisible) {
            binding.cardViewFiltersContainer.visibility = GONE
        } else {
            binding.cardViewFiltersContainer.visibility = VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}