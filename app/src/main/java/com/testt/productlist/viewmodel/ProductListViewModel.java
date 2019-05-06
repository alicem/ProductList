package com.testt.productlist.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.testt.productlist.App;
import com.testt.productlist.db.DataRepository;
import com.testt.productlist.entity.ProductEntity;

import java.util.List;

/**
 * {@link ProductListViewModel} is the bridge between ui and data
 * {@link ProductListViewModel} gets access to data through {@link DataRepository}
 * <p>
 * <p>
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist.viewmodel
 */
public class ProductListViewModel extends AndroidViewModel {

    /**
     * {@link DataRepository} instance
     */
    private final DataRepository mRepository;

    /**
     * products live data
     */
    private final MediatorLiveData<List<ProductEntity>> mObservableProducts;

    /**
     * network error live data
     */
    private MediatorLiveData<Boolean> mObservableNetworkError;

    public ProductListViewModel(Application application) {
        super(application);

        /*
         * {@link ProductListViewModel#mObservableProducts} initialization
         */
        mObservableProducts = new MediatorLiveData<>();
        mObservableProducts.setValue(null);

        /*
         * {@link ProductListViewModel#mObservableNetworkError} initialization
         */
        mObservableNetworkError = new MediatorLiveData<>();
        mObservableNetworkError.setValue(false);

        /*
          {@link ProductListViewModel#mRepository} initialization
         */
        mRepository = ((App) application).getRepository();

        /*
         * {@link ProductListViewModel#mObservableProducts} add source
         */

        mObservableProducts.addSource(mRepository.getProducts(), mObservableProducts::setValue);
        /*
         * {@link ProductListViewModel#mObservableNetworkError} add source
         */
        mObservableNetworkError.addSource(mRepository.getNetworkError(), mObservableNetworkError::setValue);
    }

    /**
     * get subscribable database products saved
     *
     * @return subscribable database saved products as in
     * {@link ProductListViewModel#mObservableProducts}
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
        return mRepository.searchProducts(query);
    }

    /**
     * will patch the request to repository
     * level to clear the products from local database
     */
    public void clearProducts() {
        mRepository.clearProducts();
    }

    /**
     * get subscribable network error status
     *
     * @return subscribable network error status as in
     * {@link ProductListViewModel#mObservableNetworkError} status
     */
    public MediatorLiveData<Boolean> getNetworkError() {
        return mObservableNetworkError;
    }

    /**
     * force set network error to false
     * <p>
     * this will be used when the re try button
     * on the error pane has clicked.
     */
    public void resetNetworkError() {
        mObservableNetworkError.setValue(false);
    }

    /**
     * will patch the request to repository
     * level to send the network request which fetchs
     * products again.
     */
    public void sendRequest() {
        mRepository.sendRequest();
    }

}