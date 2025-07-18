# 🤖 Face Detection Summary App

This is a small curiosity-driven Android project built to explore and understand how face detection works using Google's ML Kit.

It captures an image using the device camera and detects faces to display:
- Smile probability 😊
- Left eye open percentage 👁️
- Right eye open percentage 👁️

## 🧠 Why This Project?

I was curious about:
- How face detection works on-device
- How probabilities like "smiling" or "eye open" are measured
- How to work with Google's ML Kit and camera integration

So I built this mini project to get a hands-on feel of it.

## 📸 Features
- Takes photo from camera
- Detects all faces in the image
- Shows:
  - Face number
  - Smile % (with confidence)
  - Eye open probabilities

## 🛠️ Built With
- Kotlin
- Android SDK (Camera Intent)
- ML Kit (Face Detection API)

## 📦 ML Kit Details
- Uses **FaceDetectorOptions** for:
  - Performance mode: Accurate
  - Landmark detection
  - Smile and eye probability classification

---

## 🙋‍♂️ Just a Fun Project
This isn’t a production-level face scanner.  
It was just a small side experiment to satisfy my curiosity about ML-based face detection on Android.

Feel free to fork, play, and learn with it too.
