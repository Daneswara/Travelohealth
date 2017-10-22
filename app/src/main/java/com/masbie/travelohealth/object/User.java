package com.masbie.travelohealth.object;

import com.google.firebase.database.IgnoreExtraProperties;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Daneswara on 07/10/2017.
 */

@IgnoreExtraProperties
public class User
{

    public String name;
    public String email;
    public String register;

    public User()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email)
    {
        this.name = name;
        this.email = email;
        this.register = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    }

}