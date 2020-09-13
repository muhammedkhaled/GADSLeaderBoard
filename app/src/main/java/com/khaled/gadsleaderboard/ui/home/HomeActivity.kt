package com.khaled.gadsleaderboard.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.khaled.gadsleaderboard.R
import com.khaled.gadsleaderboard.ui.sumbit.SubmitActivity
import com.khaled.gadsleaderboard.utils.Constants.Companion.FILTER_HOURS
import com.khaled.gadsleaderboard.utils.Constants.Companion.FILTER_KEY
import com.khaled.gadsleaderboard.utils.Constants.Companion.FILTER_SKILL_IQ
import com.khaled.gadsleaderboard.utils.Constants.Companion.TYPE_KEY
import com.khaled.gadsleaderboard.utils.UtilViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"

    // views
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    // vars
    lateinit var viewModel: LeaderBoardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViews()
    }

    private fun initViews() {
        toolbar_submit_button.setOnClickListener {
            startActivity(Intent(this, SubmitActivity::class.java))
        }
        tabLayout = activity_home_tab_layout
        viewPager = activity_home_view_pager
        setupTabLayout()
    }

    private fun setupTabLayout() {
        //  hoursFragment
        val hoursFragment: Fragment = LeaderBoardFragment()
        val hoursBundle = Bundle()
        hoursBundle.putString(FILTER_KEY, FILTER_HOURS)
        hoursBundle.putBoolean(TYPE_KEY, true)
        hoursFragment.arguments = hoursBundle
        //  completedFragment
        val skillIqFragment: Fragment = LeaderBoardFragment()
        val skillIqBundle = Bundle()
        skillIqBundle.putString(FILTER_KEY, FILTER_SKILL_IQ)
        skillIqBundle.putBoolean(TYPE_KEY, false)
        skillIqFragment.arguments = skillIqBundle
        // setup viewpager
        val fragments = arrayOf(hoursFragment, skillIqFragment)
        val titles =
            arrayOf(getString(R.string.learning_leaders), getString(R.string.skill_iq_leaders))
        val utilViewPagerAdapter =
            UtilViewPagerAdapter(supportFragmentManager, this.lifecycle, fragments)
        viewPager.adapter = utilViewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()
        viewPager.setCurrentItem(0, false)
        // setup tabLayout
        // tabLayout OnTabSelectedListener
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                try {
                    Objects.requireNonNull(tab.customView)!!.animate().alpha(1f)
                        .setDuration(300).interpolator = AccelerateInterpolator(3f)
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                    e.printStackTrace()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                try {
                    Objects.requireNonNull(tab.customView)!!.animate().alpha(0.9f)
                        .setDuration(100).interpolator = DecelerateInterpolator(2f)
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                    e.printStackTrace()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}