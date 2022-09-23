package mob.dau.ren.shiki.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import mob.dau.ren.shiki.R
import mob.dau.ren.shiki.databinding.ListAnimeItemBinding
import mob.dau.ren.shiki.network.AnimeItem
import mob.dau.ren.shiki.network.fullImageUrl

class ListAnimeAdapter(
    private val context: Context,
    private val onItemClick: (AnimeItem) -> Unit
) : ListAdapter<AnimeItem, ListAnimeAdapter.ItemViewHolder>(DiffCallback) {
    private val radius = context.resources.getDimension(R.dimen.list_anime_image_radius)

    inner class ItemViewHolder(val binding: ListAnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AnimeItem) {
            binding.apply {
                titleAnime.text = item.name
                uploadImage(imageViewAnime, item.image.fullImageUrl())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListAnimeItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    private fun uploadImage(imageView: ShapeableImageView, imageUrl: String?) {
        imageView.shapeAppearanceModel = imageView.shapeAppearanceModel.toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius)
            .build()
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