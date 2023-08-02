package com.example.movieapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Secure.getString
import android.util.Log
import android.widget.Toast
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.adapters.MoviesAdapter
import com.example.movieapplication.data.Movie
import com.example.movieapplication.repositories.MoviesRepository
import com.example.movieapplication.repositories.MoviesRepository.getNowPlayingMovies
import com.example.movieapplication.repositories.MoviesRepository.getTopRatedMovies
import com.example.movieapplication.repositories.MoviesRepository.getUpcomingMovies

class MainActivity : AppCompatActivity() {

    //now playing
    private lateinit var nowPlayingMovies: RecyclerView
    private lateinit var nowPlayingMoviesAdapter: MoviesAdapter
    private lateinit var nowPlayingMoviesLayoutMgr: LinearLayoutManager
    private var nowPlayingMoviesPage = 1

    //popular movies
    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager
    private var popularMoviesPage = 1

    //top rated movies
    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager
    private var topRatedMoviesPage = 1


    //upcoming
    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager
    private var upcomingMoviesPage = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //now playing movies
        nowPlayingMovies = findViewById(R.id.now_playing_movies)
        nowPlayingMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        nowPlayingMovies.layoutManager = nowPlayingMoviesLayoutMgr
        nowPlayingMoviesAdapter = MoviesAdapter(mutableListOf())
        nowPlayingMovies.adapter = nowPlayingMoviesAdapter


        //popular movies
        popularMovies = findViewById(R.id.popular_movies)
        popularMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MoviesAdapter(mutableListOf())
        popularMovies.adapter = popularMoviesAdapter


        //top rated movies
        topRatedMovies = findViewById(R.id.top_rated_movies)
        topRatedMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter = MoviesAdapter(mutableListOf())
        topRatedMovies.adapter = topRatedMoviesAdapter


        //upcoming movies
        upcomingMovies = findViewById(R.id.upcoming_movies)
        upcomingMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
        upcomingMoviesAdapter = MoviesAdapter(mutableListOf())
        upcomingMovies.adapter = upcomingMoviesAdapter


        //calling movie genres
        getNowPlayingMovies()
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()



        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )

        MoviesRepository.getUpcomingMovies(
            upcomingMoviesPage,
            ::UpcomingMoviesFetched,
            ::onError
        )
    }

    private fun getNowPlayingMovies() {
        MoviesRepository.getNowPlayingMovies(
            nowPlayingMoviesPage,
            ::nowPlayingMoviesFetched,
            ::onError
        )


    }

    private fun getUpcomingMovies() {
        MoviesRepository.getUpcomingMovies(
            upcomingMoviesPage,
            ::UpcomingMoviesFetched,
            ::onError
        )
    }

    private fun UpcomingMoviesFetched(movies: List<Movie>) {
        upcomingMoviesAdapter.appendMovies(movies)
        attachUpcomingMoviesOnScrollListener()
    }

    private fun attachUpcomingMoviesOnScrollListener() {
        upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    upcomingMovies.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    getUpcomingMovies()
                }
            }
        })
    }


    private fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                val visibleItemCount = popularMoviesLayoutMgr.childCount
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    //popularMovies.removeOnScrollListener(this)
                    Log.d("MainActivity", "Fetching movies")
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    fun nowPlayingMoviesFetched(movies: List<Movie>) {
        nowPlayingMoviesAdapter.appendMovies(movies)
        attachNowPlayingMoviesOnScrollListener()
    }

    private fun attachNowPlayingMoviesOnScrollListener() {
        nowPlayingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = nowPlayingMoviesAdapter.itemCount
                val visibleItemCount = nowPlayingMoviesLayoutMgr.childCount
                val firstVisibleItem = nowPlayingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    nowPlayingMovies.removeOnScrollListener(this)
                    nowPlayingMoviesPage++
                    getNowPlayingMovies()
                }
            }
        })
    }

    fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()


    }

    private fun getPopularMovies() {
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }


    private fun getTopRatedMovies() {
        MoviesRepository.getTopRatedMovies(
            topRatedMoviesPage,
            ::onTopRatedMoviesFetched,
            ::onError
        )
    }

    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        topRatedMoviesAdapter.appendMovies(movies)
        attachTopRatedMoviesOnScrollListener()
    }

    private fun attachTopRatedMoviesOnScrollListener() {
        topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedMoviesLayoutMgr.itemCount
                val visibleItemCount = topRatedMoviesLayoutMgr.childCount
                val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    topRatedMovies.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()

    }
}






