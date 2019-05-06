package com.testt.productlist;

import android.app.Application;

import com.testt.productlist.db.AppDatabase;
import com.testt.productlist.db.DataRepository;

/**
 * {@link App} one and only application class
 * <p>
 * provides access to application database class {@link AppDatabase}
 * and provides access to application repository class {@link DataRepository}
 * <p>
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist
 */
public class App extends Application {

    /**
     * provides database
     *
     * @return {@link AppDatabase}
     */
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    /**
     * provides repository
     *
     * @return {@link DataRepository}
     */
    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}