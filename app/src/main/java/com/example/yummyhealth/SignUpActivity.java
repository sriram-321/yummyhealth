package com.example.yummyhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText txtFullName, txtusername, txtemail, txtpassword, txtMobileNumber;
    RadioButton customerButton, riderButton, regularButton, irregularButton;
    Button SignUp;
    RadioGroup CustomerTypeGroup;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog1;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtemail = (EditText) findViewById(R.id.EmailEditText);
        txtFullName = (EditText) findViewById(R.id.NameEditText);
        txtusername = (EditText) findViewById(R.id.UsernameEditText);
        txtMobileNumber = (EditText) findViewById(R.id.MobileNumberEditText);
        txtpassword = (EditText) findViewById(R.id.PasswordEditText);
        SignUp = (Button) findViewById(R.id.SignUpButton);
        customerButton = (RadioButton) findViewById(R.id.CustomerButton);
        riderButton = (RadioButton) findViewById(R.id.RiderButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        firebaseAuth = FirebaseAuth.getInstance();


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog1 = new ProgressDialog(SignUpActivity.this);
                progressDialog1.show();
                progressDialog1.setContentView(R.layout.progress_dialog);
                progressDialog1.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );

                final String email = txtemail.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();
                final String fullName = txtFullName.getText().toString();
                final String Username = txtusername.getText().toString();
                final String MobileNumber = txtMobileNumber.getText().toString();
                String Role = "";
                String CustomerType = "";

                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(SignUpActivity.this,  "Please Enter Full Name",Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(Username)) {
                    Toast.makeText(SignUpActivity.this,  "Please Enter Username",Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(MobileNumber)) {
                    Toast.makeText(SignUpActivity.this,  "Please Enter Username",Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUpActivity.this,  "Please Enter Email",Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this,  "Please Enter Password",Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();
                    return;
                }


                if (customerButton.isChecked() == false && riderButton.isChecked() == false) {
                    Toast.makeText(SignUpActivity.this,  "Please select either Customer or Rider",Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();
                    return;
                }
                if (customerButton.isChecked()) {
                    Role = "Customer";
                    CustomerType = "Irregular";

                } else if (riderButton.isChecked()) {
                    Role = "Rider";
                }

                final String finalRole = Role;
                final String finalCustomerType = CustomerType;
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    user details = new user(
                                            fullName,
                                            Username,
                                            MobileNumber,
                                            email,
                                            finalRole,
                                            finalCustomerType

                                    );

                                    FirebaseDatabase.getInstance().getReference("user")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(details).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            progressDialog1.dismiss();
                                        }
                                    });


                                } else {

                                    Toast.makeText(SignUpActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });


            }
        });
    }
}
