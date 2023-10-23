package com.example.bodymassindex

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.bodymassindex.databinding.ActivityMainBinding
import com.example.bodymassindex.viewModel.BmiViewModel
import android.content.Context

class MainActivity : AppCompatActivity() {

    private val bmiViewModel = BmiViewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = bmiViewModel
        binding.lifecycleOwner = this

        lateinit var weight : String
        lateinit var height : String

        val radioGroup = binding.Sex
        val elements = binding.Sex.childCount

        weight = ""
        height = ""


        binding.Weight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.Weight.text!!.isNotEmpty()){
                    bmiViewModel.setWeight(binding.Weight.text.toString())
                    bmiViewModel.calcBmi()
                }  else {
                    bmiViewModel.resetCalcBmi()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.Height.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(binding.Height.text!!.isNotEmpty()){
                    bmiViewModel.setHeight(binding.Height.text.toString())
                    bmiViewModel.calcBmi()
                } else {
                    bmiViewModel.resetCalcBmi()
                }
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
                    bmiViewModel.setSex(radioButton.text.toString())
                    break
                }
            }
            if (bmiViewModel.weight.value == 0.0 || binding.Weight.text.isEmpty()) {
                bmiViewModel.showToast(this, getString(R.string.ErrorWeight), 30)
            } else if (bmiViewModel.height.value == 0.0 || binding.Height.text.isEmpty()) {
                bmiViewModel.showToast(this, getString(R.string.ErrorHeight), 30)
            } else if (!isActive) {
                bmiViewModel.showToast(this, getString(R.string.ErrorSex), 30)
            } else {
                bmiViewModel.showAlertDialog(
                    this,
                    getString(R.string.DialogTitle , "${bmiViewModel.liveBmi.value}"),
                    getString(R.string.DialogMsg),
                    getString(R.string.DialogButtonPositive),
                    getString(R.string.DialogButtonNegative),
                )
            }

        }

        binding.fab.setOnClickListener {

            if (bmiViewModel.sex.value!!.isEmpty()) {
                bmiViewModel.showToast(this, getString(R.string.ErrorData), 30)
            } else {

                val linkTableItalianMenWomen = "https://www.google.com/search?sca_esv=575778037&sxsrf=AM9HkKmv-fHbFlJYV_gAQqWnP_CBs56tFA:1698065827360&q=le+tabelle+per+il+calcolo+del+bmi+sono+uguali+tra+uomo+e+donna%3F&tbm=isch&source=lnms&sa=X&ved=2ahUKEwiQnceTnIyCAxUqgf0HHdnqAiEQ0pQJegQICRAB&biw=1920&bih=923&dpr=1#imgrc=zkjUA8pt2jwLGM"

                val linkTableEnglishMenWomen = "https://www.google.com/search?q=body+mass+index+&tbm=isch&ved=2ahUKEwiH6euvnIyCAxXGh_0HHUN-AI4Q2-cCegQIABAA&oq=body+mass+index+&gs_lcp=CgNpbWcQAzIFCAAQgAQyBQgAEIAEMgUIABCABDIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjoECCMQJzoHCCMQ6gIQJzoLCAAQgAQQsQMQgwE6CAgAELEDEIMBOggIABCABBCxAzoECAAQAzoKCAAQigUQsQMQQzoHCAAQigUQQ1DdCliTKWCtKmgDcAB4AIABYogBswySAQIxOZgBAKABAaoBC2d3cy13aXotaW1nsAEKwAEB&sclient=img&ei=3m02ZYeCKcaP9u8Pw_yB8Ag&bih=923&biw=1920#imgrc=G-q9svKAv8_qFM"

                if(bmiViewModel.sex.value == "uomo"){
                    val queryMenItalian: Uri = Uri.parse(linkTableItalianMenWomen)
                    intent = Intent(Intent.ACTION_VIEW, queryMenItalian)
                }

            if(bmiViewModel.sex.value == "men"){
                val queryMenEnglish: Uri = Uri.parse(linkTableEnglishMenWomen)
                intent = Intent(Intent.ACTION_VIEW, queryMenEnglish)
            }

                if(bmiViewModel.sex.value == "donna"){
                    val queryWomenItalian: Uri = Uri.parse(linkTableItalianMenWomen)
                    intent = Intent(Intent.ACTION_VIEW, queryWomenItalian)
                }

            if(bmiViewModel.sex.value == "women"){
                val queryWomenEnglish: Uri = Uri.parse(linkTableEnglishMenWomen)
                intent = Intent(Intent.ACTION_VIEW, queryWomenEnglish)
            }

                startActivity(intent)
            }


            }
        }



    }







