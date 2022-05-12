package com.example.plantshandbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.OnBackPressedCallback
import com.example.plantshandbook.R
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.databinding.FragmentStatBinding
import kotlinx.android.synthetic.main.fragment_stat.*


class StatFragment : Fragment() {
    lateinit var binding:FragmentStatBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStatBinding.inflate(inflater, container, false)
        return binding.root





    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = spinnerStat

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_stat,
            R.layout.spinner
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        btnCloseStat.setOnClickListener {
            (activity as MainActivity).navigate(
                MainFragment(),
                MainFragment::class.simpleName.toString()
            )
        }





        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as MainActivity).navigate(
                    MainFragment(),
                    MainFragment::class.simpleName.toString()
                )
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }



}