package mob.dau.ren.shiki.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mob.dau.ren.shiki.R
import mob.dau.ren.shiki.databinding.FragmentListAnimeBinding

class ListAnimeFragment : Fragment() {
    private var _binding: FragmentListAnimeBinding? = null
    private val binding get() = _binding!!


    // test
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun bind() {

    }
}