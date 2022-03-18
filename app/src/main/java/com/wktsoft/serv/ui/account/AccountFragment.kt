package com.wktsoft.serv.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.wktsoft.serv.R
import com.wktsoft.serv.databinding.FragmentAccountBinding
import com.wktsoft.serv.databinding.FragmentOrderBinding
import com.wktsoft.serv.event.OnEventShowBottomNav
import com.wktsoft.serv.ui.order.OrderAdapter
import com.wktsoft.serv.util.ItemOffsetDecoration
import com.wktsoft.serv.util.UIUtil
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    private var orderCount: Int? = null

    private var adapter: AccountOrderHistoryAdapter? = null

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
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
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
        adapter = AccountOrderHistoryAdapter()
        var ctr = 125110
        val list: ArrayList<Map<String, String>> = ArrayList()
        for (i in 1..5) {
            val map: HashMap<String, String> = HashMap()
            val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            map["date"] = formatter.format(Date())
            map["price"] = "8,000"
            ctr = (ctr + 1)
            map["orderNumber"] = ctr.toString()
            list.add(map)
        }
        adapter!!.setList(list)
        binding.recyclerViewOrderHistory.setHasFixedSize(true)
        binding.recyclerViewOrderHistory.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewOrderHistory.isNestedScrollingEnabled = false
        binding.recyclerViewOrderHistory.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewOrderHistory.adapter = adapter

        binding.buttonNewOrder.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.orderFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}