package com.example.facedetector

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var start_btn = findViewById<Button>(R.id.start_btn)

        start_btn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, 123)
            } else {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == RESULT_OK) {
            val extras = data?.extras
            val bitmap = extras?.get("data") as? Bitmap
            if (bitmap != null) {
                detectFace(bitmap)
            }
        }
    }

    fun detectFace(bitmap: Bitmap) {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(options)
        val image = InputImage.fromBitmap(bitmap, 0)

        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isEmpty()) {
                    Toast.makeText(this@MainActivity, "No face detected!", Toast.LENGTH_SHORT)
                        .show()
                    return@addOnSuccessListener
                }
                val builder = StringBuilder()
                var i = 1
                for (face in faces) {
                    val smileProb =
                        face.smilingProbability?.times(100)?.let { "%.1f".format(it) } ?: "N/A"
                    val leftEyeProb =
                        face.leftEyeOpenProbability?.times(100)?.let { "%.1f".format(it) } ?: "N/A"
                    val rightEyeProb =
                        face.rightEyeOpenProbability?.times(100)?.let { "%.1f".format(it) } ?: "N/A"

                    builder.append("Face #$i\n")
                    builder.append("Smile: $smileProb%\n")
                    builder.append("Left Eye Open: $leftEyeProb%\n")
                    builder.append("Right Eye Open: $rightEyeProb%\n")
                    builder.append("\n")

                    i++
                }
                androidx.appcompat.app.AlertDialog.Builder(this@MainActivity)
                    .setTitle("Face Data Summary")
                    .setMessage(builder.toString())
                    .setPositiveButton("OK", null)
                    .show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@MainActivity, "Face detection failed.", Toast.LENGTH_LONG)
                    .show()
            }
    }


}

