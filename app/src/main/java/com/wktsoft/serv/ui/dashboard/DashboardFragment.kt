package com.wktsoft.serv.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.wktsoft.serv.R
import com.wktsoft.serv.databinding.FragmentDashboardBinding
import com.wktsoft.serv.event.OnEventNavigateMainMenu
import com.wktsoft.serv.event.OnEventShowBottomNav
import com.wktsoft.serv.util.UIUtil
import com.wktsoft.serv.widget.TextDrawable
import org.greenrobot.eventbus.EventBus


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textDrawable : TextDrawable? =
            context?.let { ContextCompat.getColor(it, R.color.colorSilver) }?.let {
                TextDrawable.Builder()
                    .beginConfig()
                    .width(UIUtil.convertDPStoPixel(requireContext(), 48))  // width in px
                    ?.height(UIUtil.convertDPStoPixel(requireContext(), 48)) // height in px
                    ?.textColor(ContextCompat.getColor(requireContext(), R.color.colorTextHeader))
                    ?.useFont(ResourcesCompat.getFont(requireContext(), R.font.poppins_light))
                    ?.endConfig()
                    ?.buildRound("I",
                        it
                    )
            }
        binding.imageViewNavIcon.setImageDrawable(textDrawable)

        /*binding.textViewNewOrder.setOnClickListener{
           NavHostFragment.findNavController(this).navigate(R.id.orderFragment)
        }
        binding.textViewViewOrder.setOnClickListener{
            EventBus.getDefault().post(OnEventNavigateMainMenu(R.id.navigation_order_list))
        }*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lineData()
        pieData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun lineData() {
        val valsComp1: MutableList<Entry> = mutableListOf()
        val valsComp2: MutableList<Entry> = mutableListOf()

        val c1e1 = Entry(0f, 0f) // 0 == quarter 1
        valsComp1.add(c1e1)
        val c1e2 = Entry(1.5f, 1000f) // 1 == quarter 2 ...
        valsComp1.add(c1e2)
        val c1e3 = Entry(2f, 90f) // 1 == quarter 2 ...
        valsComp1.add(c1e3)
        val c1e4 = Entry(3f, 500f) // 1 == quarter 2 ...
        valsComp1.add(c1e4)

        val c2e1 = Entry(0f, 0f) // 0 == quarter 1
        valsComp2.add(c2e1)
        val c2e2 = Entry(1f, 1000f) // 1 == quarter 2 ...
        valsComp2.add(c2e2)
        val c2e3 = Entry(2f, 1000f) // 1 == quarter 2 ...
        valsComp2.add(c2e3)
        val c2e4 = Entry(3f, 1500f) // 1 == quarter 2 ...
        valsComp2.add(c2e4)

        val setComp1 = LineDataSet(valsComp1, "Company 1")
        setComp1.axisDependency = AxisDependency.LEFT
        setComp1.color = ResourcesCompat.getColor(resources, android.R.color.holo_blue_light, null)
        setComp1.lineWidth = 4f
        setComp1.setDrawCircles(false)
        setComp1.setDrawCircleHole(false)
        setComp1.setDrawValues(false)

        val setComp2 = LineDataSet(valsComp2, "Company 2")
        setComp2.axisDependency = AxisDependency.LEFT
        setComp2.color = ResourcesCompat.getColor(resources, android.R.color.holo_green_dark, null)
        setComp2.lineWidth = 4f
        setComp2.setDrawCircles(false)
        setComp2.setDrawCircleHole(false)
        setComp2.setDrawValues(false)

        val dataSets: MutableList<ILineDataSet> = ArrayList()
        dataSets.add(setComp1)
        dataSets.add(setComp2)

        val data = LineData(dataSets)

        /*val xAxis: XAxis = binding.lineChart.getXAxis()
        xAxis.granularity = 1f // minimum axis-step (interval) is 1

        binding.lineChart.description = null
        binding.lineChart.axisLeft.setDrawLabels(false)
        binding.lineChart.axisRight.setDrawLabels(false)
        binding.lineChart.xAxis.setDrawLabels(false)
        binding.lineChart.legend.isEnabled = false
        binding.lineChart.data = data
        binding.lineChart.invalidate() // refresh*/
    }

    private fun pieData() {
        val count = 3
        val entries1 = java.util.ArrayList<PieEntry>()
        for (i in 0 until count) {
            entries1.add(PieEntry((Math.random() * 60 + 40).toFloat(), "Supplier " + (i + 1)))
        }
        val ds1 = PieDataSet(entries1, "Performance by supplier")
        ds1.setColors(*ColorTemplate.VORDIPLOM_COLORS)
        ds1.sliceSpace = 2f
        ds1.valueTextColor = Color.WHITE
        ds1.valueTextSize = 12f
        ds1.setDrawValues(false)

        /*binding.pieChart.description.isEnabled = false
        binding.pieChart.setCenterTextSize(10f)
        // radius of the center hole in percent of maximum radius
        binding.pieChart.holeRadius = 45f
        binding.pieChart.transparentCircleRadius = 50f
        binding.pieChart.legend.isEnabled = false
        binding.pieChart.setDrawCenterText(false)
        binding.pieChart.setDrawEntryLabels(false)
        binding.pieChart.data = PieData(ds1)*/
    }


}