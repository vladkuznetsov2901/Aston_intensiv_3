package com.example.aston_intensiv_3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aston_intensiv_3.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val allContacts = generateContacts()
        val contactAdapter = ContactAdapter()
        binding.contactRecycler.adapter = contactAdapter
        binding.contactRecycler.layoutManager = GridLayoutManager(this, 1)

        contactAdapter.submitList(allContacts)


    }

    fun generateContacts(): List<Contact> {
        val firstNames = listOf(
            "Иван",
            "Мария",
            "Алексей",
            "Ольга",
            "Дмитрий",
            "Елена",
            "Павел",
            "Анна",
            "Максим",
            "Юлия"
        )
        val lastNames = listOf(
            "Иванов",
            "Смирнова",
            "Кузнецов",
            "Попова",
            "Соколов",
            "Лебедева",
            "Морозов",
            "Козлова",
            "Новиков",
            "Петрова"
        )

        val contacts = mutableListOf<Contact>()
        for (i in 1..100) {
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            val phoneNumber = "+7 (${Random.nextInt(100, 999)}) ${Random.nextInt(100, 999)}-${
                Random.nextInt(
                    10,
                    99
                )
            }-${Random.nextInt(10, 99)}"
            contacts.add(
                Contact(
                    id = i,
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber
                )
            )
        }
        return contacts
    }


}