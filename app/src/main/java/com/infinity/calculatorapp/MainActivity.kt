package com.infinity.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import  kotlinx.android.synthetic.main.activity_main.*

private const val STATE_PENDING_OPERATION = "Pending Operation"
private const val STATE_OPERAND1 = "Operand1"
private const val STATE_OPERAND_STORED = "Operand1_Stored"

class MainActivity : AppCompatActivity() {


    //variable to hold the operands and type of collection
    private var operand1: Double? = null
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /////////////////////////////////////////////////////
        //      clicklistener for number buttons           //
        /////////////////////////////////////////////////////
        val listener = View.OnClickListener { v ->
            val clickedButton = v as Button
            newNumber.append(clickedButton.text)
        }

        /////////////////////////////////////////////////////
        //     Setting clicklistener on All the buttons    //
        /////////////////////////////////////////////////////

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonNeg.setOnClickListener { view ->
            val value = newNumber.text.toString()
            if (value.isNotEmpty())
            {
                newNumber.setText("")
            }
            else
            {
                try {
                    var doubleValue = value.toDouble()
                    doubleValue *= -1
                    newNumber.setText(doubleValue.toString())
                }
                catch (e : java.lang.NumberFormatException)
                {
                    newNumber.setText("")
                }

            }
        }
        /////////////////////////////////////////////////////
        //      clicklistener for operations               //
        /////////////////////////////////////////////////////

        val operationListener = View.OnClickListener { v ->
            val operationClicked = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, operationClicked)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }


            pendingOperation = operationClicked
            operation.text = pendingOperation
        }

        buttonDivide.setOnClickListener(operationListener)
        buttonMultiply.setOnClickListener(operationListener)
        buttonPlus.setOnClickListener(operationListener)
        buttonEquals.setOnClickListener(operationListener)
        buttonMinus.setOnClickListener(operationListener)
    }

    private fun performOperation(value: Double, operationClicked: String) {
        if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operationClicked
            }

            when (pendingOperation) {
                "=" -> operand1 = value


                "/" -> operand1 = if (value == 0.0) {
                    Double.NaN
                } else {
                    operand1!! / value
                }

                "*" -> operand1 = operand1!! * value

                "-" -> operand1 = operand1!! - value

                "+" -> operand1 = operand1!! + value
            }

        }

        result.setText(operand1.toString())
        newNumber.setText("")
    }
    ////////////////////////////////////////////////////////
    //     Saving values of operand and operation        //
    //     to use them after device rotation in          //
    //      savedInstanceState                           //
    ///////////////////////////////////////////////////////

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1!!)
            outState.putBoolean(STATE_OPERAND1, true)

        }
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.getBoolean(STATE_OPERAND_STORED, false)) {
            operand1 = savedInstanceState.getDouble(STATE_OPERAND1)
        }
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION)!!
        operation.text = pendingOperation
    }
}
