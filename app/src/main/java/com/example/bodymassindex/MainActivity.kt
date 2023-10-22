package com.example.bodymassindex

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.bodymassindex.databinding.ActivityMainBinding
import com.example.bodymassindex.viewModel.BmiViewModel

class MainActivity : AppCompatActivity() {

    private val bmiViewModel = BmiViewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = bmiViewModel

        val weight = binding.Weight.text.toString()
        val height = binding.Height.text.toString()
        val radioGroup = binding.Sex
        val elements = binding.Sex.childCount



        binding.Weight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                bmiViewModel.setWeight(weight.toDouble())
                bmiViewModel.calcBmi()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.Height.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                bmiViewModel.setHeight(height.toDouble())
                bmiViewModel.calcBmi()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })



        binding.CalculateButton.setOnClickListener{


            var isActive = false
            for (i in 0 until elements) {
                val radioButton = radioGroup.getChildAt(i) as RadioButton

                if (radioButton.isChecked) {
                    isActive = true
                    break
                }
            }

            if(weight.isEmpty() && weight.length <= 2){
                bmiViewModel.showToast(this , getString(R.string.ErrorWeight) , 30)
            } else if (height.isEmpty() && height.length <= 3){
                bmiViewModel.showToast(this , getString(R.string.ErrorHeight) , 30)
            } else if(!isActive){
                bmiViewModel.showToast(this , getString(R.string.ErrorSex) , 30)
            } else {
                bmiViewModel.calcBmi()
            }

        }


    }

//    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
//        return super.onCreateView(name, context, attrs)
//    }





}
