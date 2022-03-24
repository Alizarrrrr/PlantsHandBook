package com.example.plantshandbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.plantshandbook.PickImage
import com.example.plantshandbook.R
import com.example.plantshandbook.databinding.FragmentGalleryBinding
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.android.synthetic.main.fragment_gallery.*


class GalleryFragment : BaseFragment() {
    lateinit var binding:FragmentGalleryBinding
    lateinit var pickImag: PickImage

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
        pickImag=PickImage(requireContext(), this, btnPickImage, imViewGallery)

    /*    btnPickImage.setOnClickListener {
            pickImag=PickImage(requireContext(), this, btnSaveImg)
        } */
    }

    companion object {


    }
}