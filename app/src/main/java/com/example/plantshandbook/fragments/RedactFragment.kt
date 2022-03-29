package com.example.plantshandbook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.plantshandbook.DataModel
import com.example.plantshandbook.ListImageAdapter
import com.example.plantshandbook.PlantAdapter
import com.example.plantshandbook.R
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.activities.MainApp

import com.example.plantshandbook.databinding.FragmentRedactBinding
import com.example.plantshandbook.db.MainViewModel
import com.example.plantshandbook.entities.ImageItem
import kotlinx.android.synthetic.main.fragment_redact.*


class RedactFragment : BaseFragment(), ListImageAdapter.Listener {
    lateinit var binding: FragmentRedactBinding
    //private val dataModel: DataModel by activityViewModels()
    private lateinit var adapter: ListImageAdapter


    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRedactBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Toast.makeText(getActivity(), "Frag_main", Toast.LENGTH_SHORT).show()
        initRcView()
        observer()
        btnAddImgCamera.setOnClickListener {
            (activity as MainActivity).navigate(
                CameraFragment(),
                CameraFragment::class.simpleName.toString()
            )
        }
        btnAddImgGallery.setOnClickListener {
            (activity as MainActivity).navigate(
                GalleryFragment(),
                GalleryFragment::class.simpleName.toString()
            )

        }
        btnExit.setOnClickListener {
            (activity as MainActivity).navigate(
                MainFragment(),
                MainFragment::class.simpleName.toString()
            )

        }
    }

    private fun initRcView() = with(binding) {

        rcView.layoutManager = getLayoutManager()
        adapter = ListImageAdapter(this@RedactFragment)
        rcView.adapter = adapter
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun observer() {
        mainViewModel.allImage.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RedactFragment()
    }


    override fun deleteItem(id: Int) {
        mainViewModel.deleteImage(id)
    }

    override fun onClickItem(note: ImageItem) {

    }

}
/*private fun setupView() {

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
*/
