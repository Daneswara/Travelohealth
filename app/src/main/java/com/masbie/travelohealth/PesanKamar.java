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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.masbie.travelohealth.dao.external.Dao;
import com.masbie.travelohealth.dao.external.auth.FirebaseDao;
import com.masbie.travelohealth.dao.external.request.RegisterDao;
import com.masbie.travelohealth.dao.internal.queue.RoomDao;
import com.masbie.travelohealth.db.DBOpenHelper;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.pojo.service.RoomQueueProcessedPojo;
import com.masbie.travelohealth.pojo.service.RoomRequestPojo;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanKamar extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener
{
    private static final int REQUEST_IMAGE_CAPTURE = 0x01;
    private Button    tanggal;
    private Button    upload;
    private ImageView gambar;

    private File              gambarUpload;
    private String            photoTaken;
    private DatabaseReference mDatabase;
    private SweetAlertDialog  proses_kirim;
    private FirebaseStorage   storage        = FirebaseStorage.getInstance();
    private Bitmap            imageBitmap    = null;
    private LocalDate         tanggalpilihan = null;
    private DateTimeFormatter hms            = DateTimeFormat.forPattern("HH:mm:ss");
    private DateTimeFormatter ymd            = DateTimeFormat.forPattern("YYYY-MM-dd");
    private Random            random         = new Random();
    private DateTimeZone      zone           = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
    private DBOpenHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_kamar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final String  getGambar = getIntent().getExtras().getString("gambar", null);
        final Integer getKamar  = getIntent().getExtras().getInt("id_kamar", 0);
        final String  getKelas  = getIntent().getExtras().getString("kelas", null);
        final double  getHarga  = getIntent().getExtras().getDouble("harga", 0);

        if(getKamar == 0)
        {
            finish();
        }
        else
        {
            this.db = new DBOpenHelper(this);
            toolbar.setTitle("Pesan Kamar " + getKelas);
            ImageView toolbar_layout = findViewById(R.id.image_toolbar);
            toolbar_layout.setImageResource(R.drawable.vip1);
            /*StorageReference storageRef     = storage.getReference().child("images/" + getGambar);
            Glide.with(this)
                 .using(new FirebaseImageLoader())
                 .load(storageRef)
                 .into(toolbar_layout);*/

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
                                        // Calendar         calendar   = Calendar.getInstance();
                                        //StorageReference storageRef = storage.getReference();
                                        //StorageReference ImagesRef  = storageRef.child("surat-rujukan/SR - " + time + ".jpg");
                                        //final long       time       = calendar.getTimeInMillis();

                                        //InputStream stream = null;
                                        if(gambarUpload != null)
                                        {
                                            Integer         room     = getKamar;
                                            LocalDate       tanggal  = tanggalpilihan;
                                            RoomRequestPojo selected = new RoomRequestPojo(room, tanggal);
                                            RegisterDao.registerRoomRequest(selected, gambarUpload, PesanKamar.this, new Callback<ResponsePojo<RoomQueueProcessedPojo>>()
                                            {
                                                @SuppressWarnings("ConstantConditions") @Override public void onResponse(@NonNull Call<ResponsePojo<RoomQueueProcessedPojo>> call, @NonNull Response<ResponsePojo<RoomQueueProcessedPojo>> response)
                                                {
                                                    RoomQueueProcessedPojo queue = response.body().getData().getResult();
                                                    RoomDao.insertOrUpdate(db, queue);
                                                    FirebaseDao.subscribe(String.format(Locale.getDefault(), "room-%s", queue.getOrder().toString(ymd)));
                                                    proses_kirim.dismissWithAnimation();
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

                                                @Override public void onFailure(@NonNull Call<ResponsePojo<RoomQueueProcessedPojo>> call, @NonNull Throwable throwable)
                                                {
                                                    Dao.defaultFailureTask(PesanKamar.this, call, throwable);
                                                }
                                            });
                                            /*try
                                            {
                                                stream = new FileInputStream(gambarUpload);
                                            }
                                            catch(FileNotFoundException e)
                                            {
                                                e.printStackTrace();
                                            }*/
                                            /*UploadTask uploadTask = ImagesRef.putStream(stream);
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
                                            });*/
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
                        Uri               uri                      = FileProvider.getUriForFile(getApplicationContext(), "com.masbie.travelohealth.fileprovider", photoFile);
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
        tanggalpilihan = ymd.parseDateTime(String.format(Locale.getDefault(), "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)).toLocalDate();
        tanggal.setText("Tanggal Pesan: " + tanggalpilihan.toString(ymd));
    }

    @Override protected void onDestroy()
    {
        if(this.db != null)
        {
            this.db.close();
        }
        super.onDestroy();
    }
}
