package com.example.dymagram.views.pager_fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.example.dymagram.R


class MediaFragments : Fragment() {

    // Request launcher result
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    // XML Views
    private lateinit var previewView: PreviewView
    private lateinit var pictureCaptureImageButton: ImageButton
    private lateinit var videoCaptureImageButton: ImageButton
    private lateinit var switchCameraImageButton: ImageButton
    private lateinit var flashImageButton: ImageButton

    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var imageCapture: ImageCapture
    private lateinit var videoCapture: VideoCapture<Recorder>
    private lateinit var preview: Preview

    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    // Photo / Video components
    private lateinit var recording: Recording
    private var isRecording = false

    private lateinit var camera: Camera
    private var isFLashEnabled = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestPermissionLauncher = this.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.CAMERA] == true &&
                permissions[Manifest.permission.RECORD_AUDIO] == true) {
                view?.findViewById<PreviewView>(R.id.previewView)?.let {
                    startCamera(it)
                }
            } else {
                Toast.makeText(requireContext(), "Permission denied, change parameters", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_media_fragments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previewView = view.findViewById(R.id.previewView)
        pictureCaptureImageButton = view.findViewById(R.id.picture_capture_imageButton)
        videoCaptureImageButton = view.findViewById(R.id.video_capture_imageButton)
        switchCameraImageButton = view.findViewById(R.id.btnSwitchCamera)
        flashImageButton = view.findViewById(R.id.btnFlash)

        // Check camera permission
        checkAndRequestPermissions()

        // Init Camera
        initCameraX(previewView)

        // Listeners camera buttons
        cameraComponentListening()
    }


    private fun checkAndRequestPermissions() {
        if (allPermissionsGranted()) {
           view?.findViewById<PreviewView>(R.id.previewView)?.let {
               startCamera(it)
           }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
                )
            )
        }
    }

    private fun initCameraX(preview: PreviewView) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindCameraUseCases(preview)
            }, ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun bindCameraUseCases(previewView: PreviewView) {
        preview = Preview.Builder().build().also { 
            it.surfaceProvider = previewView.surfaceProvider
        }
        
        // Conf capture photo
        imageCapture = ImageCapture.Builder().build()
        
        
        cameraProvider.unbindAll()
        
        cameraProvider.bindToLifecycle(
            viewLifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
    }

    
    @SuppressLint("ClickableViewAccessibility")
    private fun cameraComponentListening() {
        pictureCaptureImageButton.setOnClickListener { takePhoto() }
        switchCameraImageButton.setOnClickListener { switchCamera() }
        videoCaptureImageButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Quand l'utilisateur appuie sur le bouton, commencer l'enregistrement
                    if (!isRecording) {
                        startRecording()  // Méthode qui démarre l'enregistrement vidéo
                        isRecording = true
                    }
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Quand l'utilisateur relâche le bouton ou annule l'action, arrêter l'enregistrement
                    if (isRecording) {
                        stopRecording()  // Méthode qui arrête l'enregistrement vidéo
                        isRecording = false
                    }
                    true
                }
                else -> false
            }
        }
        flashImageButton.setOnClickListener {
            camera.let {
                isFLashEnabled = !isFLashEnabled
                toggleFlash()
            }
        }
    }

    private fun startCamera(previewView: PreviewView) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            // Configuration preview
            preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK) // Choisir la caméra arrière
                .build()

            // Configuration pour la capture photo
            imageCapture = ImageCapture.Builder().build()

            // Configuration pour la capture vidéo avec Recorder
            val recorder = Recorder.Builder()
                .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
                .build()
            videoCapture = VideoCapture.withOutput(recorder)

            // Détacher les précédentes configurations pour réattacher les nouvelles
            cameraProvider.unbindAll()
            camera = cameraProvider.bindToLifecycle(
                viewLifecycleOwner, cameraSelector, preview, imageCapture, videoCapture
            )

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        // Créer un nom de fichier avec un timestamp
        val name = "IMG_${System.currentTimeMillis()}.jpg"

        // Configuration des informations pour enregistrer dans MediaStore
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Dymagram-Photos")
        }

        // Obtenir une URI pour l'image dans la galerie
        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            requireContext().contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        // Prendre la photo et enregistrer l’image
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = output.savedUri ?: return
                    Toast.makeText(requireContext(), "Photo enregistrée: $savedUri", Toast.LENGTH_SHORT).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(requireContext(), "Erreur lors de la capture: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun startRecording() {
        val name = "VID_${System.currentTimeMillis()}.mp4"

        val contentValues = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
            put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/Dymagram-Videos") // Pour Android 10+ (Scoped Storage)
        }

        // Créer un fichier de sortie pour la vidéo
        val mediaStoreOutput = MediaStoreOutputOptions.Builder(
            requireContext().contentResolver,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        ).setContentValues(contentValues)
            .build()

        // Préparer l'enregistrement
        recording = videoCapture.output
            .prepareRecording(requireContext(), mediaStoreOutput)
            .start(ContextCompat.getMainExecutor(requireContext())) { recordEvent ->
                when (recordEvent) {
                    is VideoRecordEvent.Finalize -> {
                        val savedUri = recordEvent.outputResults.outputUri
                        Toast.makeText(requireContext(), "Vidéo enregistrée : $savedUri", Toast.LENGTH_SHORT).show()

                        // Rafraîchir la galerie
                        MediaScannerConnection.scanFile(
                            requireContext(),
                            arrayOf(savedUri.path),
                            null
                        ) { _, uri ->
                            //Log.d("VideoCapture", "Video scanned: $uri")
                        }
                    }
                    else -> {
                        // Gérer les autres événements si nécessaire
                    }
                }
            }
    }

    private fun stopRecording() {
        // Arrêter l'enregistrement si en cours
        recording.stop()
        Toast.makeText(requireContext(), "Enregistrement arrêté", Toast.LENGTH_SHORT).show()
    }

    private fun switchCamera() {
        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

    private fun toggleFlash() {
        camera.cameraControl.enableTorch(isFLashEnabled)
    }


    // Permissions
    private fun allPermissionsGranted() = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    ).all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
}