package com.example.plantshandbook.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import com.example.plantshandbook.R
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.activities.MainActivity.Companion.plants
import com.example.plantshandbook.databinding.FragmentMainBinding
import com.example.plantshandbook.db.MainViewModel
import com.example.plantshandbook.dialogs.CloseAppDialog
import com.example.plantshandbook.dialogs.GameModeDialog
import com.example.plantshandbook.dialogs.SaveImagDialog
import com.example.plantshandbook.entities.FirebaseItem
import com.example.plantshandbook.entities.FirebaseListItem
import com.example.plantshandbook.entities.ImageItem
import com.example.plantshandbook.utils.Base64CoderDecoder
import com.example.plantshandbook.utils.FirebaseUtil
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL

class MainFragment : BaseFragment() {
    lateinit var binding: FragmentMainBinding

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

        btnStat.setOnClickListener{
            (activity as MainActivity).navigate(StatFragment(), StatFragment::class.simpleName.toString())
        }


        btnStartGame.setOnClickListener{
            GameModeDialog.showDialog(requireContext(), object : GameModeDialog.Listener{
                override fun onClickSwitchFree() {
                    gameMode = 0
                    (activity as MainActivity).navigate(GameFragment(), GameFragment::class.simpleName.toString())
                }

                override fun onClickSwitchLimit() {
                    gameMode = 1
                    (activity as MainActivity).navigate(GameFragment(), GameFragment::class.simpleName.toString())
                }
            })


        }

        btnRedact.setOnClickListener{
            (activity as MainActivity).navigate(RedactFragment(), RedactFragment::class.simpleName.toString())
        }

        btnStat.setOnClickListener {
            (activity as MainActivity).navigate(StatFragment(), StatFragment::class.simpleName.toString())
        }


        btnCloseApp.setOnClickListener{
            CloseAppDialog.showDialog(requireContext(), object : CloseAppDialog.Listener{
                override fun onClick(){
                    (activity as MainActivity).stopApp()
                }
            })
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                CloseAppDialog.showDialog(requireContext(), object : CloseAppDialog.Listener{
                    override fun onClick(){
                        (activity as MainActivity).stopApp()
                    }
                })
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    companion object {
        var gameMode: Int? = null

    }




}