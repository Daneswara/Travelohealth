package com.masbie.travelohealth.object;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Daneswara on 07/10/2017.
 */

@IgnoreExtraProperties
public class ValidasiKamar
{

    public String gambar;
    public String id_kamar;
    public String tanggal;
    public String kelas;
    public long   harga;

    public ValidasiKamar()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ValidasiKamar(String id_kamar, String tanggal, String kelas, long harga, String gambar)
    {
        this.id_kamar = id_kamar;
        this.gambar = gambar;
        this.tanggal = tanggal;
        this.kelas = kelas;
        this.harga = harga;
    }

}