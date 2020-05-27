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

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.microsoft.identity.client.*;
import com.microsoft.identity.client.exception.MsalClientException;
import com.microsoft.identity.client.exception.MsalException;
import com.microsoft.identity.client.exception.MsalServiceException;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IMultipleAccountPublicClientApplication b2cApp;
    private String[] scopes;
    Button btnSignIn;
    TextView txtErrorMessage;
    AppSubClass state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        state = (AppSubClass) getApplicationContext();
        scopes = Constants.SCOPES.split("\\s+");

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSignInClicked(scopes);
            }
        });

        txtErrorMessage = (TextView) findViewById(R.id.txtErrorMessage);

        b2cApp = state.getPublicClient();
        if (b2cApp == null)
        {
            PublicClientApplication.createMultipleAccountPublicClientApplication(this.getApplicationContext(),
                    R.raw.auth_config_b2c,
                    new IPublicClientApplication.IMultipleAccountApplicationCreatedListener() {
                        @Override
                        public void onCreated(IMultipleAccountPublicClientApplication application) {
                            b2cApp = application;
                            state.setPublicClient((b2cApp));
                            b2cApp.getAccounts(new IPublicClientApplication.LoadAccountsCallback() {
                                @Override
                                public void onTaskCompleted(List<IAccount> result) {
                                    if (result.size() == 1)
                                    {
                                        // User is already logged in acquire token silently
                                        b2cApp.acquireTokenSilentAsync(scopes, result.get(0), Constants.AUTHORITY, getSilentAuthCallback());
                                    }
                                }

                                @Override
                                public void onError(MsalException exception) {
                                    txtErrorMessage.setText(exception.getMessage());
                                }
                            });
                        }

                        @Override
                        public void onError(MsalException exception) {
                            txtErrorMessage.setText(exception.getMessage());
                        }
                    });
        }

    }

    public Activity getActivity() {
        return this;
    }


    private void onSignInClicked(String[] scopes) {
        b2cApp.acquireToken(getActivity(), scopes, getAuthInteractiveCallback());
    }


    private AuthenticationCallback getAuthInteractiveCallback() {
        return new AuthenticationCallback() {
            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                /* Successfully got a token, redirect to the authenticated page */
                state.setAuthResult(authenticationResult);
                Intent intent =  new Intent(getActivity(), AuthenticatedActivity.class);
                startActivity(intent);
            }
            @Override
            public void onError(MsalException exception) {
                if (exception instanceof MsalClientException) {
                    // An exception from the client (MSAL)
                    txtErrorMessage.setText(exception.getMessage());
                } else if (exception instanceof MsalServiceException) {
                    // An exception from the server
                    txtErrorMessage.setText(exception.getMessage());
                }
            }
            @Override
            public void onCancel() {
                /* User canceled the authentication */
            }
        };
    }

    private SilentAuthenticationCallback getSilentAuthCallback() {
      return new SilentAuthenticationCallback() {
          @Override
          public void onSuccess(IAuthenticationResult authenticationResult) {
              /* Successfully got a token, redirect to the authenticated page */
              state.setAuthResult(authenticationResult);
              Intent intent =  new Intent(getActivity(), AuthenticatedActivity.class);
              startActivity(intent);
          }

          @Override
          public void onError(MsalException exception) {
              if (exception instanceof MsalClientException) {
                  // An exception from the client (MSAL)
                  txtErrorMessage.setText(exception.getMessage());
              } else if (exception instanceof MsalServiceException) {
                  // An exception from the server
                  txtErrorMessage.setText(exception.getMessage());
              }
          }
      };
    }
}
