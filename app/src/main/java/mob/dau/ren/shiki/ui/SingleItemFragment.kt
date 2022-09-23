package mob.dau.ren.shiki.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import mob.dau.ren.shiki.R
import mob.dau.ren.shiki.ShikiApplication
import mob.dau.ren.shiki.databinding.FragmentSingleItemBinding
import mob.dau.ren.shiki.repository.ListAnimeRepository
import mob.dau.ren.shiki.util.BASE_URL
import mob.dau.ren.shiki.util.smartTruncate
import mob.dau.ren.shiki.viewmodels.Factory
import mob.dau.ren.shiki.viewmodels.ListAnimeViewModel

private const val TAG = "SingleItemFragment"
class SingleItemFragment : Fragment() {
    private val viewModel: ListAnimeViewModel by activityViewModels {
        Factory(
            ListAnimeRepository((activity?.application as ShikiApplication).database)
        )
    }

    private var _binding: FragmentSingleItemBinding? = null
    private val binding get() = _binding!!

    private var id: Int? = null

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ITEM_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleItemBinding.inflate(inflater, container, false)
        viewModel.refreshItem(id!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.genresAnime
        val adapter = ListGenresAdapter()
        recyclerView.adapter = adapter

        viewModel.singleAnime.observe(viewLifecycleOwner) {
            binding.apply {
                titleAnime.text = it.name
                Log.d(TAG, it.kind)
                setKindText(kindAnime, it.kind)
                episodesAnime.text = getString(R.string.episodes_placeholder, it.episodes)
                uploadImage(previewAnime, it.videos[0].previewUrl)
                uploadShapeableImage(animeImage, BASE_URL + it.image.url)
                adapter.submitList(it.genres)
                descriptionAnime.text = it.description.smartTruncate(120)
            }
        }
    }

    private fun setKindText(textView: TextView, kind: String) {
        if(kind == "tv") {
            textView.text = requireContext().resources.getString(R.string.tv_label)
        }
    }

    private fun uploadShapeableImage(imageView: ShapeableImageView, imageUrl: String?) {
        imageView.shapeAppearanceModel = imageView.shapeAppearanceModel.toBuilder()
            .setAllCorners(
                CornerFamily.ROUNDED,
                requireContext().resources.getDimension(R.dimen.single_anime_image_radius)
            )
            .build()
        imageUrl?.let {
            val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
            imageView.load(imageUri)
        }
    }

    private fun uploadImage(imageView: ImageView, imageUrl: String?) {
        imageUrl?.let {
            val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
            imageView.load(imageUri)
        }
    }

    private fun bind() {

    }

    companion object {
        const val ITEM_ID = "itemId"
    }
}