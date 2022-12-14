package mob.dau.ren.shiki.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
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
import mob.dau.ren.shiki.network.Video
import mob.dau.ren.shiki.network.getFullImageUrl
import mob.dau.ren.shiki.repository.ListAnimeRepository
import mob.dau.ren.shiki.util.BASE_URL
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
    private var url: String? = null

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
        val adapter = ListGenreAdapter {
            activity?.onBackPressed()
            viewModel.setGenre(it.id.toString())
            viewModel.fetchAnimeByStatusAndGenre()
        }
        recyclerView.adapter = adapter

        viewModel.singleAnime.observe(viewLifecycleOwner) {
            url = it.url
            var video: Video? = null
            try {
                video = it.videos?.get(0)
            } catch (exception: IndexOutOfBoundsException) {
                Log.d(TAG, exception.message.toString())
            }

            binding.apply {
                titleAnime.text = it.name
                setKindText(kindAnime, it.kind)
                episodesAnime.text = getString(R.string.episodes_placeholder, it.episodes)
                uploadImage(previewAnime, video?.previewUrl)
                previewAnime.setOnClickListener {
                    if (video != null) {
                        openVideo(video.videoUrl)
                    }
                }
                it?.studios?.let { it1 ->
                    uploadImage(studioImage, it1[0]?.getFullImageUrl())
                    it1[0]?.id?.let { it2 -> setStudioListener(studioImage, it2) }
                }
                uploadShapeableImage(animeImage, BASE_URL + it.image.url)
                setScore(scoreAnime, it.score)
                setRating(ratingAnime, it.rating)
                setStatus(statusAnime, it.status)
                adapter.submitList(it.genres)
                it?.description?.let { it1 -> setDescription(descriptionAnime, it1) }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_single_anime, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, BASE_URL.removeSuffix("/") + url)
                if(activity?.packageManager?.resolveActivity(intent, 0) != null) {
                    startActivity(intent)
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setStudioListener(imageView: ImageView, studioId: Int) {
        imageView.setOnClickListener {
            activity?.onBackPressed()
            viewModel.setStudio(studioId.toString())
            viewModel.fetchAnimeByStatusAndGenre()
        }
    }

    private fun setDescription(textView: TextView, description: String) {
        var updated = description.replace("character", "").replace("[", "")
            .replace("]", "").replace("=", "")
            .replace("/", "").replace("anime", "")
        for (i in updated) {
            if (i.isDigit())
                updated = updated.replace("$i", "")
        }
        textView.text = updated
    }

    private fun setStatus(textView: TextView, status: String) {
        when (status) {
            "released" -> {
                textView.setTextColor(requireContext().resources.getColor(R.color.dark_green))
                textView.text = "??????????"
            }
            "ongoing" -> {
                textView.setTextColor(requireContext().resources.getColor(R.color.navy_blue))
                textView.text = "??????????????"
            }
            "anons" -> {
                textView.setTextColor(requireContext().resources.getColor(R.color.dark_red))
                textView.text = "????????????????????????"
            }

        }
    }

    private fun setScore(textView: TextView, score: String) {
        if (score == "0.0") {
            textView.text = "N/A"
            return
        }
        textView.text = score
    }

    private fun setRating(textView: TextView, rating: String) {
        when (rating) {
            "r" -> textView.text = requireContext().resources.getString(R.string.R_label)
            "pg_13" -> textView.text = requireContext().resources.getString(R.string.PG_label)
            "g" -> textView.text = requireContext().resources.getString(R.string.G_label)
            "r_plus" -> textView.text = requireContext().resources.getString(R.string.Rplus_label)
            "pg" -> textView.text = requireContext().resources.getString(R.string.PGch_label)
            "none" -> textView.text = requireContext().resources.getString(R.string.none_label)
        }
    }

    private fun setKindText(textView: TextView, kind: String) {
        when (kind) {
            "tv" -> textView.text = requireContext().resources.getString(R.string.tv_label)
            "movie" -> textView.text = requireContext().resources.getString(R.string.film_label)
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
            val imageUri = it.toUri().buildUpon().scheme("https").build()
            imageView.load(imageUri)
        }

    }

    private fun openVideo(url: String) {
        val packageManager = context?.packageManager ?: return
        val httpUri = Uri.parse(url)
        var intent = Intent(Intent.ACTION_VIEW, httpUri)
        if (intent.resolveActivity(packageManager) == null) {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        }
        startActivity(intent)
    }

    companion object {
        const val ITEM_ID = "itemId"
    }
}