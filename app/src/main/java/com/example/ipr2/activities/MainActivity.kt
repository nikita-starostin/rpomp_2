package com.example.ipr2.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ipr2.R
import com.example.ipr2.adapter.FeedAdapter
import com.example.ipr2.common.RetrofitServiceGenerator
import com.example.ipr2.database.DatabaseOpenHelper
import com.example.ipr2.database.entries.rssCacheEntry.RSSCacheCommands
import com.example.ipr2.database.entries.rssCacheEntry.RSSCacheQueries
import com.example.ipr2.models.RSSObject
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val RSS_link = "https://people.onliner.by/feed"
    private lateinit var databaseOpenHelper: DatabaseOpenHelper
    private var offline = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.title = resources.getString(R.string.news_online)
        setSupportActionBar(toolbar)
        val linearLayoutManager = LinearLayoutManager(
            baseContext,
            RecyclerView.VERTICAL, false
        )
        recyclerView.layoutManager = linearLayoutManager
        databaseOpenHelper = DatabaseOpenHelper(this)
        loadRSS()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_refresh) {
            loadRSS()
        }
        return true
    }

    private fun loadRSS() {
        try {
            loadRSSFromInternet()
        } catch (e: Throwable) {
            loadRSSFromCache()
        }
    }

    private fun loadRSSFromInternet() {
        val call = RetrofitServiceGenerator.createService().getFeed(RSS_link)
        call.enqueue(object : Callback<RSSObject> {
            override fun onFailure(call: Call<RSSObject>?, t: Throwable?) {
                Log.d("ResponseError", "failed")
                loadRSSFromCache()
            }

            override fun onResponse(
                call: Call<RSSObject>?, response:
                Response<RSSObject>?
            ) {
                if (response?.isSuccessful == true) {
                    response.body()?.let { rssObject ->
                        offline = false
                        Log.d("Response", "$rssObject")
                        loadRssObject(rssObject)
                        RSSCacheCommands.updateCache(databaseOpenHelper, rssObject)
                    }
                }
            }
        })
    }

    private fun loadRSSFromCache() {
        toolbar.title = resources.getString(R.string.news_offline)
        offline = true
        loadRssObject(RSSCacheQueries.getCache(databaseOpenHelper))
    }

    private fun loadRssObject(rssObject: RSSObject?) {
        if (rssObject != null) {
            val adapter = FeedAdapter(rssObject, baseContext, offline)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}
