//===============================================================================
// Microsoft FastTrack for Azure
// Azure Active Directory B2C Authentication Samples
//===============================================================================
// Copyright © Microsoft Corporation.  All rights reserved.
// THIS CODE AND INFORMATION IS PROVIDED "AS IS" WITHOUT WARRANTY
// OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT
// LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
// FITNESS FOR A PARTICULAR PURPOSE.
//===============================================================================
package com.example.firstandroidapp;

import android.app.Application;
import android.content.res.Configuration;

import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IMultipleAccountPublicClientApplication;

public class AppSubClass extends Application {
    private IAuthenticationResult authResult;
    private IMultipleAccountPublicClientApplication b2cApp;
    private static AppSubClass me;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        me = this;

        authResult = null;
        b2cApp = null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static AppSubClass getInstance() {
        return me;
    }

    public IAuthenticationResult getAuthResult() {
        return authResult;
    }

    public void setAuthResult (IAuthenticationResult authResult) {
        this.authResult = authResult;
    }

    public IMultipleAccountPublicClientApplication getPublicClient() {
        return b2cApp;
    }

    public void setPublicClient (IMultipleAccountPublicClientApplication app) {
        this.b2cApp = app;
    }
}
