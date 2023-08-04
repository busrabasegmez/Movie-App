package com.example.movieapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop


//intents
const val MOVIE_BACK_PHOTO = "extra_movie_back_photo"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_DATE = "extra_movie_date"
const val MOVIE_DETAILS = "extra_movie_details"
const val MOVIE_RATING = "extras_movie_rating"

class MovieDetails : AppCompatActivity() {

    private lateinit var backPhoto: ImageView
    private lateinit var poster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieDetails: TextView
    private lateinit var releasedDate: TextView
    private lateinit var imdbRating : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)


        backPhoto = findViewById(R.id.backPoster)
        poster = findViewById(R.id.moviePoster)
        movieTitle = findViewById(R.id.movieTitle)
        movieDetails = findViewById(R.id.movieDetailsTText)
        releasedDate = findViewById(R.id.date)
        imdbRating = findViewById(R.id.imdbRating)


        val extras = intent.extras

        if (extras != null) {
            getDetails(extras)
        } else {
            finish()
        }
    }


    private fun getDetails(extras: Bundle) {
        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }
        extras.getString (MOVIE_BACK_PHOTO)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$backdropPath")
                .transform(CenterCrop())
                .into(backPhoto)
        }

        movieTitle.text = extras.getString(MOVIE_TITLE, "")
        releasedDate.text = extras.getString(MOVIE_DATE, "")
        movieDetails.text = extras.getString(MOVIE_DETAILS, "")
        imdbRating.text= extras.getFloat(MOVIE_RATING).toString()

    }
}