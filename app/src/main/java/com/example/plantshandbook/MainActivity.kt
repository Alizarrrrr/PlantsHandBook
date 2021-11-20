package com.example.plantshandbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = PlantAdapter()
    private val imageIdList = listOf(
        R.drawable.plant1,
        R.drawable.plant2,
        R.drawable.plant3,
        R.drawable.plant4,
        R.drawable.plant5
    )
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
        setupView()
    }

    private fun setupView() {


        rcView.layoutManager = GridLayoutManager(this@MainActivity, 3)
        rcView.adapter = adapter

        val plantName = resources.getStringArray(R.array.plant_a)
        adapter.setPlantNames(plantName)

        buttonAdd.setOnClickListener {
            if (index > 4) {
                index = 0
            }
            val plant = Plant(imageIdList[index], plantName[index])
            adapter.addPlant(plant)
            index++

        }
    }

}