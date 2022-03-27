package com.example.plantshandbook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.databinding.FragmentGalleryBinding
import com.example.plantshandbook.dialogs.SaveImagDialog
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.android.synthetic.main.fragment_gallery.*


class GalleryFragment : BaseFragment() {
    lateinit var binding:FragmentGalleryBinding

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
        binding = FragmentGalleryBinding.inflate(inflater, container, false)

        return binding.root
        //pickImag = PickImage(requireContext(), this, btnSaveImg)

            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPickImage.setOnClickListener {
            (activity as MainActivity).imagePicker()
        }
        btnSaveImgGallery.setOnClickListener{
            SaveImagDialog.showDialog(requireContext(), object : SaveImagDialog.Listener{
                override fun onClick(){
                    (activity as MainActivity).resaveGalleryImg()
                }
            })

        }


        btnEndPickGallery.setOnClickListener {
            (activity as MainActivity).navigate(MainFragment(), MainFragment::class.simpleName.toString())
        }
    }

    companion object {


    }
}