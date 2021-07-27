package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.newsapp.Communicator
import com.example.newsapp.R
import kotlinx.android.synthetic.main.fragment_a.view.*

class FragmentA : Fragment() {

private  lateinit var communicatior: Communicator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_a, container, false)

        communicatior = activity as Communicator

        view.sendBtn.setOnClickListener{
           communicatior.passDataCom(view.messageInput.text.toString()+ "monkeys ")
        }



        return  view
    }

}