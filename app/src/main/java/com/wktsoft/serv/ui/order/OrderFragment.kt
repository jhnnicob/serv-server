package com.wktsoft.serv.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.wktsoft.serv.databinding.FragmentOrderBinding
import com.wktsoft.serv.event.OnEventShowBottomNav
import com.wktsoft.serv.util.ItemOffsetDecoration
import com.wktsoft.serv.util.UIUtil
import org.greenrobot.eventbus.EventBus

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null

    private var orderCount : Int? = null

    private var adapter : OrderAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderCount = arguments?.get("orders") as Int?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            run {
                EventBus.getDefault().post(OnEventShowBottomNav(true))
                activity?.onBackPressed()
            }
        }
        binding.toolbar.setOnMenuItemClickListener {
            Toast.makeText(
                requireContext(), "Not available at the moment",
                Toast.LENGTH_SHORT
            ).show()
            true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        EventBus.getDefault().post(OnEventShowBottomNav(false))
        if (orderCount != null) {
            binding.floatingActionButtonAddNewItem.visibility = VISIBLE
            binding.textViewAddItem.visibility = GONE
            binding.textViewOrderFromHistory.visibility = GONE
            adapter = OrderAdapter()
            val list : ArrayList<Map<String, String>> = ArrayList()
            val units = listOf("Case", "Pcs")
            for (i in 1..orderCount!!) {
                val qty = (1..10).random()
                val map : HashMap<String, String> = HashMap()
                map["name"] = "Product Name $i"
                map["detail"] = "$qty " + units.shuffled().take(1)[0] + " COD %-1-1"
                map["price"] = "2,000"
                list.add(map)
            }
            adapter!!.setList(list)
            binding.recyclerViewOrderItems.setHasFixedSize(true)
            binding.recyclerViewOrderItems.itemAnimator = DefaultItemAnimator()
            binding.recyclerViewOrderItems.isNestedScrollingEnabled = false
            binding.recyclerViewOrderItems.layoutManager = LinearLayoutManager(activity)
            binding.recyclerViewOrderItems.addItemDecoration(ItemOffsetDecoration(UIUtil.convertDPStoPixel(requireContext(),4)))
            binding.recyclerViewOrderItems.adapter = adapter
        } else {
            binding.floatingActionButtonAddNewItem.visibility = GONE
            binding.textViewAddItem.visibility = VISIBLE
            binding.textViewOrderFromHistory.visibility = VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}