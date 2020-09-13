package com.khaled.gadsleaderboard.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.khaled.gadsleaderboard.R
import com.khaled.gadsleaderboard.data.models.LeaderBoardResponseItem
import com.khaled.gadsleaderboard.utils.ImageViewUtils
import kotlinx.android.synthetic.main.item_leadeboard.view.*



class LeaderBoardAdapter(val type: Boolean) :
    RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder>() {

    class LeaderBoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderBoardViewHolder {
        return LeaderBoardViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_leadeboard,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: LeaderBoardViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.itemView.apply {
            item_leader_board_name.text = item.name
            if (type)
                item_leader_board_score.text = "${item.hours} Learning hours, ${item.country}"
            else
                item_leader_board_score.text = "${item.score} Skill IQ, ${item.country}"

            ImageViewUtils.fitImage(
                context,
                item_leader_board_img,
                item.badgeUrl
//                ,R.drawable.ic_placeholder
            )

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<LeaderBoardResponseItem>() {
        override fun areItemsTheSame(
            oldItem: LeaderBoardResponseItem,
            newItem: LeaderBoardResponseItem
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: LeaderBoardResponseItem,
            newItem: LeaderBoardResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}