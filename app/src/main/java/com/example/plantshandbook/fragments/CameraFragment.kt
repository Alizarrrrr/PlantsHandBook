package com.example.plantshandbook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.plantshandbook.R
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.databinding.FragmentCameraBinding

import com.example.plantshandbook.dialogs.SaveImagDialog
import kotlinx.android.synthetic.main.fragment_camera.*


class CameraFragment : BaseFragment() {
    lateinit var binding: FragmentCameraBinding

    private val imageIdList = listOf(
        R.drawable.plant1,
        R.drawable.plant2,
        R.drawable.plant3,
        R.drawable.plant4,
        R.drawable.plant5
    )

    override fun onClickNew() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCameraBinding.inflate(inflater, container, false)

        return binding.root
        //pickImag = PickImage(requireContext(), this, btnSaveImg, )


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).plotImgCamera()

        btnImgCamera.setOnClickListener {
            (activity as MainActivity).startCamera()
        }
        btnSaveImg.setOnClickListener {
            SaveImagDialog.showDialog(requireContext(), object : SaveImagDialog.Listener {
                override fun onClick() {
                    (activity as MainActivity).saveCameraShot()
                }
            })
        }
        btnEndPick.setOnClickListener {
            (activity as MainActivity).navigate(
                RedactFragment(),
                RedactFragment::class.simpleName.toString()
            )
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as MainActivity).navigate(
                    RedactFragment(),
                    RedactFragment::class.simpleName.toString()
                )
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)


    }

    companion object {
        @JvmStatic
        fun newInstance() = CameraFragment()
    }

    //btnImgPick


}
