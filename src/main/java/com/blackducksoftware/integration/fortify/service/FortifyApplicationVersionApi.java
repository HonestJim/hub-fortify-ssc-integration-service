/*
 * Copyright (C) 2017 Black Duck Software Inc.
 * http://www.blackducksoftware.com/
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Black Duck Software ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Black Duck Software.
 *
 *
 * API service for Fortify REST API
 * 
 * @author hsathe
 */
package com.blackducksoftware.integration.fortify.service;

import java.io.IOException;
import java.util.List;

import com.blackducksoftware.integration.fortify.batch.util.PropertyConstants;
import com.blackducksoftware.integration.fortify.model.CommitFortifyApplicationRequest;
import com.blackducksoftware.integration.fortify.model.CreateApplicationRequest;
import com.blackducksoftware.integration.fortify.model.CreateFortifyApplicationResponse;
import com.blackducksoftware.integration.fortify.model.FortifyApplicationResponse;
import com.blackducksoftware.integration.fortify.model.UpdateFortifyApplicationAttributesRequest;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class FortifyApplicationVersionApi extends FortifyService {

    private final static OkHttpClient.Builder okBuilder = getHeader(PropertyConstants.getProperty("fortify.username"),
            PropertyConstants.getProperty("fortify.password"));;

    private final static Retrofit retrofit = new Retrofit.Builder().baseUrl(PropertyConstants.getProperty("fortify.server.url"))
            .addConverterFactory(GsonConverterFactory.create()).client(okBuilder.build()).build();

    private final static FortifyApplicationVersionApiService apiService = retrofit.create(FortifyApplicationVersionApiService.class);

    public static FortifyApplicationResponse getApplicationVersionByName(String fields, String filter) throws IOException {
        Call<FortifyApplicationResponse> apiApplicationResponseCall = apiService.getApplicationVersionByName(fields, filter);
        FortifyApplicationResponse applicationAPIResponse = apiApplicationResponseCall.execute().body();
        return applicationAPIResponse;
    }

    public static int createApplicationVersion(CreateApplicationRequest request) throws IOException {
        Call<CreateFortifyApplicationResponse> apiApplicationResponseCall = apiService.createApplicationVersion(request);
        CreateFortifyApplicationResponse applicationAPIResponse = apiApplicationResponseCall.execute().body();
        return applicationAPIResponse.getData().getId();
    }

    public static int updateApplicationAttributes(int parentId, List<UpdateFortifyApplicationAttributesRequest> request) throws IOException {
        Call<ResponseBody> apiApplicationResponseCall = apiService.updateApplicationAttributes(parentId, request);
        int response = apiApplicationResponseCall.execute().code();
        return response;
    }

    public static int commitApplicationVersion(int id, CommitFortifyApplicationRequest request) throws IOException {
        Call<ResponseBody> apiApplicationResponseCall = apiService.commitApplicationVersion(id, request);
        int response = apiApplicationResponseCall.execute().code();
        return response;
    }
}
