package com.wktsoft.serv.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.wktsoft.serv.R
import com.wktsoft.serv.databinding.FragmentAccountsBinding
import com.wktsoft.serv.ui.order.OrderListAdapter
import com.wktsoft.serv.util.ActionItemListener
import com.wktsoft.serv.util.ItemOffsetDecoration
import com.wktsoft.serv.util.UIUtil

class AccountsFragment : Fragment() {

    private var _binding: FragmentAccountsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: AccountsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AccountsAdapter()
        adapter.setActionItemListener(object : ActionItemListener {
            override fun onClickItem(obj: Any?) {
                NavHostFragment.findNavController(this@AccountsFragment).navigate(R.id.accountFragment)
            }
        })
        val list: ArrayList<Map<String, String>> = ArrayList()
        for (i in 1..5) {
            val map: HashMap<String, String> = HashMap()
            map["name"] = "Customer Name $i"
            map["address"] = "Customer Address"
            map["customerId"] = "$i"
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}