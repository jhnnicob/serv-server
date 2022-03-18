package com.wktsoft.serv.ui.product

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
import com.wktsoft.serv.databinding.FragmentProductBinding
import com.wktsoft.serv.event.OnEventShowBottomNav
import com.wktsoft.serv.ui.account.AccountOrderHistoryAdapter
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null

    private var orderCount: Int? = null

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
        _binding = FragmentProductBinding.inflate(inflater, container, false)
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}