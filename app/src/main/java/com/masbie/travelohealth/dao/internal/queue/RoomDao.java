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
import com.masbie.travelohealth.pojo.service.RoomQueueProcessedPojo;
import com.masbie.travelohealth.pojo.service.RoomSummaryPojo;
import com.masbie.travelohealth.util.Util;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.LocalDate;
import timber.log.Timber;

public class RoomDao
{
    public static void insertOrUpdate(DBOpenHelper helper, RoomQueueProcessedPojo queue)
    {
        Timber.d("insertOrUpdate");
        // Gets the data repository in write mode

        final SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            // @formatter:off
            if(queue.getId() == null)               values.putNull("`id`");         else values.put("`id`",         queue.getId());
            if(queue.getQueue() == null)            values.putNull("`queue`");      else values.put("`queue`",      queue.getQueue());
            if(queue.getUserGroup() == null)        values.putNull("`u_group`");    else values.put("`u_group`",    queue.getUserGroup());
            if(queue.getRoomClassSector() == null)  values.putNull("`rcs`");        else values.put("`rcs`",        queue.getRoomClassSector());
            if(queue.getOrder() == null)            values.putNull("`order`");      else values.put("`order`",      queue.getOrder().toString(Util.formatLocalDate));
            if(queue.getValidity() == null)         values.putNull("`validity`");   else values.put("`validity`",   queue.getValidity());
            if(queue.getProcessed() == null)        values.putNull("`processed`");  else values.put("`processed`",  queue.getProcessed());
            if(queue.getMessage() == null)          values.putNull("`message`");    else values.put("`message`",    queue.getMessage());
            if(queue.getRoom() == null)             values.putNull("`room`");       else values.put("`room`",       queue.getRoom());
            if(queue.getTimestamp() == null)        values.putNull("`create_at`");  else values.put("`create_at`",  queue.getTimestamp().toString(Util.formatLocalDateTime));
            if(queue.getRoomSummary() == null)
            {
                values.putNull("`room_id`");
                values.putNull("`room_name`");
                values.putNull("`room_class`");
                values.putNull("`room_sector`");
                values.putNull("`room_feature`");
                values.putNull("`room_cost`");
            }
            else
            {
                if(queue.getRoomSummary().getId() == null)          values.putNull("`room_id`");        else values.put("`room_id`",        queue.getRoomSummary().getId());
                if(queue.getRoomSummary().getName() == null)        values.putNull("`room_name`");      else values.put("`room_name`",      queue.getRoomSummary().getName());
                if(queue.getRoomSummary().getRoomClass() == null)   values.putNull("`room_class`");     else values.put("`room_class`",     queue.getRoomSummary().getRoomClass());
                if(queue.getRoomSummary().getSector() == null)      values.putNull("`room_sector`");    else values.put("`room_sector`",    queue.getRoomSummary().getSector());
                if(queue.getRoomSummary().getFeature() == null)     values.putNull("`room_feature`");   else values.put("`room_feature`",   queue.getRoomSummary().getFeature());
                if(queue.getRoomSummary().getCost() == null)        values.putNull("`room_cost`");      else values.put("`room_cost`",      queue.getRoomSummary().getCost());
            }
            if(queue.getQueueProcessed() == null) values.putNull("`queue_processed`"); else values.put("`queue_processed`", queue.getQueueProcessed());
            // @formatter:on

            // insertOrUpdate the new row, returning the primary key value of the new row
            db.insertWithOnConflict("`room_queue`", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
        finally
        {
            db.close();
        }
    }

    public static List<RoomQueueProcessedPojo> findWhereOrder(DBOpenHelper helper, LocalDate order)
    {
        Timber.d("findWhereOrder");

        @NonNull final SQLiteDatabase          db     = helper.getReadableDatabase();
        @Nullable List<RoomQueueProcessedPojo> result = new LinkedList<>();
        try
        {
            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    "`id`",
                    "`queue`",
                    "`u_group`",
                    "`rcs`",
                    "`order`",
                    "`validity`",
                    "`processed`",
                    "`message`",
                    "`room`",
                    "`create_at`",
                    "`room_id`",
                    "`room_name`",
                    "`room_class`",
                    "`room_sector`",
                    "`room_feature`",
                    "`room_cost`",
                    "`queue_processed`"
            };

            String   selection     = "`order` = ?";
            String[] selectionArgs = {order.toString(Util.formatLocalDate)};

            Cursor cursor = db.query(
                    "`room_queue`",                     // The table to query
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
                result.add(new RoomQueueProcessedPojo(
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("id")) ? null : cursor.getInt(id),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("queue")) ? null : cursor.getInt(id),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("u_group")) ? null : cursor.getInt(id),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("rcs")) ? null : cursor.getInt(id),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("order")) ? null : Util.formatLocalDate.parseLocalDate(cursor.getString(id)),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("validity")) ? null : cursor.getString(id),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("processed")) ? null : cursor.getInt(id),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("message")) ? null : cursor.getString(id),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("room")) ? null : cursor.getInt(id),
                        cursor.isNull(id = cursor.getColumnIndexOrThrow("create_at")) ? null : Util.formatLocalDateTime.parseLocalDateTime(cursor.getString(id)),
                        new RoomSummaryPojo(
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("room_id")) ? null : cursor.getInt(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("room_name")) ? null : cursor.getString(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("room_class")) ? null : cursor.getString(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("room_sector")) ? null : cursor.getString(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("room_feature")) ? null : cursor.getString(id),
                                cursor.isNull(id = cursor.getColumnIndexOrThrow("room_cost")) ? null : cursor.getInt(id)
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
