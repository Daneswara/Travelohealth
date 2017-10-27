package com.masbie.travelohealth.db;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 27 October 2017, 7:14 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import timber.log.Timber;

public class DBOpenHelper extends SQLiteOpenHelper
{
    // If you change the database schema, you must increment the database version.
    public static final int    DATABASE_VERSION = 1;
    public static final String DATABASE_NAME    = "queue.db";

    public DBOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Timber.d("DBOpenHelper");
    }

    public void onCreate(SQLiteDatabase db)
    {
        Timber.d("onCreate");

        // @formatter:off
        db.execSQL("" +
                "CREATE TABLE IF NOT EXISTS `service_queue`" +
                "(" +
                    "`id` INTEGER PRIMARY KEY," +
                    "`queue` INTEGER," +
                    "`order` TEXT," +
                    "`timestamp` TEXT," +
                    "`start` TEXT," +
                    "`end` TEXT," +
                    "`service_id` INTEGER," +
                    "`service_name` TEXT," +
                    "`service_start` TEXT," +
                    "`service_end` TEXT," +
                    "`doctor_id` INTEGER," +
                    "`doctor_name` TEXT," +
                    "`doctor_start` TEXT," +
                    "`doctor_end` TEXT," +
                    "`queue_processed` INTEGER" +
                ");");
        db.execSQL("" +
                "CREATE TABLE IF NOT EXISTS `room_queue`" +
                "(" +
                    "`id` INTEGER PRIMARY KEY," +
                    "`queue` INTEGER," +
                    "`u_group` INTEGER," +
                    "`rcs` INTEGER," +
                    "`order` TEXT," +
                    "`validity` TEXT," +
                    "`processed` INTEGER," +
                    "`message` STRING," +
                    "`room` INTEGER," +
                    "`create_at` TEXT," +
                    "`room_id` INTEGER," +
                    "`room_name` TEXT," +
                    "`room_class` TEXT," +
                    "`room_sector` TEXT," +
                    "`room_feature` TEXT," +
                    "`room_cost` TEXT," +
                    "`queue_processed` INTEGER" +
                ");" +
                "");
        // @formatter:on
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Timber.d("onUpgrade");

        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // @formatter:off
        db.execSQL("" +
                "DROP TABLE IF EXISTS `service_queue`;" +
                "" +
                "DROP TABLE IF EXISTS `room_queue`;" +
                "" +
                "");
        // @formatter:on
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Timber.d("onDowngrade");

        onUpgrade(db, oldVersion, newVersion);
    }
}
