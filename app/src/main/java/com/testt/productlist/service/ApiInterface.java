package com.testt.productlist.service;

import com.testt.productlist.model.GetProductsResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * {@link ApiInterface} is designed to access all
 * api end points
 * <p>
 * we have only one now :->>
 * but who knows in the future :P
 * <p>
 * <p>
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist.service
 */
public interface ApiInterface {

    @GET("5ccf0950300000770752c466")
    Call<GetProductsResponseModel> getProducts();

}
