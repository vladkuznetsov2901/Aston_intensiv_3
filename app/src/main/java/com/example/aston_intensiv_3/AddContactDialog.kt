package com.example.aston_intensiv_3

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.aston_intensiv_3.databinding.DialogAddContactBinding

class AddContactDialog : DialogFragment() {

    private var _binding: DialogAddContactBinding? = null
    private val binding get() = _binding!!

    interface OnContactAddedListener {
        fun onContactAdded(contact: Contact)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val firstName = binding.firstNameInput.text.toString().trim()
            val lastName = binding.lastNameInput.text.toString().trim()
            val phoneNumber = binding.phoneNumberInput.text.toString().trim()

            if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(context, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newContact = Contact(
                id = 0,
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )

            (activity as? OnContactAddedListener)?.onContactAdded(newContact)
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
