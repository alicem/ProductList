package com.testt.productlist.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link GetProductsResponseModel} is the equivalent of one and
 * only url {@link com.testt.productlist.Conf#API_ROOT} response
 * <p>
 * It is parcelable {@link Parcelable} because and serialization
 * might be required in the future
 * <p>
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist.model
 */
public class GetProductsResponseModel implements Parcelable {

    public static final Creator<GetProductsResponseModel> CREATOR = new Creator<GetProductsResponseModel>() {
        @Override
        public GetProductsResponseModel createFromParcel(Parcel in) {
            return new GetProductsResponseModel(in);
        }

        @Override
        public GetProductsResponseModel[] newArray(int size) {
            return new GetProductsResponseModel[size];
        }
    };

    public final List<ProductModel> products;

    public GetProductsResponseModel(List<ProductModel> products) {
        this.products = products;
    }

    public GetProductsResponseModel(Parcel in) {
        products = new ArrayList<>();
        in.readTypedList(products, ProductModel.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(products);
    }

}
