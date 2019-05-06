package com.testt.productlist.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.testt.productlist.entity.ProductEntity;

import java.util.List;

/**
 * {@link ProductDao} interface is responsible of
 * database CRUD actions
 * <p>
 * since there is only one table and this is a simple schema
 * there are not many query methods; yet if any other query method
 * requirement will born in the future, here {@link ProductDao}
 * is where we add them.
 * <p>
 * {@link ProductDao} is only accessable by {@link DataRepository}
 * <p>
 * <p>
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist.db
 */
@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> loadAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductEntity> products);

    @Query("SELECT * FROM products WHERE name LIKE :query")
    LiveData<List<ProductEntity>> searchAllProducts(String query);

    @Query("DELETE FROM products")
    public void clearProducts();

}
