package com.testt.productlist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * {@link ProductModel} is part of the
 * url {@link com.testt.productlist.Conf#API_ROOT}
 * response. This is how each product model's image
 * model has been defined on the backend (??)
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
public class ProductImageModel implements Parcelable {

    public static final Creator<ProductImageModel> CREATOR = new Creator<ProductImageModel>() {
        @Override
        public ProductImageModel createFromParcel(Parcel in) {
            return new ProductImageModel(in);
        }

        @Override
        public ProductImageModel[] newArray(int size) {
            return new ProductImageModel[size];
        }
    };

    public final long id;
    public final String url;


    public ProductImageModel(long id, String url) {
        this.id = id;
        this.url = url;
    }

    public ProductImageModel(Parcel in) {
        id = in.readLong();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(url);
    }

}