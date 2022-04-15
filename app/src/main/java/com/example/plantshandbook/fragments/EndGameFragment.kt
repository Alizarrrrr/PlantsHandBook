package com.example.plantshandbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.plantshandbook.EndGameAdapter
import com.example.plantshandbook.ListImageAdapter
import com.example.plantshandbook.R
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.databinding.FragmentEndGameBinding
import com.example.plantshandbook.db.MainViewModel
import com.example.plantshandbook.entities.EndGameItem
import com.example.plantshandbook.entities.ImageItem
import com.example.plantshandbook.fragments.GameFragment.Companion.endGameList
import kotlinx.android.synthetic.main.fragment_end_game.*
import kotlinx.coroutines.launch


class EndGameFragment : BaseFragment() {
    lateinit var binding: FragmentEndGameBinding
    private lateinit var adapter: EndGameAdapter



    override fun onClickNew() {

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEndGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        setAdapter()
        btnExitEndGame.setOnClickListener {
            endGameList = emptyList<EndGameItem>().toMutableList()
            (activity as MainActivity).navigate(
                MainFragment(),
                MainFragment::class.simpleName.toString()
            )
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as MainActivity).navigate(
                    MainFragment(),
                    MainFragment::class.simpleName.toString()
                )
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)

    }
    private fun initRcView() = with(binding) {

        rcViewEndGame.layoutManager = getLayoutManager()
        adapter = EndGameAdapter()
        rcViewEndGame.adapter = adapter
    }
    private fun getLayoutManager(): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun setAdapter() {
        adapter.submitList(endGameList)

    }



companion object {


}
}