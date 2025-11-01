package net.baudovalentim.motivation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.baudovalentim.motivation.R
import net.baudovalentim.motivation.databinding.ActivityUserBinding
import net.baudovalentim.motivation.preferences.SecurityPreferences
import net.baudovalentim.motivation.utils.MotivationConstants

class UserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserBinding
    private lateinit var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        securityPreferences = SecurityPreferences(applicationContext)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()
        verifyUserName()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.button_save) {
            handleSave()
        }
    }

    private fun verifyUserName() {
        val name = securityPreferences.getString(MotivationConstants.KEY.PERSON_NAME)

        if (name.isNotEmpty()) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleSave() {
        val name = binding.editName.text.toString()

        if (name.isEmpty()) {
            Toast.makeText(applicationContext, "Informe o seu nome", Toast.LENGTH_SHORT).show()
        } else {
            securityPreferences.storeString(MotivationConstants.KEY.PERSON_NAME, name)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setListeners() {
        binding.buttonSave.setOnClickListener(this)
    }
}