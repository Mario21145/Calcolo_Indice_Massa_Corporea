package com.example.bodymassindex.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BmiViewModel : ViewModel() {

    private val _liveBmi = MutableLiveData<Double>(00.0)
    val liveBmi: LiveData<Double>
        get() = _liveBmi

    //Setter and Getter

    private var _weight = MutableLiveData(0.00)
    var weight: LiveData<Double> = _weight
    fun setWeight(weight: Double) {
        if (weight != _weight.value) {
            _weight.value = weight
        }
    }

    private var _height = MutableLiveData(0.00)
    var height: LiveData<Double> = _height
    fun setHeight(height: Double) {
        if (height != _height.value) {
            _height.value = height
        }
    }

    fun showToast(context: Context, msg: String, duration: Int) {
        Toast.makeText(context, msg, duration).show()
    }


    fun calcBmi() {
        val convertedCmToMetres = height.value?.div(100.0f)

        val Bmi = weight.value!! / (convertedCmToMetres!! * convertedCmToMetres)
        _liveBmi.value = Bmi
    }


}




