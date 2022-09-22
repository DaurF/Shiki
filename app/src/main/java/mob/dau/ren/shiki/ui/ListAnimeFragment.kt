package mob.dau.ren.shiki.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import mob.dau.ren.shiki.R
import mob.dau.ren.shiki.databinding.FragmentListAnimeBinding
import mob.dau.ren.shiki.viewmodels.ListAnimeViewModel

class ListAnimeFragment : Fragment() {
    private val viewModel: ListAnimeViewModel by activityViewModels()

    private var _binding: FragmentListAnimeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

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
        val adapter = ListAnimeAdapter()
        recyclerView.adapter = adapter

        viewModel.listAnime.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun bind() {

    }
}