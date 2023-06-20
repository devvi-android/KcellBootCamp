package kz.kcell.kcellbootcamp.presentation.moviesList

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.kcell.kcellbootcamp.BuildConfig
import kz.kcell.kcellbootcamp.data.entities.Movie
import kz.kcell.kcellbootcamp.databinding.MovieItemBinding
import kz.kcell.kcellbootcamp.utils.loadImage
import kz.kcell.kcellbootcamp.utils.orZero
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import kotlin.coroutines.coroutineContext

class MoviesAdapter(
    private val onClick: (item: Movie) -> Unit
) : ListAdapter<Movie, MoviesAdapter.MovieViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = MovieViewHolder(
        onClick, MovieItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: MoviesAdapter.MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(
        private val onClick: (item: Movie) -> Unit, private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) = with(binding) {
            root.setOnClickListener {
                onClick.invoke(item)
            }
            movieItemTitle.text = item.title
            movieItemRelease.text = item.releaseDate
            movieItemRatingbar.rating = item.voteAverage.toFloat().orZero()
            movieItemPoster.loadImage(
                BuildConfig.IMAGE_URL + item.posterPath, progressBar
            )
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem

}

