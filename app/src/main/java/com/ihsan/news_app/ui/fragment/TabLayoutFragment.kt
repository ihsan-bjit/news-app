package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ihsan.news_app.R
import com.ihsan.news_app.adapter.TabAdapter
import com.ihsan.news_app.adapter.TabAdapter.Companion.listTab

class TabLayoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //        Tab layout
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPage = view.findViewById<ViewPager2>(R.id.view_pager_2)

        val tabAdapter = TabAdapter(childFragmentManager, lifecycle)
        viewPage.adapter = tabAdapter
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = listTab[position].category
        }.attach()
    }

}