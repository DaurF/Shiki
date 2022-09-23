package mob.dau.ren.shiki.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mob.dau.ren.shiki.databinding.ListGenreItemBinding
import mob.dau.ren.shiki.network.Genre

class ListGenresAdapter : ListAdapter<Genre, ListGenresAdapter.GenreViewHolder>(DiffCallback) {
    inner class GenreViewHolder(private val binding: ListGenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.genreTextView.text = genre.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            ListGenreItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }
    }
}