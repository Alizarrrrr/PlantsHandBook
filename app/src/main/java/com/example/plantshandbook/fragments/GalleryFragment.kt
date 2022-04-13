package com.example.plantshandbook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.activities.MainActivity.Companion.bitmapCheck
import com.example.plantshandbook.activities.MainActivity.Companion.imageUri
import com.example.plantshandbook.databinding.FragmentGalleryBinding
import com.example.plantshandbook.dialogs.SaveImagDialog
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
            (activity as MainActivity).drawingImage()
        }
        btnSaveImgGallery.setOnClickListener{
            SaveImagDialog.showDialog(requireContext(), object : SaveImagDialog.Listener{
                override fun onClick(){
                    (activity as MainActivity).saveGalleryImg()
                }
            })
            //(activity as MainActivity).drawingImage()
        }

        btnEndPickGallery.setOnClickListener {
            (activity as MainActivity).navigate(RedactFragment(), RedactFragment::class.simpleName.toString())
            bitmapCheck = false
            imageUri = null

        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as MainActivity).navigate(
                    RedactFragment(),
                    RedactFragment::class.simpleName.toString()
                )
                imageUri = null
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    companion object {


    }
}