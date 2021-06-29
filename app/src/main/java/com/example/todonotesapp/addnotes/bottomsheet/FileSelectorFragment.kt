package com.example.todonotesapp.addnotes.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.todonotesapp.R
import com.example.todonotesapp.addnotes.OnOptionClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FileSelectorFragment : BottomSheetDialogFragment() {

    companion object{
        const val tag = "FileSelectorFragment"
        fun newInstance() :FileSelectorFragment = FileSelectorFragment()
    }

    private lateinit var textViewCamera: TextView
    private lateinit var textViewGallery: TextView
    private lateinit var onOptionClickListener:OnOptionClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClickListener = context as OnOptionClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_image_dialog_layout,container,false)

        bindViews(view)
        setupClickListener()

        return view
    }

    private fun bindViews(view: View) {

        textViewCamera = view.findViewById(R.id.textViewCamera)
        textViewGallery = view.findViewById(R.id.textViewGallery)
    }

    private fun setupClickListener() {

        textViewCamera.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClickListener.onCameraClick()
                dismiss()
            }

        })

        textViewGallery.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClickListener.onGalleryClick()
                dismiss()
            }

        })
    }
}