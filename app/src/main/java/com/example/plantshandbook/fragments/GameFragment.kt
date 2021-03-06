package com.example.plantshandbook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.plantshandbook.R
import com.example.plantshandbook.activities.MainActivity
import com.example.plantshandbook.databinding.FragmentGameBinding
import com.example.plantshandbook.db.MainViewModel
import com.example.plantshandbook.dialogs.CloseGameDialog
import com.example.plantshandbook.dialogs.GamePassDialog

import com.example.plantshandbook.entities.EndGameItem
import com.example.plantshandbook.entities.ImageItem
import com.example.plantshandbook.entities.StatItem
import com.example.plantshandbook.fragments.MainFragment.Companion.gameMode
import com.example.plantshandbook.utils.Base64CoderDecoder
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameFragment : BaseFragment() {
    lateinit var binding: FragmentGameBinding
    private lateinit var mainViewModel: MainViewModel
    var listImage: List<ImageItem> = listOf()
    var statIt: List<StatItem> = listOf()
    var itemSize: Int = 0
    var randomValues: Int = 9999999
    private var correctAnswerList = IntArray(1000)
    var currentAnswerList = IntArray(1000)
    var gameIteration: Int = 0
    var imageContainer: String = ""
    var imageNameList = Array(4) { "" }
    private var randomValuesImageList = arrayListOf<Int>()
    private var clickTextView: Int = 0
    var currentSelection: Int = 9999999
    var currentTextView: Int = 99
    var gameResult = BooleanArray(1000)



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
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        observer()

        setMarkupGameMode()
        setProgressBar()
//        gameAct()


        tvVar1.setOnClickListener {
            setImButtonClickable()
            currentTextView = 0
            currentSelection = randomValuesImageList[0]
            if (currentTextView != 99) {
                setDefaultTextView()
            }
            tvVar1.setBackgroundResource(R.drawable.text_view_style_choice)
        }
        tvVar2.setOnClickListener {
            setImButtonClickable()
            currentTextView = 1
            currentSelection = randomValuesImageList[1]
            if (currentTextView != 99) {
                setDefaultTextView()
            }
            tvVar2.setBackgroundResource(R.drawable.text_view_style_choice)
        }
        tvVar3.setOnClickListener {
            setImButtonClickable()
            currentTextView = 2
            currentSelection = randomValuesImageList[2]
            if (currentTextView != 99) {
                setDefaultTextView()
            }
            tvVar3.setBackgroundResource(R.drawable.text_view_style_choice)
        }
        tvVar4.setOnClickListener {
            setImButtonClickable()
            currentTextView = 3
            currentSelection = randomValuesImageList[3]
            if (currentTextView != 99) {
                setDefaultTextView()
            }
            tvVar4.setBackgroundResource(R.drawable.text_view_style_choice)
        }

        imCheck.setOnClickListener {
            if (clickTextView == 1) {
                imCheck.setImageResource(R.drawable.ic_next)
                clickTextView = 2
                setBackTextCheck()
                gameStatPictureSave()

            } else if (clickTextView == 2) {

                gameIteration += 1
                randomValuesImageList = arrayListOf<Int>()
                imCheck.setImageResource(R.drawable.ic_check)
                observer()
                setDefaultTextView()
                defaultValueVariables()
                setImButtonNoClickable()
                setTextViewClickable()
                gameAct()
                setContent()
                setProgressBar()
                gameStatSave(statIt[0])
                gameEnd()
            }
        }

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


    /*  private fun sizeImage(){
          sizeArrayImage=  listImage.size
          Toast.makeText(context, sizeArrayImage, Toast.LENGTH_SHORT).show()
      }*/
    private fun gameAct() {

        if (itemSize != 0) {
            //increase the iteration number of the game


            //generating a random set of numbers from a list of pictures
//              List(4) { Random.nextInt(1, itemSize) }

            var randomNumber = 0
            while (randomValuesImageList.size < 4) {
                randomNumber = Random.nextInt(0, itemSize)

                if (!randomValuesImageList.contains(randomNumber)) {
                    randomValuesImageList.add(randomNumber)
                }

            }

            //choosing the correct answer from the list
            randomValues = Random.nextInt(0, 3)
            correctAnswerList[gameIteration] = randomValuesImageList[randomValues]
            //copy image
            imageContainer = listImage[correctAnswerList[gameIteration]].img


            //copy title
            for (i in 0..3) {
                imageNameList[i] = listImage[randomValuesImageList[i]].title
            }


        } else {
            Toast.makeText(context, "itemSize is 0", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setContent() {
        imViewGame.setImageBitmap(Base64CoderDecoder.decoder(imageContainer))
        tvVar1.text = imageNameList[0]
        tvVar2.text = imageNameList[1]
        tvVar3.text = imageNameList[2]
        tvVar4.text = imageNameList[3]
    }


    private fun observer() {
        lifecycleScope.launch {
            listImage = mainViewModel.getAllImageList()
            statIt = mainViewModel.getAllStatList()

            sizeIm()
            if (itemSize > 3) {
                gameAct()
                setVisibleAll()
                setContent()
            } else {
                GamePassDialog.showDialog(requireContext(), object : GamePassDialog.Listener {
                    override fun onClickSwitchRedact() {
                        (activity as MainActivity).navigate(
                            RedactFragment(),
                            RedactFragment::class.simpleName.toString()
                        )
                    }

                    override fun onClickSwitchMain() {
                        (activity as MainActivity).navigate(
                            MainFragment(),
                            MainFragment::class.simpleName.toString()
                        )
                    }
                })
            }
        }
    }

    private fun sizeIm() {
        itemSize = listImage.size
    }

    private fun setBackTextCheck() {
        if (currentSelection == correctAnswerList[gameIteration]) {
            gameResult[gameIteration] = true
            when (currentTextView) {
                0 -> {
                    tvVar1.setBackgroundResource(R.drawable.text_view_style_check_true)
                }
                1 -> {
                    tvVar2.setBackgroundResource(R.drawable.text_view_style_check_true)
                }
                2 -> {
                    tvVar3.setBackgroundResource(R.drawable.text_view_style_check_true)
                }
                3 -> {
                    tvVar4.setBackgroundResource(R.drawable.text_view_style_check_true)
                }
            }
        } else {
            gameResult[gameIteration] = false
            when (currentTextView) {
                0 -> {
                    tvVar1.setBackgroundResource(R.drawable.text_view_style_check_false)
                }
                1 -> {
                    tvVar2.setBackgroundResource(R.drawable.text_view_style_check_false)
                }
                2 -> {
                    tvVar3.setBackgroundResource(R.drawable.text_view_style_check_false)
                }
                3 -> {
                    tvVar4.setBackgroundResource(R.drawable.text_view_style_check_false)
                }
            }
            when (randomValues) {
                0 -> {
                    tvVar1.setBackgroundResource(R.drawable.text_view_style_check_answer)
                }
                1 -> {
                    tvVar2.setBackgroundResource(R.drawable.text_view_style_check_answer)
                }
                2 -> {
                    tvVar3.setBackgroundResource(R.drawable.text_view_style_check_answer)
                }
                3 -> {
                    tvVar4.setBackgroundResource(R.drawable.text_view_style_check_answer)
                }
            }
        }
        tvVar1.isFocusable = false
        tvVar2.isFocusable = false
        tvVar3.isFocusable = false
        tvVar4.isFocusable = false
        tvVar1.isClickable = false
        tvVar2.isClickable = false
        tvVar3.isClickable = false
        tvVar4.isClickable = false

        if (gameMode == 1) {
            endGameList.add(
                EndGameItem(
                    gameIteration,
                    gameResult[gameIteration],
                    listImage[currentSelection].title,
                    listImage[correctAnswerList[gameIteration]].title,
                    imageContainer
                )
            )
        }

        currentAnswerList[gameIteration] = currentSelection
    }

    private fun setDefaultTextView() {
        tvVar1.setBackgroundResource(R.drawable.selector_back_text_view)
        tvVar2.setBackgroundResource(R.drawable.selector_back_text_view)
        tvVar3.setBackgroundResource(R.drawable.selector_back_text_view)
        tvVar4.setBackgroundResource(R.drawable.selector_back_text_view)
    }

    private fun setTextViewClickable() {
        tvVar1.isFocusable = true
        tvVar2.isFocusable = true
        tvVar3.isFocusable = true
        tvVar4.isFocusable = true
        tvVar1.isClickable = true
        tvVar2.isClickable = true
        tvVar3.isClickable = true
        tvVar4.isClickable = true
    }

    private fun defaultValueVariables() {
        currentTextView = 99
    }

    private fun setImButtonClickable() {
        imCheck.isFocusable = true
        imCheck.isClickable = true
        clickTextView = 1
    }

    private fun setImButtonNoClickable() {
        imCheck.isFocusable = false
        imCheck.isClickable = false
        clickTextView = 0
    }

    private fun setVisibleAll() {
        imViewGame.visibility = View.VISIBLE
        btnClose.visibility = View.VISIBLE
        // progressBar.visibility = View.VISIBLE
        tvVar1.visibility = View.VISIBLE
        tvVar2.visibility = View.VISIBLE
        tvVar3.visibility = View.VISIBLE
        tvVar4.visibility = View.VISIBLE
        imCheck.visibility = View.VISIBLE
    }

    private fun setMarkupGameMode() {
        when (gameMode) {
            0 -> {
                progressBar.visibility = View.INVISIBLE
                tvProgressBar.visibility = View.INVISIBLE
            }
            1 -> {
                progressBar.visibility = View.VISIBLE
                tvProgressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun setProgressBar() {
        if (gameMode == 1) {
            progressBar.max = 10
            progressBar.progress = gameIteration
            tvProgressBar.text = getString(R.string.progress_bar_text, gameIteration)
        }
    }

    private fun gameEnd() {
        if (gameMode == 1 && gameIteration == 10) {


            (activity as MainActivity).navigate(
                EndGameFragment(),
                EndGameFragment::class.simpleName.toString()
            )
        }
    }

    private fun gameStatSave(item: StatItem){
        when (gameMode) {
            0 -> {
                if (gameIteration ==1){
                    val statIteration = statIt[0].game_iteration_free + 1
                    mainViewModel.updateStat(item.copy(game_iteration_free = statIteration))
                }
            }
            1 -> {
                if (gameIteration ==10){
                    val statIteration = statIt[0].game_iteration_10g + 1
                    mainViewModel.updateStat(item.copy(game_iteration_10g = statIteration))
                }
            }
        }
    }

    private fun gameStatPictureSave(){
        when (gameMode) {
            0 -> {
                val statIterationAll = listImage[correctAnswerList[gameIteration]].all_count_free + 1

                if (currentSelection == correctAnswerList[gameIteration]){
                    val statIterationTrue = listImage[correctAnswerList[gameIteration]].true_count_free + 1
                    mainViewModel.updateImage(listImage[correctAnswerList[gameIteration]].copy(true_count_free= statIterationTrue, all_count_free= statIterationAll))
                }
                else{
                    mainViewModel.updateImage(listImage[correctAnswerList[gameIteration]].copy(all_count_free= statIterationAll))
                }
            }
            1 -> {
                val statIterationAll = listImage[correctAnswerList[gameIteration]].all_count_10g + 1

                if (currentSelection == correctAnswerList[gameIteration]){
                    val statIterationTrue = listImage[correctAnswerList[gameIteration]].true_count_10g + 1
                    mainViewModel.updateImage(listImage[correctAnswerList[gameIteration]].copy(true_count_10g= statIterationTrue, all_count_10g= statIterationAll))
                }
                else{
                    mainViewModel.updateImage(listImage[correctAnswerList[gameIteration]].copy(all_count_10g= statIterationAll))
                }
            }
        }

    }

    companion object {
        //var endGameList: List<EndGameItem> = listOf()
        //var endGameList: MutableList<EndGameItem> = mutableListOf()
        var endGameList = emptyList<EndGameItem>().toMutableList()

    }


}
