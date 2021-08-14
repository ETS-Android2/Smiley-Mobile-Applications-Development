package com.example.smiley;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;

import java.io.IOException;
import java.util.List;

public class Smiley extends AppCompatActivity {
    ImageView imageView;
    Button chose;
    TextView smiling, entityId, text;
    public static int counter = 0;

    private InputImage imageFromBitmap(Bitmap bitmap) {
        int rotationDegree = 0;
        // [START image_from_bitmap]
        InputImage image = InputImage.fromBitmap(bitmap, rotationDegree);
        return image;
        // [END image_from_bitmap]
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void imageFromMediaImage(Image mediaImage, int rotation) {
        // [START image_from_media_image]
        InputImage image = InputImage.fromMediaImage(mediaImage, rotation);
        // [END image_from_media_image]
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smiley);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.smile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.donate:
                        startActivity(new Intent(getApplicationContext(),Donate.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.smile:
                        return true;


                    case R.id.quote:
                        startActivity(new Intent(getApplicationContext(),Quote.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        imageView = findViewById(R.id.imageView);
        chose = findViewById(R.id.button);
        smiling = findViewById(R.id.confidence);
        text = findViewById(R.id.text);
        entityId = findViewById(R.id.entityId);

        chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "chose image"), 121);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 121) {
            imageView.setImageURI(data.getData());

            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                Bitmap mutablaBmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
                InputImage image = imageFromBitmap(mutablaBmp);
                final Canvas canvas = new Canvas(mutablaBmp);

                // [START set_detector_options]
                FaceDetectorOptions options =
                        new FaceDetectorOptions.Builder()
                                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                                .setMinFaceSize(0.15f)
                                .enableTracking()
                                .build();
                // [END set_detector_options]

                // [START get_detector]
                FaceDetector detector = FaceDetection.getClient(options);
                // Or use the default options:
                // FaceDetector detector = FaceDetection.getClient();
                // [END get_detector]

                // [START run_detector]
                Task<List<Face>> result =
                        detector.process(image)
                                .addOnSuccessListener(
                                        new OnSuccessListener<List<Face>>() {
                                            @Override
                                            public void onSuccess(List<Face> faces) {
                                                // Task completed successfully
                                                // [START_EXCLUDE]
                                                // [START get_face_info]
                                                for (Face face : faces) {
                                                    //Face drawing
                                                    Rect bounds = face.getBoundingBox();
                                                    Paint p = new Paint();
                                                    p.setColor(Color.YELLOW);
                                                    p.setStyle(Paint.Style.STROKE);
                                                    canvas.drawRect(bounds,p);

                                                    float rotY = face.getHeadEulerAngleY();  // Head is rotated to the right rotY degrees
                                                    float rotZ = face.getHeadEulerAngleZ();  // Head is tilted sideways rotZ degrees




                                                    // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
                                                    // nose available):
                                                    FaceLandmark leftEar = face.getLandmark(FaceLandmark.LEFT_EAR);
                                                    if (leftEar != null) {
                                                        PointF leftEarPos = leftEar.getPosition();
                                                    }

                                                    // If classification was enabled:
                                                    if (face.getSmilingProbability() != null) {
                                                        float smileProb = face.getSmilingProbability();
                                                        if(smileProb>0.5){
                                                            text.setText(""+smileProb);
                                                            smiling.setText("Smiling");
                                                            counter++;
                                                            entityId.setText("Total smiles: "+counter);
                                                        } else {
                                                            text.setText(""+smileProb);
                                                            smiling.setText("Not smiling");
                                                        }


                                                    }
                                                    if (face.getRightEyeOpenProbability() != null) {
                                                        float rightEyeOpenProb = face.getRightEyeOpenProbability();
                                                    }

                                                    // If face tracking was enabled:
                                                    if (face.getTrackingId() != null) {
                                                        int id = face.getTrackingId();
                                                    }
                                                }
                                                // [END get_face_info]
                                                // [END_EXCLUDE]
                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Task failed with an exception
                                                // ...
                                            }
                                        });

                // [END run_detector]
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}