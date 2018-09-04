package com.kokonut.test.raul.kokonuttest.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kokonut.test.raul.kokonuttest.R;
import com.kokonut.test.raul.kokonuttest.control.LoginController;
import com.kokonut.test.raul.kokonuttest.model.LoginRequest;
import com.kokonut.test.raul.kokonuttest.model.LoginResponse;

import butterknife.BindView;
import rx.Subscriber;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.input_user)
    protected EditText input_user;

    @BindView(R.id.input_password)
    protected EditText input_password;

    @BindView(R.id.loading_layer)
    protected View loading_layer;

    private LoginController controller;

    @Override
    void createView() {
     setContentView(R.layout.activity_login);
    }

    @Override
    void createController() {
        controller = new LoginController(this);
    }

    @Override
    void setupView() {
        input_password.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onLoginClick(null);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    void refresh() {

    }

    public void onLoginClick(View pView){
        LoginRequest request = new LoginRequest();
        request.setUsername(input_user.getText().toString());
        request.setPassword(input_password.getText().toString());
        loading_layer.setVisibility(View.VISIBLE);
        controller.login(request, new Subscriber<LoginResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(LoginActivity.this,
                                R.string.error_login,
                                Toast.LENGTH_SHORT).show();
                loading_layer.setVisibility(View.GONE);
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                if(loginResponse.getSuccess() == LoginResponse.SUCCESS ){
                    loginSuccess(loginResponse);
                }else {
                    (new AlertDialog.Builder(LoginActivity.this))
                            .setMessage(loginResponse.getMessage())
                            .setTitle(R.string.title_message)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                }
                loading_layer.setVisibility(View.GONE);
                input_password.setText("");
                input_user.setText("");
            }
        });
    }

    private void loginSuccess(LoginResponse response){
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(ListActivity.EXTRA_LOGIN_RESPONSE,getGson().toJson(response));
        startActivity(intent);
    }
}
