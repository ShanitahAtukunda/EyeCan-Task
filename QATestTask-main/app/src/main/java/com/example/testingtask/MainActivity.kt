package com.example.testingtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private var doublePressToExitPressOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    override fun onBackPressed() {
//        if (doublePressToExitPressOnce) {
//            super.onBackPressed()
//        } else {
//            this.doublePressToExitPressOnce = true
//            Toast.makeText(
//                this,
//                "Click again to exit",
//                Toast.LENGTH_SHORT
//            ).show()
//            Handler(Looper.getMainLooper()).postDelayed(
//                { doublePressToExitPressOnce = false },
//                5000
//            )
//        }
//    }
}