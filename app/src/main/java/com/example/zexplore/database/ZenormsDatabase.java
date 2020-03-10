package com.example.zexplore.database;
import android.content.Context;

import com.example.zexplore.model.AccountClass;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.City;
import com.example.zexplore.model.Country;
import com.example.zexplore.model.Form;
import com.example.zexplore.model.Notification;
import com.example.zexplore.model.Occupation;
import com.example.zexplore.model.State;
import com.example.zexplore.model.Title;
import com.example.zexplore.model.dao.AttachmentDAO;
import com.example.zexplore.model.dao.FormDao;
import com.example.zexplore.model.dao.NotificationDao;
import com.example.zexplore.model.dao.SpinnerDao;
import com.example.zexplore.util.DateConverter;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Created by michael.shaba on 3/21/2018.
 */
@Database(entities = {Form.class, State.class, City.class,
        AccountClass.class, Country.class, Title.class, Occupation.class, Attachment.class, Notification.class}, version = 9, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class ZenormsDatabase extends RoomDatabase {

    public abstract FormDao formDao();

    public abstract SpinnerDao spinnerDao();

    public abstract AttachmentDAO attachmentDao();

    public abstract NotificationDao notificationDao();

    private static ZenormsDatabase database;

    private static Context _context;
    public static ZenormsDatabase getDatabase(final Context context){
        _context = context;
        if (database == null){
            synchronized (ZenormsDatabase.class){
                if (database == null){
                    database = Room.databaseBuilder(context.getApplicationContext(),
                            ZenormsDatabase.class, "zxplore.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return database;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }
    };

}
