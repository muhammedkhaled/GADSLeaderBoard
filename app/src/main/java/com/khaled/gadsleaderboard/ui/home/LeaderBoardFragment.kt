package com.khaled.gadsleaderboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.khaled.gadsleaderboard.R
import com.khaled.gadsleaderboard.ui.home.adapters.LeaderBoardAdapter
import com.khaled.gadsleaderboard.utils.Constants.Companion.FILTER_KEY
import com.khaled.gadsleaderboard.utils.Constants.Companion.TYPE_KEY
import com.khaled.gadsleaderboard.utils.Resource
import kotlinx.android.synthetic.main.fragment_leader_board.view.*

class LeaderBoardFragment : Fragment() {

    // views
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    // vars
    private lateinit var leaderBoardAdapter: LeaderBoardAdapter
    private lateinit var viewModel: LeaderBoardViewModel
    private var isHoursFlag = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leader_board, container, false)
        viewModel = ViewModelProvider(this).get(LeaderBoardViewModel::class.java)
        if (arguments != null) {
            viewModel.filterBoardMutableLiveData.value = arguments!!.getString(FILTER_KEY)!!
            isHoursFlag = arguments!!.getBoolean(TYPE_KEY)
        }
        leaderBoardAdapter = LeaderBoardAdapter(isHoursFlag)
        progressBar = view.fragment_leader_board_progress_bar
        recyclerView = view.fragment_leader_board_recycler_View
        recyclerView.apply {
            adapter = leaderBoardAdapter
        }
        getLeaderBoard()
        return view
    }


    private fun getLeaderBoard() {
        viewModel.getLeaderBoard()
        viewModel.leaderBoardMutableLiveData.observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgress(true)
                }
                is Resource.Success -> {
                    response.data?.let { date ->
                        leaderBoardAdapter.differ.submitList(date)
                        showProgress(false)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error => ${response.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }


    private fun showProgress(flag: Boolean) {
        if (flag) {
            recyclerView.visibility = GONE
            progressBar.visibility = VISIBLE
        } else {
            recyclerView.visibility = VISIBLE
            progressBar.visibility = GONE
        }
    }
}