package com.example.testdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText etName, etRollno;
    Spinner sprinnercourse;
    Button btnInsertData;

    DatabaseReference studenDbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etRollno = findViewById(R.id.etRolling);
        sprinnercourse = findViewById(R.id.sprinnerCourse);
        btnInsertData = findViewById(R.id.btnInsertData);

        studenDbref = FirebaseDatabase.getInstance().getReference().child("Students");
        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStudentData();
            }
        });

    }

    private void insertStudentData() {
        String name = etName.getText().toString();
        String rollno = etRollno.getText().toString();
        String course = sprinnercourse.getSelectedItem().toString();

        Students students = new Students(name,rollno,course);

        studenDbref.push().setValue(students);
        Toast.makeText(MainActivity.this, "Data Inserted",Toast.LENGTH_SHORT).show();
    }


}