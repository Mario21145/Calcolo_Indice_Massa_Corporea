package com.example.bodymassindex.viewModel

import android.content.Context
import android.provider.Settings.System.getString
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bodymassindex.R
import kotlin.math.roundToInt

class BmiViewModel : ViewModel() {

    private val _liveBmi = MutableLiveData(0.0)
    val liveBmi: LiveData<Double>
        get() = _liveBmi

    //Setter
    private var _weight = MutableLiveData(0.0)
    var weight: LiveData<Double> = _weight
    fun setWeight(weight: String) {
        val weightDouble = weight.toDouble()
        if (weightDouble != _weight.value) {
            _weight.value = weightDouble
        }
    }

    private var _height = MutableLiveData(0.0)
    var height: LiveData<Double> = _height
    fun setHeight(height: String) {
        val heightDouble = height.toDouble()
        if (heightDouble != _height.value) {
            _height.value = heightDouble
        }
    }

    private var _sex = MutableLiveData("")
    var sex: LiveData<String> = _sex
    fun setSex(sex: String) {
        _sex.value = sex
    }

    fun showToast(context: Context, msg: String, duration: Int) {
        Toast.makeText(context, msg, duration).show()
    }

    fun showAlertDialog(
        context: Context,
        title: String,
        msg: String,
        positiveButtonText: String,
        negativeButtonText: String,
    ) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(title)
        builder.setMessage(msg)

        builder.setPositiveButton(positiveButtonText) { dialog, _ ->
            resetCalcBmi()
            resetAll()
            dialog.dismiss()
        }

        builder.setNegativeButton(negativeButtonText) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun calcBmi() {
        val convertedCmToMetres = height.value?.let { it / 100 }
        val Bmi = weight.value!! / (convertedCmToMetres!! * convertedCmToMetres)

        if (Bmi.isFinite()) {
            val BmiRounded = (Bmi * 10).roundToInt() / 10.0
            if (_liveBmi.value != BmiRounded) {
                _liveBmi.value = BmiRounded
            }
        } else {
            _liveBmi.value = weight.value!!
        }
    }

    fun resetCalcBmi() {
        _liveBmi.value = 00.0
    }

    fun resetAll() {
        _weight.value = 0.0
        _height.value = 0.0
        _sex.value = ""
    }

}




