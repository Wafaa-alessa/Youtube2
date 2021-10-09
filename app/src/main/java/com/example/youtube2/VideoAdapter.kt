package com.example.youtube2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import kotlinx.android.synthetic.main.video_item.view.*

class VideoAdapter(
    private val videos: Array<Array<String>>,
    private val youTubeplayer: YouTubePlayer): RecyclerView.Adapter<VideoAdapter.VideoViewHolder>(){
    class VideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val button: Button = itemView.button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.video_item,
            parent,
            false
        )
        return VideoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val nowTitle = videos[position][0]
        val nowLink = videos[position][1]
        holder.button.text = nowTitle
        holder.button.setOnClickListener {
            youTubeplayer.loadVideo(nowLink, 0f)
        }
    }

    override fun getItemCount() = videos.size
}