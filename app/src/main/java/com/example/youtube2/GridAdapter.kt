package com.example.youtube2


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer


class GridAdapter(
    private val videos: Array<Array<String>>,
    private val youTubeplayer: YouTubePlayer,
    private val context:Context
):BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    lateinit var buttonView:Button

    override fun getCount(): Int {
        return videos.size
    }

    override fun getItem(position: Int): Any {
        return videos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        val nowTitle = videos[position][0]
        val nowLink = videos[position][1]

        if (layoutInflater==null)
        {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        }
        if (convertView == null)
        {
            convertView = layoutInflater!!.inflate(R.layout.video_item,null)

        }
        if (convertView != null) {
            buttonView = convertView.findViewById(R.id.button)
            buttonView.text = nowTitle
            buttonView.setOnClickListener{
                youTubeplayer.loadVideo(nowLink, 0f)
            }
        }
        return convertView
    }


}