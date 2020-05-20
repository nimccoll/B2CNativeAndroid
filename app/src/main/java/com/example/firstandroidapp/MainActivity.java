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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static IPublicClientApplication mPublicClientApp = null;
    private IMultipleAccountPublicClientApplication b2cApp;
    private String[] scopes;
    Button btnSignIn;
    TextView txtAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scopes = Constants.SCOPES.split("\\s+");

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSignInClicked(scopes);
            }
        });
        txtAccessToken = (TextView) findViewById(R.id.txtAccessToken);

        PublicClientApplication.createMultipleAccountPublicClientApplication(this.getApplicationContext(),
                R.raw.auth_config_b2c,
                new IPublicClientApplication.IMultipleAccountApplicationCreatedListener() {
                    @Override
                    public void onCreated(IMultipleAccountPublicClientApplication application) {
                        b2cApp = application;
                    }

                    @Override
                    public void onError(MsalException exception) {
                    }
                });
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
                /* Successfully got a token, use it to call a protected resource */
                String accessToken = authenticationResult.getAccessToken();
                txtAccessToken.setText(accessToken);
            }
            @Override
            public void onError(MsalException exception) {
                if (exception instanceof MsalClientException) {
                    //And exception from the client (MSAL)
                } else if (exception instanceof MsalServiceException) {
                    //An exception from the server
                }
            }
            @Override
            public void onCancel() {
                /* User canceled the authentication */
            }
        };
    }
}
