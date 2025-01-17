package com.example.agrimart.data.interface1;

import com.example.agrimart.data.model.OrderGHN;
import com.fasterxml.jackson.databind.JsonNode;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GhnApiService {

    @GET("master-data/province")
    Call<JsonNode> getProvinces(@Header("token") String token);

    @GET("master-data/district")
    Call<JsonNode> getDistricts(@Header("token") String token, @Query("province_id") int provinceId);

    @GET("master-data/ward")
    Call<JsonNode> getWards(@Header("token") String token, @Query("district_id") int districtId);

    @POST("v2/shipping-order/fee")
    Call<JsonNode> getFeeOrder(
            @Header("token") String token,
            @Header("ShopId") String shopId,
            @Header("Content-Type") String contentType,
            @Body RequestBody requestBody
    );

    @POST("v2/shipping-order/create")
    Call<JsonNode> createShippingOrder(
            @Header("token") String token,
            @Header("ShopId") String shopId,
            @Header("Content-Type") String contentType,
            @Body RequestBody requestBody
    );

    @POST("v2/switch-status/cancel")
    Call<JsonNode> cancelShippingOrder(
            @Header("token") String token,
            @Header("ShopId") String shopId,
            @Body OrderGHN requestBody
    );

    @POST("v2/a5/gen-token")
    Call<JsonNode> generateToken(
            @Header("token") String token,
            @Header("ShopId") String shopId,
            @Body OrderGHN requestBody
    );

    @POST("v2/shipping-order/detail")
    Call<JsonNode> getOrderDetail(
            @Header("token") String token,
            @Header("ShopId") String shopId,
            @Body RequestBody requestBody
    );




}
