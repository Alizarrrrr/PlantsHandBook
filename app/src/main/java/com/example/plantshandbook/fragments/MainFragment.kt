package com.example.plantshandbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plantshandbook.DataModel
import com.example.plantshandbook.Plant
import com.example.plantshandbook.PlantAdapter
import com.example.plantshandbook.R
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {
    private val dataModel: DataModel by activityViewModels()
    private val adapter = PlantAdapter()
    private val imageIdList = listOf(
        R.drawable.plant1,
        R.drawable.plant2,
        R.drawable.plant3,
        R.drawable.plant4,
        R.drawable.plant5
    )
    private var index = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Toast.makeText(getActivity(), "Frag_main", Toast.LENGTH_SHORT).show()
        setupView()
        btnAddImg.setOnClickListener {
            dataModel.indicatorbtnAddImg.value=true
            /* Toast.makeText(requireContext(), "Button start INPUT FRAG", Toast.LENGTH_SHORT).show()
             dataModel.IndicatorbtnAddImg.observe(viewLifecycleOwner, Observer {set ->
                 true
             })

             */

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    //adapter pickImage
    private fun setupView() {

        rcView.layoutManager = GridLayoutManager(requireContext(), 3)//LinearLayoutManager(requireContext())
        rcView.adapter = adapter

        val plantName = resources.getStringArray(R.array.plant_a)
        adapter.setPlantNames(plantName)

        btnAddPlant.setOnClickListener {
            if (index > 4) {
                index = 0
            }
            val plant = Plant(imageIdList[index], plantName[index])
            adapter.addPlant(plant)
            index++

        }

    }

}

