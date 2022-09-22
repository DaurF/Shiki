package mob.dau.ren.shiki.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import mob.dau.ren.shiki.databinding.ListAnimeItemBinding
import mob.dau.ren.shiki.network.AnimeItem
import mob.dau.ren.shiki.network.fullUrl

class ListAnimeAdapter : ListAdapter<AnimeItem, ListAnimeAdapter.ItemViewHolder>(DiffCallback) {
    inner class ItemViewHolder(val binding: ListAnimeItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: AnimeItem) {
                binding.apply {
                    titleAnime.text = item.name
                    uploadImage(imageViewAnime, item.image.fullUrl())
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ListAnimeItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun uploadImage(imageView: ImageView, imageUrl: String?) {
        imageUrl?.let {
            val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
            imageView.load(imageUri)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<AnimeItem>() {
        override fun areItemsTheSame(oldItem: AnimeItem, newItem: AnimeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimeItem, newItem: AnimeItem): Boolean {
            return oldItem == newItem
        }
    }
}