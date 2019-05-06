package com.testt.productlist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * {@link ProductModel} is part of the
 * url {@link com.testt.productlist.Conf#API_ROOT}
 * response. This is how each product model has been
 * defined on the backend (??)
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
public class ProductModel implements Parcelable {

    public static final Parcelable.Creator<ProductModel> CREATOR = new Parcelable.Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };


    public final long identifier;
    public final String name;
    public final String brand;
    public final double original_price;
    public final double current_price;
    public final String currency;
    public final ProductImageModel image;

    public ProductModel(long identifier, String name, String brand, double original_price, double current_price, String currency, ProductImageModel image) {
        this.identifier = identifier;
        this.name = name;
        this.brand = brand;
        this.original_price = original_price;
        this.current_price = current_price;
        this.currency = currency;
        this.image = image;
    }

    public ProductModel(Parcel in) {
        identifier = in.readLong();
        name = in.readString();
        brand = in.readString();
        original_price = in.readDouble();
        current_price = in.readDouble();
        currency = in.readString();
        image = in.readParcelable(ProductImageModel.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(identifier);
        dest.writeString(name);
        dest.writeString(brand);
        dest.writeDouble(original_price);
        dest.writeDouble(current_price);
        dest.writeString(currency);
        dest.writeParcelable(image, flags);
    }


}
