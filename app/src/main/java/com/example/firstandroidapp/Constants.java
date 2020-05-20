package com.example.firstandroidapp;

public class Constants {
    /* Azure AD b2c Configs */
    final static String AUTHORITY = "https://login.microsoftonline.com/tfp/%s/%s";
    final static String TENANT = "nimccollOrgTenant.onmicrosoft.com";
    final static String CLIENT_ID = "68bc4097-ea4a-4bb2-962f-ab5fdb6164ad";
    final static String SCOPES = "https://graph.microsoft.com/profile";
    final static String API_URL = "https://graph.microsoft.com";

    final static String SISU_POLICY = "B2C_1_SiUpIn";
    final static String EDIT_PROFILE_POLICY = "B2C_1_SiPe";
    final static String REDIRECT_URI = "msal68bc4097-ea4a-4bb2-962f-ab5fdb6164ad://auth";
}
