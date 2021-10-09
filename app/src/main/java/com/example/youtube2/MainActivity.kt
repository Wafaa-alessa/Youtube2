package com.example.youtube2

import android.widget.GridView
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MainActivity : AppCompatActivity() {

    private lateinit var youTubeView: YouTubePlayerView
    private lateinit var youTubeplayer: YouTubePlayer
    private var nowVideo = 0
    private var time = 0f
    private val videos: Array<Array<String>> = arrayOf(
        arrayOf("Numbers Game", "tb2T8mT6N6Y"),
        arrayOf("Calculator", "EpP6KgJtHTk"),
        arrayOf("Guess the Phrase", "I9YU0bfxev0") ,
        arrayOf("Username and Password", "fVoGwVTyIr8"),
        arrayOf("GUI Username and Password", "fVoGwVTyIr8"),
        arrayOf("Country Capitals", "CEg7CLCb5P0"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val checkButton: Button = findViewById(R.id.button)

        checkButton.setOnClickListener {
            if (checkForInternet(this)) {
                Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
            }
        }
        youTubeView = findViewById(R.id.youtube)
        youTubeView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubeplayer = youTubePlayer
                youTubeplayer.loadVideo(videos[nowVideo][1], time)
                initialize()
            }
        })

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            youTubeView.enterFullScreen()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            youTubeView.exitFullScreen()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("nowVideo", nowVideo)
        outState.putFloat("time", time)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        nowVideo = savedInstanceState.getInt("nowVideo", 0)
        time = savedInstanceState.getFloat("time", 0f)
    }

    private fun initialize(){

        val gridView: GridView = findViewById(R.id.grid)
        gridView.adapter = GridAdapter(videos, youTubeplayer,this@MainActivity)

    }

    //internet
    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}