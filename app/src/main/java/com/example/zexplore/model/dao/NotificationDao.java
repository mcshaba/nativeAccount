package com.example.zexplore.model.dao;

import com.example.zexplore.model.Notification;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface NotificationDao {
    @Query("SELECT COUNT(*) FROM Notification WHERE status = 1")
    Single<Long> getNotificationCount();

    @Query("SELECT * FROM Notification ORDER BY id DESC")
    Single<List<Notification>> getNotifications();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotification(Notification notification);

    @Query("DELETE FROM Notification WHERE id = :id")
    void deleteNotification(int id);
}
