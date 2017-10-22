package com.masbie.travelohealth;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.masbie.travelohealth.object.ValidasiKamar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

public class PesanKamar extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener
{
    private static final int REQUEST_IMAGE_CAPTURE = 0x01;
    Button tanggal;
    Button upload;
    ImageView gambar;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Bitmap imageBitmap = null;
    File gambarUpload;
    private String photoTaken;
    private DatabaseReference mDatabase;
    private SweetAlertDialog proses_kirim;
    private String tanggalpilihan = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_kamar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String getGambar = getIntent().getExtras().getString("gambar", null);
        final String getKamar = getIntent().getExtras().getString("id_kamar", null);
        final String getKelas = getIntent().getExtras().getString("kelas", null);
        final long getHarga = getIntent().getExtras().getLong("harga", 0);

        if(getGambar == null || getKamar == null || getKelas == null || getHarga == 0)
        {
            finish();
        }
        else
        {
            toolbar.setTitle("Pesan Kamar " + getKelas);
            ImageView toolbar_layout = findViewById(R.id.image_toolbar);
            StorageReference storageRef = storage.getReference().child("images/" + getGambar);
            Glide.with(this)
                 .using(new FirebaseImageLoader())
                 .load(storageRef)
                 .into(toolbar_layout);

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(tanggalpilihan == null)
                    {
                        new SweetAlertDialog(PesanKamar.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Pilih Tanggal Pemesanan!")
                                .setConfirmText("OK")
                                .showCancelButton(false)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog)
                                    {
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    }
                    else
                    {
                        new SweetAlertDialog(PesanKamar.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Apa anda yakin?")
                                .setContentText("Anda akan memesan kamar pada tanggal " + tanggalpilihan + "!")
                                .setConfirmText("Ya, saya yakin!")
                                .setCancelText("Batal")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener()
                                {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog)
                                    {
                                        sDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                {
                                    @Override
                                    public void onClick(final SweetAlertDialog sDialog)
                                    {
                                        proses_kirim = new SweetAlertDialog(PesanKamar.this, SweetAlertDialog.PROGRESS_TYPE);
                                        proses_kirim.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                        proses_kirim.setTitleText("Loading");
                                        proses_kirim.setCancelable(false);
                                        proses_kirim.show();
                                        Calendar calendar = Calendar.getInstance();
                                        StorageReference storageRef = storage.getReference();
                                        final long time = calendar.getTimeInMillis();
                                        StorageReference ImagesRef = storageRef.child("surat-rujukan/SR - " + time + ".jpg");

                                        InputStream stream = null;
                                        if(gambarUpload != null)
                                        {
                                            try
                                            {
                                                stream = new FileInputStream(gambarUpload);
                                            }
                                            catch(FileNotFoundException e)
                                            {
                                                e.printStackTrace();
                                            }
                                            UploadTask uploadTask = ImagesRef.putStream(stream);
                                            uploadTask.addOnFailureListener(new OnFailureListener()
                                            {
                                                @Override
                                                public void onFailure(@NonNull Exception exception)
                                                {
                                                    // Handle unsuccessful uploads
                                                    exception.printStackTrace();
                                                }
                                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                                            {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                                {
                                                    mDatabase.child("pesankamar").child(String.valueOf(time)).setValue(new ValidasiKamar(getKamar, tanggalpilihan, getKelas, getHarga, "SR - " + time + ".jpg"));
                                                    mDatabase.child("kamar").child(getKamar).child("terisi").addListenerForSingleValueEvent(new ValueEventListener()
                                                    {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot)
                                                        {
                                                            long jumlahterisi = dataSnapshot.getValue(long.class);
                                                            mDatabase.child("kamar").child(getKamar).child("terisi").setValue(jumlahterisi + 1);
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError)
                                                        {

                                                        }
                                                    });
                                                    proses_kirim.dismissWithAnimation();
                                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                                    sDialog
                                                            .setTitleText("Berhasil!")
                                                            .setContentText("Tunggu konfirmasi dari operator!")
                                                            .setConfirmText("OK")
                                                            .showCancelButton(false)
                                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                                            {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sweetAlertDialog)
                                                                {
                                                                    Intent intent = new Intent(PesanKamar.this, Home.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            })
                                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                                }
                                            });
                                        }
                                        else
                                        {
                                            proses_kirim.dismissWithAnimation();
                                            sDialog
                                                    .setTitleText("Upload Surat Rujukan!")
                                                    .setContentText("Mohon upload surat rujukan dengan melakukan foto pada surat!")
                                                    .setConfirmText("OK")
                                                    .showCancelButton(false)
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                                    {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog)
                                                        {
                                                            sDialog.dismissWithAnimation();
                                                        }
                                                    })
                                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                        }
                                    }
                                })
                                .show();
                    }
                }
            });
        }

        tanggal = findViewById(R.id.tanggal);
        upload = findViewById(R.id.upload);
        gambar = findViewById(R.id.gambar);

        tanggal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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

        upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                photoTaken = null;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if(takePictureIntent.resolveActivity(getPackageManager()) != null)
                {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try
                    {
                        photoFile = createImagePath();
                    }
                    catch(IOException ex)
                    {
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    photoTaken = photoFile.getName().toString();
                    if(photoFile != null)
                    {
                        Uri uri = FileProvider.getUriForFile(getApplicationContext(), "com.masbie.travelohealth.fileprovider", photoFile);
                        List<ResolveInfo> resolvedIntentActivities = getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                        for(ResolveInfo resolvedIntentInfo : resolvedIntentActivities)
                        {
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

    public File createImagePath() throws IOException
    {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent)
    {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            if(this.photoTaken != null)
            {
                gambarUpload = new File(getFilesDir(), photoTaken);
                try
                {
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(gambarUpload));
                    if(b != null)
                    {

                        gambar.setImageBitmap(b);
                        //                        this.onSubmitRoomButtonClickedCommit(imageFile);
                    }
                }
                catch(FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        String date = String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year;
        tanggal.setText("Tanggal Pesan: " + date);
        tanggalpilihan = date;
    }
}
