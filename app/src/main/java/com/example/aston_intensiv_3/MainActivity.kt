package com.example.aston_intensiv_3

import ItemTouchHelperCallback
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.aston_intensiv_3.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity(), AddContactDialog.OnContactAddedListener,
    EditContactDialog.OnContactUpdatedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactAdapter: ContactAdapter
    private val viewModel: MainViewModel by viewModels()

    companion object {
        const val IS_DELETE = "isMultiSelectMode"
        const val DELETE_BTN_VISIBLE = "deleteButton"
        const val ADD_BTN_VISIBLE = "addButton"
        const val CANCEL_BTN_VISIBLE = "cancelButton"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactAdapter = ContactAdapter { updatedList ->
            viewModel.contacts.clear()
            viewModel.contacts.addAll(updatedList)
        }

        binding.contactRecycler.adapter = contactAdapter
        binding.contactRecycler.layoutManager = GridLayoutManager(this, 1)
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(contactAdapter))
        itemTouchHelper.attachToRecyclerView(binding.contactRecycler)
        if (viewModel.contacts.isEmpty()) {
            viewModel.contacts.addAll(generateContacts())
        }

        contactAdapter.submitList(viewModel.contacts)

        binding.addButton.setOnClickListener {
            val dialog = AddContactDialog()
            dialog.show(supportFragmentManager, "AddContactDialog")
        }

        binding.deleteBtn.setOnClickListener {
            contactAdapter.isMultiSelectMode = true
            contactAdapter.notifyDataSetChanged()
            binding.addButton.visibility = View.GONE
            binding.deleteButton.visibility = View.VISIBLE
            binding.cancelButton.visibility = View.VISIBLE

        }

        binding.deleteButton.setOnClickListener {
            val selectedIds = contactAdapter.selectedContacts
            viewModel.contacts.removeAll { it.id in selectedIds }
            contactAdapter.submitList(viewModel.contacts.toList())
            contactAdapter.isMultiSelectMode = false
            contactAdapter.selectedContacts.clear()
            contactAdapter.notifyDataSetChanged()
            binding.addButton.visibility = View.VISIBLE
            binding.deleteButton.visibility = View.GONE
            binding.cancelButton.visibility = View.GONE
        }

        binding.cancelButton.setOnClickListener {
            contactAdapter.isMultiSelectMode = false
            contactAdapter.selectedContacts.clear()
            contactAdapter.notifyDataSetChanged()
            binding.addButton.visibility = View.VISIBLE
            binding.deleteButton.visibility = View.GONE
            binding.cancelButton.visibility = View.GONE

        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_DELETE, contactAdapter.isMultiSelectMode)
        outState.putInt(DELETE_BTN_VISIBLE, binding.deleteButton.visibility)
        outState.putInt(ADD_BTN_VISIBLE, binding.addButton.visibility)
        outState.putInt(CANCEL_BTN_VISIBLE, binding.cancelButton.visibility)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        contactAdapter.isMultiSelectMode = savedInstanceState.getBoolean(IS_DELETE)
        binding.addButton.visibility = savedInstanceState.getInt(ADD_BTN_VISIBLE)
        binding.deleteButton.visibility = savedInstanceState.getInt(DELETE_BTN_VISIBLE)
        binding.cancelButton.visibility = savedInstanceState.getInt(CANCEL_BTN_VISIBLE)
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