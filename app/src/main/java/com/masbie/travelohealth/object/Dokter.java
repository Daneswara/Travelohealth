package com.masbie.travelohealth.object;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Daneswara on 07/10/2017.
 */

@IgnoreExtraProperties
public class Dokter {

    public String hari;
    public String gambar;
    public String id;
    public String jampraktek;
    public String nama;

    public Dokter() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Dokter(String id, String nama, String hari, String jampraktek, String gambar) {
        this.id = id;
        this.nama = nama;
        this.jampraktek = jampraktek;
        this.gambar = gambar;
        this.hari = hari;
    }

}