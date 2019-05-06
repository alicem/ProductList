package com.testt.productlist.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.testt.productlist.entity.ProductEntity;
import com.testt.productlist.model.GetProductsResponseModel;
import com.testt.productlist.model.ProductModel;
import com.testt.productlist.service.GetProductsAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;

/**
 * {@link DataRepository} class is responsible of delivering
 * the data to the relevant UI
 * <p>
 * There main responsibilities of {@link DataRepository} are basically :
 * <p>
 * 1. Deliver the products if there are any in the database
 * 2. Fetch them from url if there are not any in the database
 * 3. Save the from url fetched ones in the database
 * 4. If there are any problems with fetching the products
 * ___from url, notify the subscriber ui
 * 5. Provide searching capability (i.e. respond the product
 * ___list which's name starts with the input query value)
 * <p>
 * <p>
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist.db
 */
public class DataRepository implements GetProductsAction.Listener {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<ProductEntity>> mObservableProducts;
    private MediatorLiveData<Boolean> mObservableNetworkError;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableProducts = new MediatorLiveData<>();
        mObservableNetworkError = new MediatorLiveData<>();

        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(),
                productEntities -> {
                    if (productEntities != null && productEntities.size() > 0) {
                        mObservableProducts.postValue(productEntities);
                    } else {
                        sendRequest();
                    }
                });
    }

    /**
     * this method will send the one and only
     * network request of the application.
     * Which is fetching the products from url
     * <p>
     * {@link com.testt.productlist.Conf#API_ROOT}
     */
    public void sendRequest() {
        GetProductsAction.perform(DataRepository.this);
    }

    /**
     * @param database Room database {@link AppDatabase}
     *                 The one and only database of the giant app scope :>
     * @return {@link DataRepository} singleton instance
     */
    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * get subscribable database products saved
     *
     * @return subscribable database saved products as in
     * {@link DataRepository#mObservableProducts}
     */
    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }

    /**
     * get result of the search from local database
     * procedure is name starts with!
     *
     * @param query - search input (name of the product)
     *              it is not case sensitive
     * @return the list of products whose name starts with the query
     */
    public LiveData<List<ProductEntity>> searchProducts(String query) {
        return mDatabase.productDao().searchAllProducts(query);
    }

    /**
     * will clear products from database
     * as clear as is
     * <p>
     * The reason this method has implemented is,
     * the pull to refresh functionality will refresh also locally
     * saved products. In this case there is a need to clear
     * the products saved in local database and....
     * <p>
     * ... this method is the winner !(?) ;)
     */
    public void clearProducts() {
        mDatabase.productDao().clearProducts();
    }

    /**
     * get subscribable network error status
     *
     * @return subscribable network error status as in
     * {@link DataRepository#mObservableNetworkError} status
     */
    public LiveData<Boolean> getNetworkError() {
        return mObservableNetworkError;
    }

    /**
     * This is a method of {@link GetProductsAction.Listener}
     * <p>
     * will be called after product items are fetched from url successfully
     *
     * @param response url response capsulated as {@link GetProductsResponseModel}
     *                 result will contain products fetched
     */
    @Override
    public void getProductsSuccess(GetProductsResponseModel response) {

        List<ProductModel> vals = response.products;

        Collections.sort(vals, new Comparator<ProductModel>() {
            @Override
            public int compare(final ProductModel object1, final ProductModel object2) {
                return object1.name.compareTo(object2.name);
            }
        });

        mDatabase.runInTransaction(() -> {
            mDatabase.productDao().insertAll(modelToEntity(vals));
        });
    }


    /**
     * This is a method of {@link GetProductsAction.Listener}
     * <p>
     * will be called after product items fetch failed !!
     * <p>
     * mObservableNetworkError livedata {@link DataRepository#mObservableNetworkError}
     * instance will be updated and the subscriber user interfaces
     * will be awaken (!)
     *
     * @param call - retrofit {@link Call}
     * @param t    - retrofit {@link retrofit2.Retrofit}
     *             - Throwable (java.lang.Throwable)
     */
    @Override
    public void onFailure(Call call, Throwable t) {
        mObservableNetworkError.postValue(true);
        mObservableNetworkError.setValue(false);
    }


    /**
     * This method will convert the product list retrieved
     * from url in to the shape that they will be used within
     * the application.
     * <p>
     * The data data types used within the app are based on
     * the local database models (i.e. entities). The data
     * type used within the app is {@link ProductEntity}
     * <p>
     * The data type provided by the url
     * {@link com.testt.productlist.Conf#API_ROOT}
     * is {@link ProductModel}
     *
     * @param productModelList {@link List<ProductModel>} the Model List of
     *                         {@link ProductModel} which retrieved
     *                         from the provided url
     * @return {@link List<ProductEntity>} the {@link ProductEntity} version
     * of the list provided in the input.
     */
    private List<ProductEntity> modelToEntity(List<ProductModel> productModelList) {
        List<ProductEntity> productEntityList = new ArrayList<>();

        for (ProductModel productModel : productModelList) {

            ProductEntity productEntity = new ProductEntity(
                    productModel.identifier,
                    productModel.name,
                    productModel.brand,
                    productModel.original_price,
                    productModel.current_price,
                    productModel.currency,
                    productModel.image == null ? "" : productModel.image.url
            );
            productEntityList.add(productEntity);


        }
        return productEntityList;
    }


}
