package com.example.todonotesapp.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.todonotesapp.BuildConfig
import com.example.todonotesapp.R
import com.example.todonotesapp.utils.AppConstant
import java.io.File
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNotesActivity : AppCompatActivity() {

    // Declaring variables for views
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var submitButton: Button
    lateinit var imageViewAdd: ImageView
    lateinit var imageLocation : File
    var picturePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        // Creating function for binding views from the xml file
        bindViews()

        // Creating function for submit_button click
        clickListener()
    }

    private fun clickListener() {
        submitButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent()
                intent.putExtra(AppConstant.NOTE_TITLE,editTextTitle.text.toString())
                intent.putExtra(AppConstant.DESCRIPTION,editTextDescription.text.toString())
                intent.putExtra(AppConstant.DESCRIPTION, editTextDescription.text.toString() )
                intent.putExtra(AppConstant.IMAGE_PATH, picturePath )
                setResult(AppConstant.ADD_NOTES_CODE,intent)
                finish()
            }
        })

        // Set onClickListener on imageView
        imageViewAdd.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {

                // check for permissions is provided or not
                // permission not provided don't open dialog other than open dialog
                if (checkAndRequestPermission()){

                    // create a method for dialog setup
                    setupDialog()                                    }
            }

        })
    }

    private fun checkAndRequestPermission(): Boolean {

        // Create variables to store the permissions camera and storage
        val permissionCamera = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val listPermissionNeeded = ArrayList<String>()

        // check permission if not granted added to the list
        if (permissionCamera !=PackageManager.PERMISSION_GRANTED){
            listPermissionNeeded.add(android.Manifest.permission.CAMERA)
        }
        if (storagePermission !=PackageManager.PERMISSION_GRANTED){
            listPermissionNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (listPermissionNeeded.isNotEmpty()){
            ActivityCompat.requestPermissions(this,listPermissionNeeded.toTypedArray<String>(),AppConstant.MY_PERMISSION_CODE)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            AppConstant.MY_PERMISSION_CODE->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    setupDialog()
                }
            }
        }
    }

    private fun setupDialog() {
        val view : View = LayoutInflater.from(this@AddNotesActivity)
            .inflate(R.layout.add_image_dialog_layout,null)

        // binding dialog box field
        val textViewCamera = view.findViewById<TextView>(R.id.textViewCamera)
        val textViewGallery = view.findViewById<TextView>(R.id.textViewGallery)

        // Below we create a dialog box
        val dialog : AlertDialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(true)
            .create()

        // set OnCliCkListener on gallery textView to open device gallery
        textViewCamera.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                // Open a Camera first of all we create a intent
                // But we cant pass source or destination we will pass an ACTIONS
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(packageManager) != null){
                    var photoFile : File? = null

                    try {
                        // create a method for create a temporary file in pictures directory
                        photoFile = createImageFile()
                    } catch (e: Exception) {
                    }

                    // Convert temporary file in uri
                    if (photoFile != null){
                        val photoUri : Uri = FileProvider.getUriForFile(this@AddNotesActivity,
                        BuildConfig.APPLICATION_ID+".provider",
                        photoFile)

                        imageLocation = photoFile

                        // And then this file/uri pass to intent
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                        dialog.hide()
                        startActivityForResult(takePictureIntent,AppConstant.REQUEST_CODE_CAMERA)
                    }
                }
            }

        })

        // set OnCliCkListener on gallery textView to open device gallery
        textViewGallery.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {

                // Open a gallery first of all we create a intent
                // But we cant pass source or destination we will pass an ACTIONS
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,AppConstant.REQUEST_CODE_GALLERY)
                dialog.hide()
            }

        })

        dialog.show()
    }

    private fun createImageFile(): File? {

        // first we create a  timeStamp and this use as a file name
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        // Create a file name
        val fileName : String = "JPEG" +timeStamp+"_"
        // Store directly
        val storageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName,"jpg",storageDir)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstant.REQUEST_CODE_CAMERA){

            // save image address in picturePath variable for sending previous activity or save
            // in dataBase
            picturePath = imageLocation.path.toString()

            Log.d("mmmm",picturePath)

            Glide.with(this).load(picturePath).into(imageViewAdd)
        }
        if (requestCode == AppConstant.REQUEST_CODE_GALLERY){

            // get selected image address in below variable
            val selectedImage = data?.data
            // save image address in picturePath variable for sending previous activity or save
            // in dataBase
            picturePath = selectedImage.toString()

            Log.d("mmmm",picturePath)

            Glide.with(this).load(picturePath).into(imageViewAdd)

        }

    }

    private fun bindViews() {
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        submitButton = findViewById(R.id.buttonSubmit)
        imageViewAdd = findViewById(R.id.imageViewAdd)
    }
}