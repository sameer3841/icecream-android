package com.saunakpatel.icecream

// File : MainActivity.kt

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val costScoop1 = 3.5
    private val costScoop2 = 5.0

    private val costConeCup    = 0.0
    private val costConePlain  = 0.5
    private val costConeWaffle = 1.0

    private val qtyMax = 7

    private lateinit var chkScope2: CheckBox
    private lateinit var rbtngCone: RadioGroup
    private lateinit var seekbQty: SeekBar
    private lateinit var quantityVal: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val flavors = findViewById<Spinner>(R.id.flavors)
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            flavors.adapter = adapter
        }

        chkScope2 = findViewById<CheckBox>(R.id.doubleScoop)
        rbtngCone = findViewById<RadioGroup>(R.id.coneGroup)
        seekbQty = findViewById<SeekBar>(R.id.quantitySlider)
        quantityVal = findViewById<TextView>(R.id.quantityVal)

        chkScope2.setOnCheckedChangeListener { _, _ -> update() }
        rbtngCone.setOnCheckedChangeListener { _, _ -> update() }

        seekbQty.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                quantityVal.text = progress.toString()
                update()
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        update()
        quantityVal.text = seekbQty.progress.toString()


    }
    fun update() {
        var totalCost = 0.0

        totalCost += if (chkScope2.isChecked) costScoop2 else costScoop1

        when (rbtngCone.checkedRadioButtonId) {
            R.id.paperCup -> totalCost += costConeCup
            R.id.plainCone -> totalCost += costConePlain
            R.id.chocolateWaffle -> totalCost += costConeWaffle
        }

        val quantity = seekbQty.progress
        totalCost *= quantity

        if (quantity == 0) {
            totalCost = if (chkScope2.isChecked) costScoop2 else costScoop1
            totalCost += costConeCup // Default cone
        }

        val costTextView: TextView = findViewById(R.id.amount)
        costTextView.text = String.format("$ %.2f", totalCost)
    }
}
