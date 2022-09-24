package mob.dau.ren.shiki.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import mob.dau.ren.shiki.R
import mob.dau.ren.shiki.ShikiApplication
import mob.dau.ren.shiki.databinding.FragmentListAnimeBinding
import mob.dau.ren.shiki.repository.ListAnimeRepository
import mob.dau.ren.shiki.viewmodels.Factory
import mob.dau.ren.shiki.viewmodels.ListAnimeViewModel

class ListAnimeFragment : Fragment() {
    private val viewModel: ListAnimeViewModel by activityViewModels {
        Factory(
            ListAnimeRepository((activity?.application as ShikiApplication).database)
        )
    }

    private var _binding: FragmentListAnimeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.listAnimeRecyclerView
        val adapter = ListAnimeAdapter(requireContext()) {
            val action = ListAnimeFragmentDirections
                .actionListAnimeFragmentToSingleItemFragment(itemId = it.id, title = it.name)
            binding.root.findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        viewModel.listAnime.observe(viewLifecycleOwner) { listAnime ->
            adapter.submitList(listAnime)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_anime, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_filter_status_anons -> {
                item.isChecked = !item.isChecked
                viewModel.addStatus("anons")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_status_ongoing -> {
                item.isChecked = !item.isChecked
                viewModel.addStatus("ongoing")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_status_released -> {
                item.isChecked = !item.isChecked
                viewModel.addStatus("released")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_action -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("1")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_comedy -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("4")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_adventure -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("2")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_drama -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("8")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_fantasy -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("10")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_slice_of_life -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("36")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_school -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("23")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_detective -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("7")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_mecha -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("18")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_supernatural -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("37")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_sport -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("30")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_horror -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("14")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_thriller -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("41")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_ecchi -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("9")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_dementia -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("5")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_vampires -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("32")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_harem -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("35")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_demons -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("6")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_genre_kids -> {
                item.isChecked = !item.isChecked
                viewModel.addGenre("15")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_studio_toei -> {
                item.isChecked = !item.isChecked
                viewModel.addStudio("15")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_studio_sunrise -> {
                item.isChecked = !item.isChecked
                viewModel.addStudio("14")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_studio_madhouse -> {
                item.isChecked = !item.isChecked
                viewModel.addStudio("11")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_studio_jcstaff -> {
                item.isChecked = !item.isChecked
                viewModel.addStudio("7")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_studio_ig -> {
                item.isChecked = !item.isChecked
                viewModel.addStudio("10")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_studio_tms -> {
                item.isChecked = !item.isChecked
                viewModel.addStudio("73")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_studio_deen -> {
                item.isChecked = !item.isChecked
                viewModel.addStudio("37")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            R.id.action_filter_studio_pierrot -> {
                item.isChecked = !item.isChecked
                viewModel.addStudio("1")
                viewModel.fetchAnimeByStatusAndGenre()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}