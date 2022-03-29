package com.example.plantshandbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.plantshandbook.R
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*

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
       /*
        btnStartGame.setOnClickListener{

        }


        btnList.setOnClickListener{

        }

        btnStat.setOnClickListener{

        }

        */

        btnRedact.setOnClickListener{
            (activity as MainActivity).navigate(RedactFragment(), RedactFragment::class.simpleName.toString())
        }


        btnCloseApp.setOnClickListener{
            (activity as MainActivity).stopApp()
        }
    }

    companion object {

    }
}