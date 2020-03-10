package com.example.zexplore.model.dao;

import com.example.zexplore.model.Attachment;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;

@Dao
public interface AttachmentDAO {
    @Query("SELECT * FROM Attachment")
    LiveData<List<Attachment>> getAttachments();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAttachments(List<Attachment> attachmentList);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id, formId, image, type  FROM Attachment WHERE formId = :formId")
    LiveData<List<Attachment>> getAttachments(long formId);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id, formId, image, type  FROM Attachment WHERE formId = :formId")
    List<Attachment> getAttachment(long formId);

    @Query("SELECT * FROM Attachment WHERE formId = :formId")
    List<Attachment> getAllAttachments(long formId);

    @Query("DELETE FROM Attachment WHERE id = :id")
    void deleteAttachment(long id);

    @Query("DELETE FROM Attachment WHERE formId = :formId")
    void deleteFormAttachment(long formId);

}
