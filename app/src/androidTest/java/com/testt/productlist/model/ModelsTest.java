package com.testt.productlist.model;

import android.os.Parcel;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ModelsTest {

    private final long image_id = 11;
    private final String image_url = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
    private final long identifier = 12;
    private final String name = "lion";
    private final String brand = "check";
    private final double original_price = 10000;
    private final double current_price = 9000;
    private final String currency = "TL";

    @Test
    public void testParcelableProductImageModel() {

        ProductImageModel productImageModel = new ProductImageModel(image_id, image_url);

        Parcel parcel = Parcel.obtain();
        productImageModel.writeToParcel(parcel, productImageModel.describeContents());
        parcel.setDataPosition(0);

        ProductImageModel fromParcelProductImageModel = ProductImageModel.CREATOR.createFromParcel(parcel);

        assertThat(fromParcelProductImageModel.id, is(image_id));
        assertThat(fromParcelProductImageModel.url, is(image_url));

    }


    @Test
    public void testParcelableProductModel() {

        ProductImageModel productImageModel = new ProductImageModel(image_id, image_url);


        ProductModel productModel = new ProductModel(
                identifier,
                name,
                brand,
                original_price,
                current_price,
                currency,
                productImageModel
        );


        Parcel parcel = Parcel.obtain();
        productModel.writeToParcel(parcel, productModel.describeContents());
        parcel.setDataPosition(0);

        ProductModel fromParcelProductModel = ProductModel.CREATOR.createFromParcel(parcel);

        assertThat(fromParcelProductModel.identifier, is(identifier));
        assertThat(fromParcelProductModel.name, is(name));
        assertThat(fromParcelProductModel.brand, is(brand));
        assertThat(fromParcelProductModel.original_price, is(original_price));
        assertThat(fromParcelProductModel.current_price, is(current_price));
        assertThat(fromParcelProductModel.currency, is(currency));
        assertNotNull(fromParcelProductModel.image);
        assertThat(fromParcelProductModel.image.id, is(image_id));
        assertThat(fromParcelProductModel.image.url, is(image_url));

    }

    @Test
    public void testParcelableGetProductsResponseModel() {

        ProductImageModel productImageModel = new ProductImageModel(image_id, image_url);

        ProductModel productModel = new ProductModel(
                identifier,
                name,
                brand,
                original_price,
                current_price,
                currency,
                productImageModel
        );

        List<ProductModel> productModelList = Collections.singletonList(productModel);

        GetProductsResponseModel getProductsResponseModel = new GetProductsResponseModel(productModelList);


        Parcel parcel = Parcel.obtain();
        getProductsResponseModel.writeToParcel(parcel, getProductsResponseModel.describeContents());
        parcel.setDataPosition(0);

        GetProductsResponseModel fromParcelGetProductsResponseModel  = GetProductsResponseModel.CREATOR.createFromParcel(parcel);

        assertNotNull(fromParcelGetProductsResponseModel.products);
        assertThat(fromParcelGetProductsResponseModel.products.size(), is(1));

        assertThat(fromParcelGetProductsResponseModel.products.get(0).identifier, is(identifier));
        assertThat(fromParcelGetProductsResponseModel.products.get(0).name, is(name));
        assertThat(fromParcelGetProductsResponseModel.products.get(0).brand, is(brand));
        assertThat(fromParcelGetProductsResponseModel.products.get(0).original_price, is(original_price));
        assertThat(fromParcelGetProductsResponseModel.products.get(0).current_price, is(current_price));
        assertThat(fromParcelGetProductsResponseModel.products.get(0).currency, is(currency));
        assertNotNull(fromParcelGetProductsResponseModel.products.get(0).image);
        assertThat(fromParcelGetProductsResponseModel.products.get(0).image.id, is(image_id));
        assertThat(fromParcelGetProductsResponseModel.products.get(0).image.url, is(image_url));

    }
}