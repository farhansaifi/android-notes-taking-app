package com.example.todonotesapp.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.todonotesapp.R

class OnBoardingTwoFragment : Fragment() {

    lateinit var buttonDone : Button
    lateinit var buttonBack : Button
    lateinit var onOptionClick: OnOptionClick

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClick = context as OnOptionClick
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?) : View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    private fun bindViews(view: View) {
        buttonDone = view.findViewById(R.id.buttonDone)
        buttonBack = view.findViewById(R.id.buttonBack)
        clickListener()
    }

    private fun clickListener() {
        // set on click listener on back text
        buttonBack.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClick.onOptionBack()
            }

        })
        // set on click listener on next text
        buttonDone.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClick.onOptionDone()
            }

        })
    }
    // create a interface
    interface OnOptionClick {
        fun onOptionBack()
        fun onOptionDone()
    }
}