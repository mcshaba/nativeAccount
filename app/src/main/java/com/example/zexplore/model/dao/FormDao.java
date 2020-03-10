package com.example.zexplore.model.dao;

import com.example.zexplore.model.Form;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FormDao {
    @Query("SELECT * FROM Form WHERE id = :id")
    LiveData<Form> getFormById(long id);

    @Query("SELECT * FROM Form WHERE id = :id")
    Form getNotifiedForm(long id);

    @Query("SELECT * FROM Form WHERE sync = 0 ORDER BY id DESC")
    LiveData<List<Form>> getSavedForms();

    @Query("SELECT * FROM Form WHERE sync = 1 ORDER BY id DESC")
    LiveData<List<Form>> getCompletedForms();

    @Query("SELECT * FROM Form WHERE sync = 2 ORDER BY id DESC")
    LiveData<List<Form>> getSentForms();

    @Query("SELECT * FROM Form WHERE sync = 1 AND status = 0")
    List<Form> getUnSyncedForms();

    @Query("SELECT * FROM Form WHERE sync = 2 AND status = 0")
    List<Form> getSyncedForms();

    @Query("SELECT * FROM Form WHERE sync = 3 AND status = 2 ORDER BY id DESC")
    LiveData<List<Form>> getRejectedForms();

    @Query("SELECT * FROM Form")
    LiveData<List<Form>> getForms();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertForm(Form form);

    @Delete
    void deleteForm(Form form);
}
