package com.skillbox.skillbox.contentprovider.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.contentprovider.classes.Contact

class ContactListAdapter(onContactClick: (Contact) -> Unit) :
    AsyncListDifferDelegationAdapter<Contact>(ContactDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ContactListAdapterDelegate(onContactClick))
    }

    class ContactDiffUtilCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }
}