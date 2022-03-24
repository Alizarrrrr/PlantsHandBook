// подключение чтения из файла https://devofandroid.blogspot.com/2018/09/pick-image-from-gallery-android-studio_15.html (Pick an Image from the Gallery – Android Studio - Kotlin)
// камера (простая, без Camera2) https://www.youtube.com/watch?v=DPHkhamDoyc       https://github.com/rpandey1234/CameraIntegration/blob/master/app/src/main/java/edu/stanford/rkpandey/cameraintegration/MainActivity.kt
//https://developer.android.com/guide/fragments/communicate

package com.example.plantshandbook.activities


import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.core.content.FileProvider
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.plantshandbook.DataModel
import com.example.plantshandbook.PickImage
import com.example.plantshandbook.R
import com.example.plantshandbook.databinding.ActivityMainBinding
import com.example.plantshandbook.db.MainViewModel
import com.example.plantshandbook.entities.ImageItem
import com.example.plantshandbook.fragments.MainFragment
import com.example.plantshandbook.utils.TimeManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.android.synthetic.main.fragment_gallery.*
import java.io.*

private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42
private lateinit var photoFile: File

class MainActivity : BaseActivity() {
    private lateinit var pickImag: PickImage
    private val dataModel: DataModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    //val internalStorageDir = getFilesDir();
    var photoFile: File? = null
    val CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE = 102
    val CAMERA_PERMISSION_REQUEST_CODE = 1
    var fileUri: Uri? = null

    var currentFragment: Fragment? = null

    var photoStorageDirPathName: String? = null


//    private val mainViewModel: MainViewModel {
//        MainViewModel.MainViewModelFactory((context?.Activity as MainApp).database)
//    }

    private val mainViewModel: MainViewModel by viewModels{
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)


        // startProgress()


        navigate(MainFragment(), MainFragment::class.simpleName.toString())




    }
     private fun getPhotoFile(fileName: String): File {
           // Use `getExternalFilesDir` on Context to access package-specific directories.
           val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
           return File.createTempFile(fileName, ".jpg", storageDirectory)
       }


    override fun onResume() {
        super.onResume()


    }

    fun startCamera() {
        if (!(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                    || packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT))
        ) {
            Toast.makeText(this, "No Camera device", Toast.LENGTH_LONG).show()
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                fileUri = getOutputMediaFileUri()
                if (fileUri != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri) // set the image file name
                    // start the Photo Capture Intent
                    startActivityForResult(intent, CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE)
                }

            } else {
                requestPermissionForCamera()
            }
        }
    }

    private fun requestPermissionForCamera() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }


    private fun getOutputMediaFileUri(): Uri? {
        val mediaStorageDir: File = getMediaFileDir()
            ?: return null
        // Create a media file name

        photoFile =
            File(mediaStorageDir.absolutePath + File.separator + System.currentTimeMillis() + "PHOTO_TEMP.jpg")
        return FileProvider.getUriForFile(
            this,
            applicationContext.packageName + ".provider",
            photoFile!!
        )
    }

    fun getMediaFileDir(): File? {
        val mediaStorageDir = File(applicationInfo.dataDir, "temp_photo")


        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        return mediaStorageDir
    }

    fun getMediaPhotoDir(): File? {
        val photoStorageDir = File(applicationInfo.dataDir, "photo")
        val namephotoStorageDir = photoStorageDir.absolutePath
        if (!photoStorageDir.exists()) {
            if (!photoStorageDir.mkdirs()) {
                return null
            }
        }
        return photoStorageDir
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PickImage.PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImag.pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PickImage.IMAGE_PICK_CODE) {
            imView.setImageURI(data?.data)
        }


 //       if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
 //               imViewGallery.setImageURI(data?.data)
 //       }
        // https://ichi.pro/ru/ispol-zovanie-kamery-android-kotlin-i-galerea-280949141171262


        if (requestCode == CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            try {

                val bfOptions = BitmapFactory.Options()
                bfOptions.inDither = false
                bfOptions.inPurgeable = true
                bfOptions.inInputShareable = true
                bfOptions.inTempStorage = ByteArray(32 * 1024)
                val fileSize = photoFile!!.length().toInt()
                val dIn = DataInputStream(FileInputStream(photoFile!!))
                val buffer = ByteArray(fileSize)
                while (dIn.read(buffer) != -1) {
                }
                val bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.size, bfOptions)
                val exif = ExifInterface(photoFile!!.absolutePath)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                var rotate = 0
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
                }
                val matrix = Matrix()
                matrix.postRotate(rotate.toFloat())

                val maxHeight = 1000
                val maxWidth = 1000
                val width = bitmap.width
                val height = bitmap.height
                if (height > width) {
                    if (height > maxHeight || width > maxWidth) {
                        val scale = Math.max(
                            maxHeight.toFloat() / height.toFloat(),
                            maxWidth.toFloat() / width.toFloat()
                        )
                        matrix.postScale(scale, scale)
                    }
                } else {
                    if (height > maxWidth || width > maxHeight) {
                        val scale = Math.max(
                            maxWidth.toFloat() / height.toFloat(),
                            maxHeight.toFloat() / width.toFloat()
                        )
                        matrix.postScale(scale, scale)
                    }
                }
                val resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
                try {
                    FileOutputStream(photoFile).use { out ->
                        resizeBitmap.compress(
                            Bitmap.CompressFormat.PNG,
                            100,
                            out
                        ) // bmp is your Bitmap instance
                    }

                    Picasso.get().load(File(photoFile!!.absolutePath)).into(imView)


                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }




    fun navigate(fragment: Fragment?, tag: String) {
        if (currentFragment != null && currentFragment?.tag == tag) return

        val transaction = supportFragmentManager.beginTransaction()
        if (fragment != null) {
            currentFragment = fragment
            transaction.replace(R.id.place_holder_main, fragment, tag)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    fun saveImgAndroidQ(): File? {

        var photoStorageDir: File = getMediaPhotoDir()
            ?: return null
        val timeName =TimeManager.getCurrentTime()+".jpg"
        photoStorageDirPathName = (photoStorageDir.toString()+ File.separator)

        getMediaPhotoDir()

        val originalFileDir = File(photoFile!!.absolutePath).toString()
        //val originalFileName = File(photoFile!!.name).toString()

        val imageSave = ImageItem(
            null,
            enteredName,
            photoStorageDirPathName!!+timeName
        )
        mainViewModel.insertImage(imageSave)

        //copy file in new path with new name
        return File(originalFileDir)
            .copyTo(File(photoStorageDir, timeName), true)

        //https://habr.com/ru/post/645465/
        //https://stackoverflow.com/questions/9292954/how-to-make-a-copy-of-a-file-in-android

    }
    fun saveImgControl(){
        if (photoFile != null){
            saveImgAndroidQ()
        }
        else {
            Toast.makeText(this, "Photos missing", Toast.LENGTH_SHORT).show()

        }
    }



    fun saveImage(){
        val nameImagInside:String = TimeManager.getCurrentTime()

        //MainViewModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)


    }



    companion object{
        var flagStartIn = 0
        var enteredName = ""
    }





}