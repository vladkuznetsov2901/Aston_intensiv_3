package com.example.aston_intensiv_3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aston_intensiv_3.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity(), AddContactDialog.OnContactAddedListener,
    EditContactDialog.OnContactUpdatedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactAdapter: ContactAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contactAdapter = ContactAdapter()
        binding.contactRecycler.adapter = contactAdapter
        binding.contactRecycler.layoutManager = GridLayoutManager(this, 1)

        if (viewModel.contacts.isEmpty()) {
            viewModel.contacts.addAll(generateContacts())
        }

        contactAdapter.submitList(viewModel.contacts)

        binding.addButton.setOnClickListener {
            val dialog = AddContactDialog()
            dialog.show(supportFragmentManager, "AddContactDialog")
        }

    }

    fun generateContacts(): MutableList<Contact> {
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

    override fun onContactAdded(contact: Contact) {
        val itemPosition = viewModel.contacts.size + 1
        val newContact = contact.copy(id = itemPosition)
        viewModel.contacts.add(newContact)
        contactAdapter.submitList(viewModel.contacts.toList())
        contactAdapter.notifyItemChanged(itemPosition)
    }

    override fun onContactUpdated(contact: Contact) {
        val index = viewModel.contacts.indexOfFirst { it.id == contact.id }
        if (index != -1) {
            viewModel.contacts[index] = contact
            contactAdapter.submitList(viewModel.contacts.toList())
            contactAdapter.notifyItemChanged(index)

        }
    }


}