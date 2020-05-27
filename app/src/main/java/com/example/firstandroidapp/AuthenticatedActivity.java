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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.microsoft.identity.client.IMultipleAccountPublicClientApplication;
import com.microsoft.identity.client.exception.MsalException;

import java.util.Map;

public class AuthenticatedActivity extends AppCompatActivity {
    private IMultipleAccountPublicClientApplication b2cApp;
    Button btnSignOut;
    TextView txtUserGreeting;
    TextView txtErrorMessage;
    AppSubClass state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticated);

        btnSignOut = (Button) findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSignOutClicked();
            }
        });
        txtUserGreeting = (TextView) findViewById(R.id.txtUserGreeting);
        txtErrorMessage = (TextView) findViewById(R.id.txtErrorMessage);

        state = (AppSubClass) getApplicationContext();

        if (state.getAuthResult() != null) {
            Map<String, ?> claims = state.getAuthResult().getAccount().getClaims();
            String userName = (String) claims.get("name");
            txtUserGreeting.setText("Hello " + userName);
        }
        else {
            // User is not logged in return to the Sign In page
            finish();
        }
    }

    private void onSignOutClicked() {
        b2cApp = state.getPublicClient();
        if (b2cApp != null)
        {
            // Sign the user out from Azure AD B2C and return to the Sign In page
            b2cApp.removeAccount(state.getAuthResult().getAccount(), new IMultipleAccountPublicClientApplication.RemoveAccountCallback() {
                @Override
                public void onRemoved() {
                    state.setAuthResult(null);
                    finish();
                }

                @Override
                public void onError(@NonNull MsalException exception) {
                    txtErrorMessage.setText(exception.getMessage());
                }
            });
        }
    }
}
