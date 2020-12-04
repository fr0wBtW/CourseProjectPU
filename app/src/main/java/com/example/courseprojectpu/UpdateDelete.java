package com.example.courseprojectpu;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDelete extends DataBase {

    protected String ID;
    protected EditText euName, euPosition,euDescribeOffer ,euTel, euEmail;
    protected Button btnUpdate, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        euName=findViewById(R.id.euName);
        euPosition=findViewById(R.id.euPosition);
        euDescribeOffer=findViewById(R.id.euDescribeOffer);
        euTel = findViewById(R.id.euTel);
        euEmail=findViewById(R.id.euEmail);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);
        Bundle b=getIntent().getExtras();
        if(b!=null){
            ID=b.getString("ID");
            euName.setText(b.getString("Name"));
            euPosition.setText(b.getString("Position"));
            euDescribeOffer.setText(b.getString("Describe"));
            euTel.setText(b.getString("Tel"));
            euEmail.setText(b.getString("Email"));
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ExecSQL(
                            "DELETE FROM Position WHERE ID=?",
                            new Object[]{
                                    ID
                            },
                            new OnQuerySuccess() {
                                @Override
                                public void OnSuccess() {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Записът е изтрит",
                                            Toast.LENGTH_LONG

                                    ).show();
                                }
                            }
                    );


                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }

                finishActivity(200);
                startActivity(new Intent(UpdateDelete.this,
                        MainActivity.class));
            }
        });


        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            ExecSQL(
                                    "UPDATE Position SET Name=?, Position=?, Describe=?,Tel=?, Email=?" +
                                            "where ID=? ",
                                    new Object[]{
                                            euName.getText().toString(),
                                            euPosition.getText().toString(),
                                            euDescribeOffer.getText().toString(),
                                            euTel.getText().toString(),
                                            euEmail.getText().toString(),
                                            ID
                                    },
                                    new OnQuerySuccess() {
                                        @Override
                                        public void OnSuccess() {
                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "Записът е променен.",
                                                    Toast.LENGTH_LONG

                                            ).show();
                                        }
                                    }
                            );


                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),
                                    e.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }

                        finishActivity(200);
                        startActivity(new Intent(UpdateDelete.this,
                                MainActivity.class));

                    }
                }
        );

    }
}