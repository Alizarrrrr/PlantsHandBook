package com.example.plantshandbook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.plantshandbook.PickImage
import com.example.plantshandbook.PlantAdapter
import com.example.plantshandbook.R
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.dialogs.SaveImagDialog
import kotlinx.android.synthetic.main.fragment_input.*
import kotlinx.android.synthetic.main.save_image_dialog.*


class InputFragment : BaseFragment() {

    lateinit var pickImag: PickImage
    private val adapter = PlantAdapter()
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

        return inflater.inflate(R.layout.fragment_input, container, false)
        pickImag = PickImage(requireContext(), this, btnSaveImg)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnImgCamera.setOnClickListener {
            (activity as MainActivity).startCamera()
        }
        btnSaveImg.setOnClickListener {
            SaveImagDialog.showDialog(requireContext(), object : SaveImagDialog.Listener{
                override fun onClick(){
                    val nameObjectPhoto = edNameObjectPhoto.text.toString()
                    (activity as MainActivity).saveImgAndroidQ()



                }
            })
        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = InputFragment()
    }

    //btnImgPick


}
