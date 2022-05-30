package com.example.firebaseimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Uri mImageUri;
    Button button,button2;
    EditText editText;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProgressBar progressBar;
    Malumot malumot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Malumot");
        storageReference= FirebaseStorage.getInstance().getReference().child("Malumot");

        imageView = findViewById(R.id.imageview1);
        button = findViewById(R.id.button1);
        editText=findViewById(R.id.edittext1);
        progressBar=findViewById(R.id.progrssbar1);
        button2=findViewById(R.id.button2);

        imageView.setOnClickListener(view -> {
            openFileChooser();
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimage();
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).centerCrop().fit().into(imageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadimage(){

        if (mImageUri!=null){
            progressBar.setVisibility(View.VISIBLE);
            StorageReference filereference=storageReference.child(System.currentTimeMillis()+"."+getFileExtension(mImageUri));
            filereference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            progressBar.setVisibility(View.INVISIBLE);
                            malumot=new Malumot();
                            malumot.setImagename(editText.getText().toString());
                            malumot.setImagelink(uri.toString());
                            databaseReference.push().setValue(malumot);
                            Toast.makeText(MainActivity.this, "Bazaga qo'shildi", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            });
        }

    }
}