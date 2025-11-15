package com.example.courses.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courses.R
import com.example.courses.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _b: FragmentHomeBinding? = null
    private val b get() = _b!!
    private val vm: HomeViewModel by viewModels()

    private val adapter = CourseAdapter(onLike = { course ->
        vm.onToggleFavorite(course)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentHomeBinding.bind(view)

        // Set up RecyclerView
        b.recyclerCourses.layoutManager = LinearLayoutManager(requireContext())
        b.recyclerCourses.adapter = adapter


        b.btnSort.setOnClickListener {
            vm.onSortClick()
        }


        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collectLatest { state ->
                adapter.submitList(state.courses)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
