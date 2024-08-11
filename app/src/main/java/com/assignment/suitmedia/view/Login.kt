package com.assignment.suitmedia.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.assignment.suitmedia.R
import com.assignment.suitmedia.databinding.ActivityLoginBinding

class login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        checkPalindrome()
        setupLogin()
    }

    private fun setupLogin() {
        binding.nextButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            if (name.isNotEmpty()) {
                val intent = Intent(this, secondScreen::class.java)
                intent.putExtra("extra_name", name)
                startActivity(intent)
            } else {
                binding.nameEditText.error = getString(R.string.please_enter_your_name)
            }
        }
    }

    private fun checkPalindrome() {
        binding.checkButton.setOnClickListener {
            val inputString = binding.palindromeEditText.text.toString()
            if (inputString.isNotEmpty()) {
                val isPalindrome = palindrome(inputString)
                if (isPalindrome) {
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.palindrome_check))
                        setMessage(getString(R.string.your_input_is_a_palindrome))
                        setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                    }.show()
                } else {
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.palindrome_check))
                        setMessage(getString(R.string.your_input_is_not_a_palindrome))
                        setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                    }.show()
                }
            } else {
                binding.palindromeEditText.error = getString(R.string.please_input_a_text)
            }

        }
    }

    private fun palindrome(inputString: String): Boolean {
        val reversedString = inputString.reversed()
        return inputString == reversedString
    }
}