package com.testt.productlist.db;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.testt.productlist.Conf;
import com.testt.productlist.entity.ProductEntity;

/**
 * {@link AppDatabase} class is responsible of application
 * database management
 * <p>
 * If any migration will require in the future, this class
 * will take care of it
 * <p>
 * If any new other Entities will be introduced to the
 * database, this class wil take care of it.
 * <p>
 * If any convertes(such as data long converter) will
 * be introduced, this class will take care of it.
 * <p>
 * <p>
 * <p>
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist.db
 */
@Database(entities = {ProductEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * singleton instance
     */
    private static AppDatabase sInstance;

    /**
     * database name
     */
    @VisibleForTesting
    public static final String DATABASE_NAME = Conf.DATABASE_NAME;

    /**
     *
     * @return {@link ProductDao}
     */
    public abstract ProductDao productDao();

    /**
     * provide instance
     *
     * @param context {@link Context}
     * @return {@link AppDatabase} insrance
     */
    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }
}