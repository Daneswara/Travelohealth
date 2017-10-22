package com.masbie.travelohealth.object;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Daneswara on 07/10/2017.
 */

@IgnoreExtraProperties
public class Poli
{

    public String hari;
    public String gambar;
    public String id;
    public String jamkerja;
    public String pelayanan;

    public Poli()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Poli(String id, String pelayanan, String hari, String jamkerja, String gambar)
    {
        this.id = id;
        this.pelayanan = pelayanan;
        this.jamkerja = jamkerja;
        this.gambar = gambar;
        this.hari = hari;
    }

}