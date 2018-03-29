package com.iclaude.scheduledrecorder.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Operations on database.
 */

@Dao
public interface RecordingsDao {

    // Table "saved_recordings".
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertRecording(Recording recording);

    @Update
    int updateRecording(Recording recording);

    @Delete
    int deleteRecording(Recording recording);

    @Query("SELECT * FROM saved_recordings WHERE id = :id")
    Recording getRecordingById(int id);


    @Query("SELECT * FROM saved_recordings ORDER BY time_added DESC")
    LiveData<List<Recording>> getAllRecordings();

    @Query("SELECT COUNT(*) FROM saved_recordings")
    int getRecordingsCount();


    // Table "scheduled_recordings".
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertScheduledRecording(ScheduledRecording scheduledRecording);

    @Update
    int updateScheduledRecordings(ScheduledRecording... scheduledRecordings);

    @Delete
    int deleteScheduledRecordings(ScheduledRecording... scheduledRecordings);

    @Query("SELECT * FROM scheduled_recordings WHERE id = :id")
    ScheduledRecording getScheduledRecordingById(int id);

    @Query("SELECT * FROM scheduled_recordings")
    LiveData<List<ScheduledRecording>> getAllScheduledRecordings();

    // Returns the next scheduled recording from now.
    @Query("SELECT * FROM scheduled_recordings ORDER BY start_time LIMIT 1")
    ScheduledRecording getNextScheduledRecording();

    @Query("SELECT COUNT(*) FROM scheduled_recordings WHERE ((:start >= start_time AND :start <= end_time) OR (:end >= start_time AND :end <= end_time) OR (:start < start_time AND :end > end_time)) AND :exceptId != id")
    int getNumRecordingsAlreadyScheduled(long start, long end, int exceptId);

    // Returns all scheduled recordings whose field start is between start and end.
    @Query("SELECT * FROM scheduled_recordings WHERE start_time BETWEEN :start AND :end")
    List<ScheduledRecording> getScheduledRecordingsBetween(long start, long end);

    @Query("SELECT COUNT(*) FROM scheduled_recordings")
    int getScheduledRecordingsCount();
}
