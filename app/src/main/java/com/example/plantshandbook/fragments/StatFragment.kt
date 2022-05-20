package com.example.plantshandbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.plantshandbook.R
import com.example.plantshandbook.StatAdapter
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.databinding.FragmentStatBinding
import com.example.plantshandbook.db.MainViewModel
import com.example.plantshandbook.entities.StatItem
import kotlinx.android.synthetic.main.fragment_stat.*
import kotlinx.coroutines.launch


class StatFragment : Fragment() {
    lateinit var binding:FragmentStatBinding
    private lateinit var adapter: StatAdapter
    private lateinit var mainViewModel: MainViewModel
    var statIt: List<StatItem> = listOf()
    var selectMode = 99



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        observer()
        initRcView()

        val spinner: Spinner = spinnerStat

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_stat,
            R.layout.spinner
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                selectMode = position
                selectStatGameMode()


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }


        btnCloseStat.setOnClickListener {
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

        rcViewStat.layoutManager = getLayoutManager()
        adapter = StatAdapter()
        rcViewStat.adapter = adapter
    }
    private fun getLayoutManager(): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
    }
    private fun observer() {
        mainViewModel.allImage.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        lifecycleScope.launch {
            statIt = mainViewModel.getAllStatList()
        }

    }
    private fun selectStatGameMode(){
        var count: Int? = null
        when (selectMode){
            0 ->{
                count = statIt[0].game_iteration_free
            }
            1 ->{
                count = statIt[0].game_iteration_10g
            }
            2 ->{
                count = statIt[0].game_iteration_free+statIt[0].game_iteration_10g
            }

        }
        tvStatCount.text = count.toString()
    }




}