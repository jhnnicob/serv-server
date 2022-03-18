package com.wktsoft.serv.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.wktsoft.serv.R
import com.wktsoft.serv.databinding.FragmentProductListBinding
import com.wktsoft.serv.util.ActionItemListener
import com.wktsoft.serv.util.ItemOffsetDecoration
import com.wktsoft.serv.util.UIUtil

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.navigation_filter -> {
                    toggleFilter()
                }
            }
            true
        })


        adapter = ProductListAdapter()
        adapter.setActionItemListener(object : ActionItemListener {
            override fun onClickItem(obj: Any?) {
                NavHostFragment.findNavController(this@ProductListFragment).navigate(R.id.productFragment)
            }
        })

        val list: ArrayList<Map<String, String>> = ArrayList()
        val units = listOf("Case", "Pcs")
        for (i in 1..5) {
            val map: HashMap<String, String> = HashMap()
            map["name"] = "Product Name $i"
            map["code"] = "Product Code"
            map["price"] = "500"
            map["unit"] = units.shuffled().take(1)[0]
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

    private fun toggleFilter() {
        if (binding.cardViewFiltersContainer.isVisible) {
            binding.cardViewFiltersContainer.visibility = View.GONE
        } else {
            binding.cardViewFiltersContainer.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}