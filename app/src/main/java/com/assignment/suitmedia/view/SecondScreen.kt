package com.assignment.suitmedia.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.assignment.suitmedia.R
import com.assignment.suitmedia.databinding.ActivitySecondScreenBinding

class secondScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnBack.setOnClickListener {
            finish()
        }

        val name = intent.getStringExtra(EXTRA_NAME)
        binding.nameTextView.text = name

        binding.chooseButton.setOnClickListener {
            val intent = Intent(this, thirdScreen::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        updateSelectedName()
    }

    private fun updateSelectedName() {
        val selectedName = sharedPreferences.getString("selected_name", "No name selected")
        binding.selectedNameTextView.text = selectedName
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
    }
}