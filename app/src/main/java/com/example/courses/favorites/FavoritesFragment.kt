package com.example.courses.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courses.R
import com.example.courses.databinding.FragmentFavoritesBinding
import com.example.courses.home.CourseAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _b: FragmentFavoritesBinding? = null
    private val b get() = _b!!
    private val vm: FavoritesViewModel by viewModels()


    private val adapter = CourseAdapter(onLike = { course ->

        vm.onToggleFavorite(course)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentFavoritesBinding.bind(view)


        b.recyclerFav.layoutManager = LinearLayoutManager(requireContext())
        b.recyclerFav.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            vm.favorites.collectLatest { list ->
                adapter.submitList(list)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
