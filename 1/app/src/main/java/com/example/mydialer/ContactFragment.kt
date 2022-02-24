package com.example.mydialer

import android.os.Bundle
import android.service.autofill.Validators.or
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL

private const val contactURL = "https://drive.google.com/u/0/uc?id=1-KO-9GA3NzSgIc1dkAsNm8Dqw0fuPxcR&export=download"

private var contactsJson = mutableListOf<Contact>()
private val dataAdapter by lazy { Adapter() }

class ContactFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRecyclerView(view)
        getConactList()
        Log.d("conatctJson", contactsJson.toString())
    }


    private fun getConactList()
    {

            val connection = URL(contactURL).openConnection() as HttpURLConnection
            val jsonData = connection.inputStream.bufferedReader().readText()

            contactsJson =
                Gson().fromJson(jsonData, Array<Contact>::class.java).toList() as ArrayList<Contact>


            activity?.runOnUiThread() {
                dataAdapter.submitList(contactsJson)
            }


    }

    private fun getRecyclerView(v: View)
    {
        v.findViewById<RecyclerView>(R.id.rView).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = dataAdapter
        }
    }

    fun search(s: CharSequence)
    {
        var results = mutableListOf<Contact>()

        if (s.isNotBlank()) {
            contactsJson.forEach { contact ->
                if (contact.name.contains(s) or contact.phone.contains(s) or contact.type.contains(s))
                    results.add(contact)
            }
        }
        else
            results.addAll(contactsJson)
        dataAdapter.submitList(results)
    }

}


