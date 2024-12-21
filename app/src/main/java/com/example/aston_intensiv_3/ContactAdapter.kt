package com.example.aston_intensiv_3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aston_intensiv_3.databinding.ContactItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ContactAdapter() :
    ListAdapter<Contact, ContactAdapter.ContactViewHolder>(DiffUtilCallback()) {
    var isMultiSelectMode = false
    val selectedContacts = mutableSetOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ContactItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {

            if (isMultiSelectMode) {
                checkBox.visibility = View.VISIBLE
                checkBox.isChecked = selectedContacts.contains(item.id)
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedContacts.add(item.id)
                    } else {
                        selectedContacts.remove(item.id)
                    }
                }
            } else {
                checkBox.visibility = View.GONE
                checkBox.setOnCheckedChangeListener(null)
            }
            contactId.text = item.id.toString()
            contactName.text = item.firstName
            contactSurname.text = item.lastName
            contactNumber.text = item.phoneNumber

            root.setOnClickListener {
                val dialog = EditContactDialog()
                dialog.arguments = Bundle().apply {
                    putParcelable("CONTACT", item)
                }
                dialog.show(
                    (holder.itemView.context as AppCompatActivity).supportFragmentManager,
                    "EditContactDialog"
                )
            }


        }


    }


    class ContactViewHolder(val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    class DiffUtilCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem == newItem
    }

}