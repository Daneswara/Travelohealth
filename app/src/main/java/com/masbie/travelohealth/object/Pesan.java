package com.masbie.travelohealth.object;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Daneswara on 07/10/2017.
 */

@IgnoreExtraProperties
public class Pesan {

    public String id_pemesanan;
    public long no_antrian;
    public long pelayanan;
    public String status;
    public String uid_pasien;

    public Pesan() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Pesan(String id_pemesanan, long no_antrian, long pelayanan, String status, String uid_pasien) {
        this.id_pemesanan = id_pemesanan;
        this.no_antrian = no_antrian;
        this.pelayanan = pelayanan;
        this.status = status;
        this.uid_pasien = uid_pasien;
    }

}