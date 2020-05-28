package com.example.weighonplanets

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //private var weightInput: String = ""
    val POUND_CONSTANT = 2.2045
    val KG_CONSTANT = 0.45359237
    val MARS_CONSTANT = .38
    val JUPITER_CONSTANT = 2.34
    val VENUS_CONSTANT = .91

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null)
            result_text.text = savedInstanceState.getString("result")
        else
            result_text.text = resources.getText(R.string.weight_result_default)

        CB_Jupiter.setOnClickListener(this)
        CB_Mars.setOnClickListener(this)
        CB_Venus.setOnClickListener(this)
        Glide.with(this).load(R.drawable.maxresdefault).into(app_imageView);

//        calculate_button.setOnClickListener {
//            weightInput = weight_editText.text.toString()
//            val poundWeight = ConvertWeight(weightInput.toDouble(), true) * MARS_CONSTANT
//            val kgWeight = ConvertWeight(poundWeight, false)
//            result_text.text =
//                resources.getText(R.string.weight_result).toString()
//                    .plus(" ${kgWeight.format(2)}")
//        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("result", result_text.text.toString())  // It save small piece of data into a bundle.
    }

    private fun convertWeight(weight: Double, toPound: Boolean): Double {
        return if (toPound)
            weight * POUND_CONSTANT
        else
            weight * KG_CONSTANT
    }

    fun Double.format(digits: Int) = "%.${digits}f".format(this)

    override fun onClick(v: View?) {
        v as CheckBox
        val weightInfoKG = weight_editText.text.toString()
        val isChecked = v.isChecked
        if (TextUtils.isEmpty(weightInfoKG)) {
            resetAllCB()
            Toast.makeText(this, "Field can not be empty!", Toast.LENGTH_SHORT).show()
            result_text.text = resources.getText(R.string.weight_result_default)
            return
        }
        if (isChecked) {
            when (v.id) {
                R.id.CB_Jupiter -> {
                    CB_Venus.isChecked = false
                    CB_Mars.isChecked = false
                    showUserWeight(convertWeight(weightInfoKG.toDouble(), true) * JUPITER_CONSTANT)

                }

                R.id.CB_Mars -> {
                    CB_Jupiter.isChecked = false
                    CB_Venus.isChecked = false
                    showUserWeight(convertWeight(weightInfoKG.toDouble(), true) * MARS_CONSTANT)
                }


                R.id.CB_Venus -> {
                    CB_Mars.isChecked = false
                    CB_Jupiter.isChecked = false
                    showUserWeight(convertWeight(weightInfoKG.toDouble(), true) * VENUS_CONSTANT)
                }

            }
        }
    }

    private fun showUserWeight(weight: Double) {
        val poundToKG = convertWeight(weight, false)
        result_text.text =
            resources.getText(R.string.weight_result).toString().plus(" ${poundToKG.format(2)}")


    }

    private fun resetAllCB() {
        CB_Venus.isChecked = false
        CB_Mars.isChecked = false
        CB_Jupiter.isChecked = false
    }


}
