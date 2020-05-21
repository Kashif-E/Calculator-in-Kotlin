package com.infinity.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private  val result by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.result) }
    private val newNumber by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.newNumber ) }
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) {findViewById<TextView>(R.id.operation  )}

    //variable to hold the operands and type of collection
    private  var operand1: Double? = null
    private  var operand2:Double= 0.0
    private  var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /////////////////////////////////////////////////////
        //              Data Input Buttons                 //
        /////////////////////////////////////////////////////
        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)

        /////////////////////////////////////////////////////
        //              Operations Button                  //
        /////////////////////////////////////////////////////

        val buttonEquals: Button  = findViewById(R.id.buttonEquals)
        val buttonDivide: Button  = findViewById(R.id.buttonDivide)
        val buttonMinus: Button  = findViewById(R.id.buttonMinus)
        val buttonPlus: Button  = findViewById(R.id.buttonPlus)
        val buttonMultiply: Button  = findViewById(R.id.buttonMultiply)

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

        /////////////////////////////////////////////////////
        //      clicklistener for operations               //
        /////////////////////////////////////////////////////

        val operationListener = View.OnClickListener { v ->
            val operationClicked = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value,operationClicked);
            }
            catch (e:NumberFormatException)
            {
             newNumber.setText("")
            }


            pendingOperation= operationClicked;
            displayOperation.text= pendingOperation 
        }

        buttonDivide.setOnClickListener(operationListener)
        buttonMultiply.setOnClickListener(operationListener)
        buttonPlus.setOnClickListener(operationListener)
        buttonEquals.setOnClickListener(operationListener)
        buttonMinus.setOnClickListener(operationListener)
    }

    private fun performOperation(value: Double, operationClicked: String) {
        if (operand1 == null)
        {
            operand1= value
        }
        else
        {
            operand2= value
            if(pendingOperation== "=")
            {
                pendingOperation= operationClicked
            }

            when(pendingOperation)
            {
                "=" -> operand1= operand2


                "/" -> if (operand2 == 0.0)
                {
                    operand1= Double.NaN
                }
                else
                {
                    operand1=operand1!! / operand2
                }

                "*" -> operand1 = operand1!! * operand2

                "-" -> operand1 = operand1!! - operand2

                "+" -> operand1 = operand1!! + operand2
            }

        }

        result.setText(operand1.toString())
        newNumber.setText("")
    }
}
