package com.example.courseprojectpu;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends DataBase {
    protected EditText editName, editPosition, editDescribe, editTel ,editEmail;
    protected Button btnInsert;
    protected ListView simpleList;

    protected void FillList() throws Exception{
        final ArrayList<String> listResults=new ArrayList<>();
        SelectSQL(
                "SELECT * FROM position ORDER BY Name;",
                null,
                new OnSelectElement() {
                    @Override
                    public void OnElementIterate(String Name, String Position, String Describe,String Tel, String Email, String ID) {
                        listResults.add(ID+ "\t"+Name+"  \t"+Position+" \t"+Describe+"  \t"+Tel+" \t"+Email+"\n");
                    }
                }
        );
        simpleList.clearChoices();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.activity_listview,
                R.id.textView,
                listResults
        );
        simpleList.setAdapter(arrayAdapter);

    }
    @Override
    @CallSuper
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        try {
            FillList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName=findViewById(R.id.editName);
        editPosition=findViewById(R.id.editPosition);
        editDescribe = findViewById(R.id.editDescribe);
        editTel = findViewById(R.id.editTel);
        editEmail=findViewById(R.id.editEmail);
        btnInsert=findViewById(R.id.button_add);
        simpleList=findViewById(R.id.simpleList);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView clickedText=view.findViewById(R.id.textView);
                String selected=clickedText.getText().toString();
                String[] elements=selected.split("\t");
                String ID=elements[0];
                String Name=elements[1];
                String Position=elements[2];
                String Describe=elements[3];
                String Tel=elements[4];
                String Email=elements[5];
                Intent intent=new Intent(MainActivity.this, UpdateDelete.class);
                Bundle b=new Bundle();
                b.putString("ID", ID);
                b.putString("Name", Name);
                b.putString("Position", Position);
                b.putString("Describe",Describe);
                b.putString("Tel",Tel);
                b.putString("Email", Email);
                intent.putExtras(b);
                startActivityForResult(intent, 200, b);

            }
        });


        try {
            initDb();
            FillList();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),
                    e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    ExecSQL("INSERT INTO position(Name, position,Describe ,Tel, Email)" +
                                    "VALUES(?, ?, ?, ?, ?)",
                            new Object[]{editName.getText().toString(),
                                    editPosition.getText().toString(),
                                    editDescribe.getText().toString(),
                                    editTel.getText().toString(),
                                    editEmail.getText().toString()
                            },
                            ()->{
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Записът е добавен",
                                        Toast.LENGTH_LONG
                                ).show();

                                try {
                                    FillList();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(),
                                            e.getMessage(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    e.printStackTrace();
                                }


                            }

                    );

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });




    }
}