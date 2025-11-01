package net.baudovalentim.motivation.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.baudovalentim.motivation.R
import net.baudovalentim.motivation.databinding.ActivityMainBinding
import net.baudovalentim.motivation.preferences.SecurityPreferences
import net.baudovalentim.motivation.repositories.PhraseRepository
import net.baudovalentim.motivation.utils.MotivationConstants

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var securityPreferences: SecurityPreferences
    private val phraseRepository = PhraseRepository()
    private var filter: Int = MotivationConstants.PHRASE.ALL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        securityPreferences = SecurityPreferences(applicationContext)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()
        getUserName()
        handleFilter(R.id.image_all)
        refreshPhrase()
    }

    override fun onClick(v: View?) {
      val listId = listOf(
          R.id.image_all,
          R.id.image_happy,
          R.id.image_sunny
      )

      if (v?.id == R.id.button_new_phrase) {
          refreshPhrase()
      } else if (v?.id in listId) {
          handleFilter(v?.id)
      }
    }

    private fun handleFilter(id: Int?) {

        binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.black))
        binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.black))
        binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.black))

        when (id) {
            R.id.image_all -> {
                filter = MotivationConstants.PHRASE.ALL
                binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }

            R.id.image_happy -> {
                filter = MotivationConstants.PHRASE.HAPPY
                binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }

            R.id.image_sunny -> {
                filter = MotivationConstants.PHRASE.SUNNY
                binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }
        }

    }

    private fun refreshPhrase() {
        binding.textPhrase.text = phraseRepository.getPhrase(filter)
    }

    private fun getUserName() {
        val name = securityPreferences.getString(MotivationConstants.KEY.PERSON_NAME)
        binding.textName.text = "Olá, ${name.trim()}!"
    }

    private fun setListeners() {
        binding.buttonNewPhrase.setOnClickListener(this)
        binding.imageAll.setOnClickListener(this)
        binding.imageHappy.setOnClickListener(this)
        binding.imageSunny.setOnClickListener(this)
    }
}