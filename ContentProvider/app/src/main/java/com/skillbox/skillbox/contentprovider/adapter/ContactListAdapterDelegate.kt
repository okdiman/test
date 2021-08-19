package com.skillbox.skillbox.contentprovider.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.contentprovider.R
import com.skillbox.skillbox.contentprovider.classes.Contact
import com.skillbox.skillbox.contentprovider.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.contact_item.*

class ContactListAdapterDelegate(private val onContactClick: (Contact) -> Unit) :
    AbsListItemAdapterDelegate<Contact, Contact, ContactListAdapterDelegate.Holder>() {
//    имплементируем методы AbsListItemAdapterDelegate
    override fun isForViewType(item: Contact, items: MutableList<Contact>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.contact_item), onContactClick)
    }

    override fun onBindViewHolder(item: Contact, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
//    создаем ViewHolder для работы с нашими элементами списка (заполнение и клик лисенер)
    class Holder(
        override val containerView: View,
        onContactClick: (Contact) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private var currentContact: Contact? = null
//    инициализруем клик лисенер
        init {
            containerView.setOnClickListener { currentContact?.let(onContactClick) }
        }
//    баиндим данные в элемент списка
        fun bind(contact: Contact) {
            currentContact = contact
            contactName.text = contact.name
        }
    }
}