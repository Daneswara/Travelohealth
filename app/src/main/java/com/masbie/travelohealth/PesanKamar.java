package com.masbie.travelohealth;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PesanKamar extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    Button tanggal, tanggalkeluar;
    Button upload;
    ImageView gambar;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final int REQUEST_IMAGE_CAPTURE = 0x01;
    private String photoTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_kamar);
        setTitle("Pesan Kamar");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(PesanKamar.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Apa anda yakin?")
                        .setContentText("Anda akan memesan kamar pada tanggal " + tanggalpilihan + "!")
                        .setConfirmText("Ya, saya yakin!")
                        .setCancelText("Batal")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(final SweetAlertDialog sDialog) {
                                Calendar calendar = Calendar.getInstance();
                                StorageReference storageRef = storage.getReference();
                                calendar.getTimeInMillis();
                                StorageReference ImagesRef = storageRef.child("surat-rujukan/SR - " + calendar.getTimeInMillis() + ".jpg");

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 300, baos);
                                byte[] data = baos.toByteArray();

                                UploadTask uploadTask = ImagesRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        System.out.println(exception.toString());
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                        sDialog
                                                .setTitleText("Berhasil!")
                                                .setContentText("Tunggu konfirmasi dari operator!")
                                                .setConfirmText("OK")
                                                .showCancelButton(false)
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        Intent intent = new Intent(PesanKamar.this, Home.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                })
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    }
                                });

                            }
                        })
                        .show();
            }
        });

        tanggal = findViewById(R.id.tanggal);
        tanggalkeluar = findViewById(R.id.tanggalkeluar);
        upload = findViewById(R.id.upload);
        gambar = findViewById(R.id.gambar);

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        PesanKamar.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
                dpd.setMinDate(now);
                dpd.setOkText("Pilih");
                dpd.setCancelText("Batal");
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        tanggalkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        PesanKamar.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
                dpd.setMinDate(now);
                dpd.setOkText("Pilih");
                dpd.setOkText("Pilih");
                dpd.setCancelText("Batal");
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoTaken = null;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImagePath();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    photoTaken = photoFile.getName().toString();
                    if (photoFile != null) {
                        Uri uri = FileProvider.getUriForFile(getApplicationContext(), "com.masbie.travelohealth.fileprovider", photoFile);
                        List<ResolveInfo> resolvedIntentActivities = getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                        for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                            String packageName = resolvedIntentInfo.activityInfo.packageName;

                            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        }
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });

    }

    public File createImagePath() throws IOException {
        // Create an image file name
        File storageDir = this.getFilesDir();
        File image = File.createTempFile(
                "validation",  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    Bitmap imageBitmap = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (this.photoTaken != null) {
                File imageFile = new File(getFilesDir(), photoTaken);
                try {
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(imageFile));
                    if (b != null) {

                        gambar.setImageBitmap(b);
//                        this.onSubmitRoomButtonClickedCommit(imageFile);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String tanggalpilihan = "";

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year;
        tanggal.setText("Tanggal: " + date);
        tanggalpilihan = date;
    }
}
