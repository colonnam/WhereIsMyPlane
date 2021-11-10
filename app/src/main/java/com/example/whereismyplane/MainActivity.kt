package com.example.whereismyplane

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var date1:TextView?=null
    var date2:TextView?=null
    var search:Button?=null

    var cal = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        date1= findViewById(R.id.textView2)
        date2= findViewById(R.id.textView4)

        //!!!!!faudrait mettre les date anterieures de base
        date1!!.text=Utils.dateToString(Calendar.getInstance().time)
        date2!!.text=Utils.dateToString(Calendar.getInstance().time)

        search=findViewById(R.id.search)



        val dateSetListener1 = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate1InView()
            }
        }

                // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
                date1!!.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(view: View) {
                        DatePickerDialog(this@MainActivity,
                            android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                            dateSetListener1,
                            // set DatePickerDialog to point to today's date when it loads up
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH)).show()
                    }


                })

        val dateSetListener2 = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate2InView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        date2!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@MainActivity,
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    dateSetListener2,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }


        })

        var names=ArrayList<String>()
        Utils.generateAirportList().forEach {
            names.add(it.getFormattedName())
        }
        val spinner: Spinner = findViewById(R.id.spinner)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            names,
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        search!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                

// Instantiate the RequestQueue.
                val queue = Volley.newRequestQueue(this@MainActivity)
                var url="https://opensky-network.org/api/flights/departure?"
                val textView = findViewById<TextView>(R.id.textView)
                val airportIcao=Utils.generateAirportList()[spinner.selectedItemPosition].icao

                val myFormat = "MM/dd/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                val time1 = sdf.parse(date1!!.text.toString()).time/1000
                val time2=sdf.parse(date2!!.text.toString()).time/1000
                url+="airport=${airportIcao}&begin=${time1}&end=${time2}"




// Request a string response from the provided URL.
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    Response.Listener<String> { response ->
                        // Display the first 500 characters of the response string.
                        textView.text = "Response is: ${response.substring(0, 500)}"
                    },
                    Response.ErrorListener {

                        textView.text = it.message
                        Log.i("Request", "${it.message} and ${it.cause}")})

// Add the request to the RequestQueue.
                queue.add(stringRequest)


            }
        })


    }
    private fun updateDate1InView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date1!!.text = sdf.format(cal.getTime())
    }
    private fun updateDate2InView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date2!!.text = sdf.format(cal.getTime())
    }

}