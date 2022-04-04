package com.example.plantshandbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.plantshandbook.R
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.activities.MainApp
import com.example.plantshandbook.databinding.FragmentGameBinding
import com.example.plantshandbook.db.MainViewModel
import com.example.plantshandbook.dialogs.CloseGameDialog
import com.example.plantshandbook.entities.ImageItem
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : BaseFragment() {
    lateinit var binding: FragmentGameBinding
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }
    private lateinit var listImage: List<ImageItem>
    private  var sizeArrayImage:Int = 0

    override fun onClickNew() {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        sizeImage()

        btnClose.setOnClickListener {
            CloseGameDialog.showDialog(requireContext(), object : CloseGameDialog.Listener {
                override fun onClick() {
                    (activity as MainActivity).navigate(
                        MainFragment(),
                        MainFragment::class.simpleName.toString()
                    )
                }
            })
        }

    }


    private fun observer() {
        mainViewModel.allImage.observe(viewLifecycleOwner) {
            listImage=it
        }
    }
    private fun sizeImage(){
        sizeArrayImage=  listImage.size
        Toast.makeText(context, sizeArrayImage, Toast.LENGTH_SHORT).show()
    }


}