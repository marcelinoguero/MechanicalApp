package com.tcc.mechanicalapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class EditProfileDataActivity extends AppCompatActivity implements PasswordChangeDialog.PasswordChangeDialogListener, View.OnClickListener {

    private String newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_data);

        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        EditText nameText = findViewById(R.id.name);
        EditText loginText = findViewById(R.id.login);

        nameText.setText(Constants.getInstance().getUserName());
        loginText.setText(Constants.getInstance().getUserLogin());

        Button btn1 = findViewById(R.id.changePasswordBtn);
        btn1.setOnClickListener(this);

        Button btn2 = findViewById(R.id.save_btn);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.changePasswordBtn) {
            openDialog();
        }

        if (v.getId() == R.id.save_btn) {
            EditText nameField = findViewById(R.id.name);
            EditText loginField = findViewById(R.id.login);

            String newName = nameField.getText().toString();
            String newLogin = loginField.getText().toString();
            if (newPassword == null) {
                newPassword = Constants.getInstance().getPassword();
            }

            if (!newName.equals("") && !newLogin.equals("")) {

                AsyncHttpClient client = new AsyncHttpClient();

                JSONObject jsonParams = new JSONObject();
                try {
                    jsonParams.put("name", newName);
                    jsonParams.put("login", newLogin);
                    jsonParams.put("password", newPassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StringEntity entity = null;
                entity = new StringEntity(jsonParams.toString(), "UTF-8");

                Context context = this.getApplicationContext();
                String server = Constants.getInstance().getServerAddress();
                String userType = Constants.getInstance().getUserType();
                String userId = Constants.getInstance().getUserId();
                client.put(context, server + "/users/editprofile/" + userType + "/" + userId, entity, "application/json", new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        Toast toast = Toast.makeText(context, "Perfil atualizado com sucesso", Toast.LENGTH_SHORT);
                        toast.show();

                        Constants.getInstance().setUserLogin(newLogin);
                        Constants.getInstance().setPassword(newPassword);
                        Constants.getInstance().setUserName(newName);
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        Log.d("Status", statusCode+"");
                        Log.d("URL",server + "/users/editprofile/" + userType + "/" + userId);
                        Toast toast = Toast.makeText(context, "Erro ao atualizar o perfil", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onRetry(int retryNo) {

                    }
                });
            } else {
                Toast toast = Toast.makeText(this, "Preencha todos os campos antes de salvar", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void openDialog() {
        PasswordChangeDialog passwordChangeDialog = new PasswordChangeDialog();
        passwordChangeDialog.show(getSupportFragmentManager(), "passwordChange");
    }

    @Override
    public void applyTexts(String password) {
        newPassword = password;
    }
}