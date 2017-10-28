package com.masbie.travelohealth.dao.internal.queue;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 27 October 2017, 7:37 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.masbie.travelohealth.db.DBOpenHelper;
import com.masbie.travelohealth.pojo.service.DoctorPojo;
import com.masbie.travelohealth.pojo.service.ServicePojo;
import com.masbie.travelohealth.pojo.service.ServiceQueueProcessedPojo;
import com.masbie.travelohealth.util.Util;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.LocalDate;
import timber.log.Timber;

public class ServiceDao
{
    public static void insertOrUpdate(DBOpenHelper helper, ServiceQueueProcessedPojo queue)
    {
        Timber.d("insertOrUpdate");
        // Gets the data repository in write mode

        final SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            // @formatter:off
            if(queue.getId() == null) values.putNull("`id`"); else values.put("`id`", queue.getId());
            if(queue.getQueue() == null) values.putNull("`queue`"); else values.put("`queue`", queue.getQueue());
            if(queue.getOrder() == null) values.putNull("`order`"); else values.put("`order`", queue.getOrder().toString(Util.formatLocalDate));
            if(queue.getTimestamp() == null) values.putNull("`timestamp`"); else values.put("`timestamp`", queue.getTimestamp().toString(Util.formatLocalDateTime));
            if(queue.getStart() == null) values.putNull("`start`"); else values.put("`start`", queue.getStart().toString(Util.formatLocalTime));
            if(queue.getEnd() == null) values.putNull("`end`"); else values.put("`end`", queue.getEnd().toString(Util.formatLocalTime));
            if(queue.getService() == null)
            {
                values.putNull("`service_id`");
                values.putNull("`service_name`");
                values.putNull("`service_start`");
                values.putNull("`service_end`");
            }
            else
            {
                if(queue.getService().getId() == null) values.putNull("`service_id`"); else values.put("`service_id`", queue.getService().getId());
                if(queue.getService().getName() == null) values.putNull("`service_name`"); else values.put("`service_name`", queue.getService().getName());
                if(queue.getService().getTimeStart() == null) values.putNull("`service_start`"); else values.put("`service_start`", queue.getService().getTimeStart().toString(Util.formatLocalTime));
                if(queue.getService().getTimeEnd() == null) values.putNull("`service_end`"); else values.put("`service_end`", queue.getService().getTimeEnd().toString(Util.formatLocalTime));
            }
            if(queue.getDoctor() == null)
            {
                values.putNull("`doctor_id`");
                values.putNull("`doctor_name`");
                values.putNull("`doctor_start`");
                values.putNull("`doctor_end`");
            }
            else
            {
                if(queue.getDoctor().getId() == null) values.putNull("`doctor_id`"); else values.put("`doctor_id`", queue.getDoctor().getId());
                if(queue.getDoctor().getName() == null) values.putNull("`doctor_name`"); else values.put("`doctor_name`", queue.getDoctor().getName());
                if(queue.getDoctor().getTimeStart() == null) values.putNull("`doctor_start`"); else values.put("`doctor_start`", queue.getDoctor().getTimeStart().toString(Util.formatLocalTime));
                if(queue.getDoctor().getTimeEnd() == null) values.putNull("`doctor_end`"); else values.put("`doctor_end`", queue.getDoctor().getTimeEnd().toString(Util.formatLocalTime));
            }
            if(queue.getQueueProcessed() == null) values.putNull("`queue_processed`"); else values.put("`queue_processed`", queue.getQueueProcessed());
            // @formatter:on

            // insertOrUpdate the new row, returning the primary key value of the new row
            db.insertWithOnConflict("`service_queue`", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
        finally
        {
            db.close();
        }
    }

    public static List<ServiceQueueProcessedPojo> findWhereServiceIDAndOrder(DBOpenHelper helper, Integer serviceId, LocalDate order)
    {
        Timber.d("findWhereServiceIDAndOrder");

        @NonNull final SQLiteDatabase             db     = helper.getReadableDatabase();
        @Nullable List<ServiceQueueProcessedPojo> result = new LinkedList<>();
        try
        {
            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    "`id`",
                    "`queue`",
                    "`order`",
                    "`timestamp`",
                    "`start`",
                    "`end`",
                    "`service_id`",
                    "`service_name`",
                    "`service_start`",
                    "`service_end`",
                    "`doctor_id`",
                    "`doctor_name`",
                    "`doctor_start`",
                    "`doctor_end`",
                    "`queue_processed`"
            };

            String   selection     = "`service_id` = ? AND `order` = ?";
            String[] selectionArgs = {String.valueOf(serviceId.intValue()), order.toString(Util.formatLocalDate)};

            Cursor cursor = db.query(
                    "`service_queue`",                     // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                 // The sort order
            );

            int id;
            while(cursor.moveToNext())
            {
                // @formatter:off
                result.add(new ServiceQueueProcessedPojo(
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("id")) ? null : cursor.getInt(id),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("queue")) ? null : cursor.getInt(id),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("order")) ? null : Util.formatLocalDate.parseLocalDate(cursor.getString(id)),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("timestamp")) ? null : Util.formatLocalDateTime.parseLocalDateTime(cursor.getString(id)),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("start")) ? null : Util.formatLocalTime.parseLocalTime(cursor.getString(id)),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("end")) ? null : Util.formatLocalTime.parseLocalTime(cursor.getString(id)),
                        new ServicePojo(
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("service_id")) ? null : cursor.getInt(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("service_name")) ? null : cursor.getString(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("service_start")) ? null : Util.formatLocalTime.parseLocalTime(cursor.getString(id)),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("service_end")) ? null : Util.formatLocalTime.parseLocalTime(cursor.getString(id))
                        ),
                        new DoctorPojo(
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("doctor_id")) ? null : cursor.getInt(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("doctor_name")) ? null : cursor.getString(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("doctor_start")) ? null : Util.formatLocalTime.parseLocalTime(cursor.getString(id)),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("doctor_end")) ? null : Util.formatLocalTime.parseLocalTime(cursor.getString(id))
                        ),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("queue_processed")) ? null : cursor.getInt(id)
                ));
                // @formatter:on
            }
            cursor.close();

        }
        finally
        {
            db.close();
        }

        return result;
    }

    public static List<ServiceQueueProcessedPojo> findWhereOrder(DBOpenHelper helper, LocalDate order)
    {
        Timber.d("findWhereServiceIDAndOrder");

        @NonNull final SQLiteDatabase             db     = helper.getReadableDatabase();
        @Nullable List<ServiceQueueProcessedPojo> result = new LinkedList<>();
        try
        {
            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    "`id`",
                    "`queue`",
                    "`order`",
                    "`timestamp`",
                    "`start`",
                    "`end`",
                    "`service_id`",
                    "`service_name`",
                    "`service_start`",
                    "`service_end`",
                    "`doctor_id`",
                    "`doctor_name`",
                    "`doctor_start`",
                    "`doctor_end`",
                    "`queue_processed`"
            };

            String   selection     = "`order` = ?";
            String[] selectionArgs = {order.toString(Util.formatLocalDate)};

            Cursor cursor = db.query(
                    "`service_queue`",                     // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                 // The sort order
            );

            int id;
            while(cursor.moveToNext())
            {
                // @formatter:off
                result.add(new ServiceQueueProcessedPojo(
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("id")) ? null : cursor.getInt(id),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("queue")) ? null : cursor.getInt(id),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("order")) ? null : Util.formatLocalDate.parseLocalDate(cursor.getString(id)),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("timestamp")) ? null : Util.formatLocalDateTime.parseLocalDateTime(cursor.getString(id)),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("start")) ? null : Util.formatLocalTime.parseLocalTime(cursor.getString(id)),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("end")) ? null : Util.formatLocalTime.parseLocalTime(cursor.getString(id)),
                        new ServicePojo(
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("service_id")) ? null : cursor.getInt(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("service_name")) ? null : cursor.getString(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("service_start")) ? null : Util.formatLocalTime.parseLocalTime(cursor.getString(id)),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("service_end")) ? null : Util.formatLocalTime.parseLocalTime(cursor.getString(id))
                        ),
                        new DoctorPojo(
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("doctor_id")) ? null : cursor.getInt(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("doctor_name")) ? null : cursor.getString(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("doctor_start")) ? null : Util.formatLocalTime.parseLocalTime(cursor.getString(id)),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("doctor_end")) ? null : Util.formatLocalTime.parseLocalTime(cursor.getString(id))
                        ),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("queue_processed")) ? null : cursor.getInt(id)
                ));
                // @formatter:on
            }
            cursor.close();

        }
        finally
        {
            db.close();
        }

        return result;
    }

}
