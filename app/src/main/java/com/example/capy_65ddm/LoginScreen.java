package com.example.capy_65ddm;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import com.example.capy_65ddm.databinding.ActivityLoginScreenBinding;

public class LoginScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private boolean isVisible = false;

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();



        setContentView(R.layout.activity_login_screen);

        ImageView btn_eye =findViewById(R.id.btn_eye);

        Button btn_login = findViewById(R.id.btn_enviar);

        EditText emailLogin = findViewById(R.id.txtEmailLogin);
        EditText edt_senha = findViewById(R.id.txtSenhaLogin);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailLogin.getText().toString();
                password = edt_senha.getText().toString();

                if(email.equals("") || password.equals("")){
                    Toast.makeText(LoginScreen.this, "Preencha os campos",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Intent in = new Intent(LoginScreen.this, HomeScreen.class);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginScreen.this);
                                    alert.setTitle("Novo usuário");
                                    alert.setMessage("Parece que você é novo no aplicativo, deseja criar uma conta com as credenciais digitadas?");
                                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            startActivity(in);
                                            finish();
                                        }
                                    });
                                    alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            user.delete();
                                            dialogInterface.cancel();
//                                            finish();
                                        }
                                    });

                                    alert.show();


                                } else {
                                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                Intent in = new Intent(LoginScreen.this, HomeScreen.class);
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                startActivity(in);
                                                finish();
                                            }else{
                                                Toast.makeText(LoginScreen.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
            }
        });

        btn_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isVisible == false) {
                    btn_eye.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    edt_senha.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    setVisible(true);
                    edt_senha.setSelection(edt_senha.length());
                } else{
                    btn_eye.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                    edt_senha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edt_senha.setSelection(edt_senha.length());
                    setVisible(false);
                }
            }
        });

    }
}