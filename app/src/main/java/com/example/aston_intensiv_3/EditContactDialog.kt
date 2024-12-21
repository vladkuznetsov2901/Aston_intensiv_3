package com.example.aston_intensiv_3

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.aston_intensiv_3.databinding.DialogAddContactBinding

class EditContactDialog : DialogFragment() {
    interface OnContactUpdatedListener {
        fun onContactUpdated(contact: Contact)
    }

    private lateinit var binding: DialogAddContactBinding
    private var contact: Contact? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        binding = DialogAddContactBinding.inflate(layoutInflater)
        builder.setView(binding.root)

        contact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("CONTACT", Contact::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("CONTACT")
        }
        contact?.let {
            binding.firstNameInput.setText(it.firstName)
            binding.lastNameInput.setText(it.lastName)
            binding.phoneNumberInput.setText(it.phoneNumber)
        }

        binding.saveButton.setOnClickListener {
            val updatedContact = contact?.copy(
                firstName = binding.firstNameInput.text.toString(),
                lastName = binding.lastNameInput.text.toString(),
                phoneNumber = binding.phoneNumberInput.text.toString()
            )
            (activity as? OnContactUpdatedListener)?.onContactUpdated(updatedContact!!)
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        return builder.create()
    }
}
