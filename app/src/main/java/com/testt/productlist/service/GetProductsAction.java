package com.testt.productlist.service;

import com.testt.productlist.model.GetProductsResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * {@link GetProductsAction} is a network action class
 * {@link GetProductsAction} responsible of delivering
 * products
 * <p>
 * <p>
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist.service
 */
public class GetProductsAction {

    /**
     * action {@link Listener} Fetch products action
     * http response callback interface
     */
    public interface Listener {
        void getProductsSuccess(GetProductsResponseModel response);

        void onFailure(Call call, Throwable t);
    }

    public static void perform(final Listener listener) {
        ApiClient.getInstance().getApiInterface().getProducts().enqueue(new Callback<GetProductsResponseModel>() {
            @Override
            public void onResponse(Call<GetProductsResponseModel> call, Response<GetProductsResponseModel> response) {
                if (listener != null) {
                    try {
                        listener.getProductsSuccess(response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetProductsResponseModel> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure(call, t);
                }
            }
        });
    }

}
