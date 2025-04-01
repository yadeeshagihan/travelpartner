package com.tugaybakay.travelpartner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText phoneEditText;
    private Button signupButton;

    private DBManager dbManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbManager = new DBManager(this);


        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.mail);
        phoneEditText = findViewById(R.id.phone);
        passwordEditText = findViewById(R.id.password);
        signupButton = findViewById(R.id.SignupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isEmpty(firstNameEditText) || isEmpty(lastNameEditText) || isEmpty(usernameEditText)
                        || isEmpty(emailEditText) || isEmpty(phoneEditText) || isEmpty(passwordEditText)) {
                    Toast.makeText(getApplicationContext(), "Please fill all the Fields", Toast.LENGTH_LONG).show();
                    return; // Exit the onClick method if any field is empty
                }

                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Call DBManager to insert data
                long insertedId = dbManager.insertMember(firstName, lastName, username, email, phone, password);
                if (insertedId != -1) {
                    Toast.makeText(getApplicationContext(), "Successfully Inserted Data", Toast.LENGTH_SHORT).show();
                    Log.e("first", "Inserted");
                    dbManager.close(); // Close the database connection

                    // Start memberMenu activity
                    Intent login = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(login);
                } else {
                    Toast.makeText(getApplicationContext(), "Error in Sign Up Process", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Helper method to check if an EditText is empty
    private boolean isEmpty(EditText et) {
        return TextUtils.isEmpty(et.getText().toString().trim());
    }
}
