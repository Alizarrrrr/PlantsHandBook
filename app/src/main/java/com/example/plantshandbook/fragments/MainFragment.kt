package com.example.plantshandbook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plantshandbook.DataModel
import com.example.plantshandbook.PlantAdapter
import com.example.plantshandbook.R
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : BaseFragment() {
    lateinit var binding: FragmentMainBinding
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
    override fun onClickNew() {

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Toast.makeText(getActivity(), "Frag_main", Toast.LENGTH_SHORT).show()
        setupView()
        btnAddImgCamera.setOnClickListener {
            (activity as MainActivity).navigate(CameraFragment(), CameraFragment::class.simpleName.toString())
            //dataModel.indicatorBtnAddImg.value=true
//            MainActivity.flagStartIn = 1
//            FragmentManager.setFragment(InputFragment.newInstance(), (activity as MainActivity) )


            // Toast.makeText(requireContext(), "Button start INPUT FRAG", Toast.LENGTH_SHORT).show()
            /* dataModel.IndicatorbtnAddImg.observe(viewLifecycleOwner, Observer {set ->
                 true
             })

             */

        }
        btnAddImgGallery.setOnClickListener{
            (activity as MainActivity).navigate(GalleryFragment(), GalleryFragment::class.simpleName.toString())

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

       /* btnAddPlant.setOnClickListener {
            if (index > 4) {
                index = 0
            }
            val plant = Plant(imageIdList[index], plantName[index])
            adapter.addPlant(plant)
            index++



        }*/

    }

}


