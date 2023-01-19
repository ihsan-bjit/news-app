package com.ihsan.news_app.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ihsan.news_app.model.Tab
import com.ihsan.news_app.ui.fragment.*

class TabAdapter(manager: FragmentManager, lifecycle:Lifecycle): FragmentStateAdapter(manager,lifecycle) {

    companion object{
        val listTab = listOf(
            Tab(HomeFragment(), "Home"),
            Tab(BusinessFragment(), "Business"),
            Tab(EntertainmentFragment(), "Entertainment"),
            Tab(GeneralFragment(), "General"),
            Tab(HealthFragment(), "Health"),
            Tab(ScienceFragment(), "Science"),
            Tab(SportsFragment(), "Sports"),
            Tab(TechnologyFragment(), "Technology"),
        )
    }
    override fun getItemCount(): Int {
        return  listTab.size
    }

    override fun createFragment(position: Int): Fragment {
        return listTab[position].fragment
    }
}