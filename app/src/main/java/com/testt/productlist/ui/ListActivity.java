package com.testt.productlist.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.testt.productlist.R;
import com.testt.productlist.entity.ProductEntity;
import com.testt.productlist.viewmodel.ProductListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist.ui
 */
public class ListActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener,
        ProductAdapter.Listener {

    /**
     * butterknife {@link ButterKnife}
     * UI binded  {@link SwipeRefreshLayout}
     * <p>
     * will be used for pull to refresh
     */
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * butterknife {@link ButterKnife}
     * UI binded  {@link RecyclerView}
     * <p>
     * product items will be represented
     * here
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    /**
     * butterknife {@link ButterKnife}
     * UI binded  {@link Toolbar}
     * <p>
     * will disappear and reappear
     * on scroll and search menu
     * icon will be here
     */
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /**
     * butterknife {@link ButterKnife}
     * UI binded  {@link ProgressBar}
     * <p>
     * will be the indicator representitve
     * of the network request in progress
     */
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    /**
     * butterknife {@link ButterKnife}
     * UI binded  {@link ProgressBar}
     * <p>
     * this is the nework error pane
     * will appear when there is an error
     * on the network (such as there
     * is no internet connection)
     */
    @BindView(R.id.errorPane)
    LinearLayout errorPane;

    /**
     * private RecyclerView Adapter instance
     */
    private RecyclerView.Adapter mAdapter;

    /**
     * private RecyclerView LayoutManager instance // might have been local anyway
     */
    private RecyclerView.LayoutManager mLayoutManager;


    /**
     * private ProductListViewModel view model instance {@link ProductListViewModel}
     */
    private ProductListViewModel mViewModel;

    /**
     * private SearchView instance {@link SearchView}
     */
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        swipeRefreshLayout.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mViewModel = ViewModelProviders.of(this).get(ProductListViewModel.class);

        subscribeUi(mViewModel.getProducts());
        subscribeNetworkError(mViewModel.getNetworkError());

    }


    /**
     * @param menu - search menu
     * @return - false --- omit
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(this);

        return true;
    }


    /**
     * full to refresh triggered
     */
    @Override
    public void onRefresh() {
        if (!mSearchView.isIconified()) {
            mSearchView.setIconified(true);
            mSearchView.onActionViewCollapsed();
        }
        mAdapter = new ProductAdapter(new ArrayList<ProductEntity>(), ListActivity.this);
        recyclerView.setAdapter(mAdapter);
        progressBar.setVisibility(View.VISIBLE);
        mViewModel.clearProducts();
    }


    /**
     * re try button on network
     * error pane has been clicked
     * <p>
     * the same request will be tried
     * to send again
     */
    @OnClick(R.id.retryButton)
    public void retryButtonClicked() {
        mViewModel.resetNetworkError();
        errorPane.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        mViewModel.sendRequest();
    }


    /**
     * here we subscribe to the provided
     * product list by viewmodel
     *
     * @param liveData viewmodel {@link ProductListViewModel}
     *                 product list livedata
     */
    private void subscribeUi(LiveData<List<ProductEntity>> liveData) {
        liveData.observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productModelList) {
                if (productModelList != null) {
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                    mAdapter = new ProductAdapter(productModelList, ListActivity.this);
                    recyclerView.setAdapter(mAdapter);

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    /**
     * here we subscribe to the provided
     * network error status viewmodel
     *
     * @param error viewmodel {@link ProductListViewModel}
     *              product network error status livedata
     */
    private void subscribeNetworkError(LiveData<Boolean> error) {
        error.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean error) {
                if (error != null && error) {
                    progressBar.setVisibility(View.GONE);
                    errorPane.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    // omit
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }


    /**
     * @param s query to be searched among locally saved products
     *          we search on type
     * @return - false --- omit
     */
    @Override
    public boolean onQueryTextChange(String s) {
        if (TextUtils.isEmpty(s)) {
            mAdapter = new ProductAdapter(mViewModel.getProducts().getValue(), ListActivity.this);
            recyclerView.setAdapter(mAdapter);
            return false;
        }

        mViewModel.searchProducts(s + "%").observe(
                this, new Observer<List<ProductEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<ProductEntity> productModelList) {
                        if (productModelList != null) {
                            mAdapter = new ProductAdapter(productModelList, ListActivity.this);
                            recyclerView.setAdapter(mAdapter);
                        }
                    }
                }
        );
        return false;
    }


    /**
     * here the product item has been clicked, we will open
     * the full screen image activity now
     *
     * @param productEntity clicked product entity
     */
    @Override
    public void itemClicked(ProductEntity productEntity) {
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("url", productEntity.getUrl());
        startActivity(intent);

    }
}
