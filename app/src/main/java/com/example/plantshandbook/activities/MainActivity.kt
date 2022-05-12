// подключение чтения из файла https://devofandroid.blogspot.com/2018/09/pick-image-from-gallery-android-studio_15.html (Pick an Image from the Gallery – Android Studio - Kotlin)
// камера (простая, без Camera2) https://www.youtube.com/watch?v=DPHkhamDoyc       https://github.com/rpandey1234/CameraIntegration/blob/master/app/src/main/java/edu/stanford/rkpandey/cameraintegration/MainActivity.kt
//https://developer.android.com/guide/fragments/communicate

package com.example.plantshandbook.activities


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.plantshandbook.R
import com.example.plantshandbook.databinding.ActivityMainBinding
import com.example.plantshandbook.db.MainViewModel
import com.example.plantshandbook.entities.ImageItem
import com.example.plantshandbook.entities.StatItem
import com.example.plantshandbook.fragments.MainFragment
import com.example.plantshandbook.utils.Base64CoderDecoder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.*

private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42
private lateinit var photoFile: File


class MainActivity : BaseActivity() {

    // private val dataModel: DataModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var items = emptyList<ImageItem>()
    var itemSize: Int = 0
    private val maxLengthImage = 1000

    //val internalStorageDir = getFilesDir();
    var photoFile: File? = null
    val CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE = 102
    val CAMERA_PERMISSION_REQUEST_CODE = 1
    var fileUri: Uri? = null
    private lateinit var bitmap: Bitmap
    private lateinit var mainViewModel: MainViewModel
    var statList: List<StatItem> = listOf()


    var currentFragment: Fragment? = null
    private var statListSize: Int? = null

    var photoStorageDirPathName: String? = null


//    private val mainViewModel: MainViewModel {
//        MainViewModel.MainViewModelFactory((context?.Activity as MainApp).database)
//    }

    /*private val mainViewModel: MainViewModel by viewModels{
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        observer()
        startStatItem()


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

    fun imagePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            } else {
                chooseImageGallery();
            }
        } else {
            chooseImageGallery();
        }
    }

    private fun chooseImageGallery() {
        //  val intent = Intent(Intent.ACTION_PICK)
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        //  intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)


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
            /* PERMISSION_CODE -> {
                 if (grantResults.size > 0 && grantResults[0] ==
                     PackageManager.PERMISSION_GRANTED
                 ) {
                     //permission from popup granted
                     pickImag.pickImageFromGallery()
                 } else {
                     //permission from popup denied
                     Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                 }
             }*/
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == Activity.RESULT_OK && requestCode == PickImage.IMAGE_PICK_CODE) {
             imView.setImageURI(data?.data)
         }*/
        if (requestCode == IMAGE_CHOOSE && resultCode == Activity.RESULT_OK) {

            imageUri = data?.data


            if (Build.VERSION.SDK_INT >= 29) {
                val source = ImageDecoder.createSource(
                    applicationContext.contentResolver,
                    imageUri!!
                )
                try {
                    bitmap = ImageDecoder.decodeBitmap(source)
                    bitmapCheck = true
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(
                        applicationContext.contentResolver,
                        imageUri
                    )
                    bitmapCheck = true
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            bitmap = resizeBitmap(bitmap, maxLengthImage)
            drawingImage()

        }



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
                bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.size, bfOptions)
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
            bitmap = resizeBitmap(bitmap, maxLengthImage)
        }
    }

    fun plotImgCamera() {
        if (photoFile != null) {
            Picasso.get().load(File(photoFile!!.absolutePath)).into(imView)
        }
    }

    fun saveCameraShot() {
        if (photoFile != null) {
            //val imageSave = Base64CoderDecoder.encoderFile(photoFile!!)?.let {
            val imageSave = Base64CoderDecoder.encoder(bitmap)?.let {
                ImageItem(
                    null,
                    enteredName,
                    it,
                    0,
                    0,
                    0,
                    0
                )
            }
            if (imageSave != null) {
                mainViewModel.insertImage(imageSave)
                Toast.makeText(this, "Save successfully", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Photos missing", Toast.LENGTH_SHORT).show()

        }
    }


    fun saveGalleryImg() {
        if (bitmapCheck) {
            val imageSave = Base64CoderDecoder.encoder(bitmap)?.let {
                ImageItem(
                    null,
                    enteredName,
                    it,
                    0,
                    0,
                    0,
                    0
                )
            }
            if (imageSave != null) {
                mainViewModel.insertImage(imageSave)
                Toast.makeText(this, "Save successfully", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Photos missing", Toast.LENGTH_SHORT).show()

        }
    }

    private fun startStatItem() {
        lifecycleScope.launch {
            statList = mainViewModel.getAllStatList()
            statListSize = statList.size
            if (statListSize == 0) {
                mainViewModel.insertStat(
                    StatItem(
                        null,
                        0,
                        0
                    )
                )
            }
        }
    }

    fun drawingImage() {
        lifecycleScope.launch {
            delay(100L)
            if (imageUri != null) {
                imViewGallery.setImageURI(imageUri)
//                imageUri = null
            }

        }

    }


    fun navigate(fragment: Fragment?, tag: String) {
        if (currentFragment != null && currentFragment?.tag == tag) return

        val transaction = supportFragmentManager.beginTransaction()
        if (fragment != null) {

            currentFragment = fragment
            transaction.replace(R.id.place_holder_main, fragment, tag)
            transaction.disallowAddToBackStack()
            transaction.commit()

        }
    }


    private fun observer() {
        mainViewModel.allImage.observe(this, Observer {
            itemSize = items.size
        })
    }


    fun stopApp() {
        finishAndRemoveTask()
        System.exit(0)
    }

    fun imgView(path: String, imgView: ImageView) {
        Picasso.get().load(path).into(imgView)
    }

    private fun resizeBitmap(source: Bitmap, maxLength: Int): Bitmap {
        try {
            if (source.height >= source.width) {
                if (source.height <= maxLength) {
                    return source
                }
                val aspectRatio: Float = source.width.toFloat() / source.height.toFloat()
                val targetWidth = (aspectRatio * maxLength).toInt()
                return Bitmap.createScaledBitmap(source, targetWidth, maxLength, false)
            } else {
                if (source.width <= maxLength) {
                    return source
                }
                val aspectRatio: Float = source.height.toFloat() / source.width.toFloat()
                val targetHeight = (aspectRatio * maxLength).toInt()
                return Bitmap.createScaledBitmap(source, maxLength, targetHeight, false)
            }
        } catch (e: Exception) {
            return source
        }
    }


    companion object {
        var flagStartIn = 0
        var enteredName = ""
        private val IMAGE_CHOOSE = 1000
        private val PERMISSION_CODE = 1001
        var bitmapCheck = false
        var imageUri: Uri? = null


    }
}

//override fun onBackPressed() {}


/*
//work code
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
    }*/

/* fun resaveGalleryImg(){
        var photoStorageDir: File? = getMediaPhotoDir()
            //?: return null
        val timeName =TimeManager.getCurrentTime()+".jpg"
        photoStorageDirPathName = (photoStorageDir.toString()+ File.separator)
        photoFile =
            File(photoStorageDirPathName!!+timeName )

        val imageSave = ImageItem(
            null,
            enteredName,
            photoStorageDirPathName!!+timeName
        )
        mainViewModel.insertImage(imageSave)
        return try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(photoFile)

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the output stream
            stream.flush()

            // Close the output stream
            stream.close()
            Toast.makeText(this, "Image saved successful.", Toast.LENGTH_SHORT).show()


        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
                //Toast.makeText(this, "Error to save image.", Toast.LENGTH_SHORT).show()

        } as Unit

    }*/