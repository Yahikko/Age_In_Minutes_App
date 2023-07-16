package com.example.dobcalc

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val millisInOneDay = 86400000
    private val millisInOneMinute = 60000

    private var tvSelectedDate: TextView? = null
    private var tvInMinutesTillDate: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Невозможно настроить цвет action bar через theme, поэтому только так
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#FF264653"))
        )

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvInMinutesTillDate = findViewById(R.id.tvInMinutesTillDate)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this, { _, selectedYear, selectedMonth, selectedDay ->

                val selectedDate = "$selectedDay.${selectedMonth + 1}.$selectedYear"
                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)

                theDate?.let {
                    val selectedDateInMinutes = theDate.time / millisInOneMinute

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / millisInOneMinute

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        tvInMinutesTillDate?.text = differenceInMinutes.toString()
                    }
                }

            }, year, month, day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - millisInOneDay
        dpd.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miInDays -> {
                Toast.makeText(this, "In Days", Toast.LENGTH_SHORT).show()
                item.isChecked = true
                true
            }

            R.id.miInHours -> {
                Toast.makeText(this, "In Hours", Toast.LENGTH_SHORT).show()
                item.groupId
                item.isChecked = !item.isChecked
                true
            }

            R.id.miInMinutes -> {
                Toast.makeText(this, "In Minutes", Toast.LENGTH_SHORT).show()
                item.isChecked = !item.isChecked
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}