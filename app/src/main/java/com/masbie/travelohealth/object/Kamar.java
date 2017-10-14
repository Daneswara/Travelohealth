package com.masbie.travelohealth.object;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Daneswara on 07/10/2017.
 */

@IgnoreExtraProperties
public class Kamar {

    public String gambar;
    public String id;
    public String fasilitas;
    public String kelas;
    public long harga;
    public long kapasitas;
    public long terisi;

    public Kamar() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Kamar(String id, String fasilitas, String kelas, long harga, String gambar) {
        this.id = id;
        this.gambar = gambar;
        this.fasilitas = fasilitas;
        this.kelas = kelas;
        this.harga = harga;
    }

}