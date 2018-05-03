package com.jenisk.realmcrudoprations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jenisk.realmcrudoprations.database.RealmHelper;
import com.jenisk.realmcrudoprations.model.DirectoryData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jenis Kasundra on 03/05/2018.
 */

public class UpdateDirectoryActivity extends AppCompatActivity {

    EditText edtName, edtNumber, edtAddress, edtLatitude, edtLongitude;
    Button btnUpdateDirectory;
    private RealmHelper realmHelper;
    private int id;
    private DirectoryData directoryData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_directory);

        init();
        bindView();
        addListner();
    }

    private void init() {
        getSupportActionBar().setTitle("Update Directory");
        realmHelper = new RealmHelper(this);
        Intent intent = getIntent();
        id=intent.getIntExtra("id",0);
        directoryData = realmHelper.getDirectory(id);
    }

    private void bindView() {
        edtName = (EditText) findViewById(R.id.edtName);
        edtNumber = (EditText) findViewById(R.id.edtNumber);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtLatitude = (EditText) findViewById(R.id.edtLatitude);
        edtLongitude = (EditText) findViewById(R.id.edtLongitude);
        btnUpdateDirectory = (Button) findViewById(R.id.btnAddDirectory);
        edtName.setText(directoryData.getName());
        edtNumber.setText(directoryData.getNumber());
        edtAddress.setText(directoryData.getAddress());
        edtLatitude.setText(directoryData.getLatitude());
        edtLongitude.setText(directoryData.getLongitude());
    }

    private void addListner() {

        btnUpdateDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edtName.getText().toString(),
                        number = edtNumber.getText().toString(),
                        address = edtAddress.getText().toString(),
                        latitude = edtLatitude.getText().toString(),
                        longitude = edtLongitude.getText().toString();

                if (name.equals("")) {
                    edtName.setError("Enter Name");
                } else if (!isValidMobile(number)) {
                    edtNumber.setError("Invalid Mobile Number");
                } else if (address.equals("")) {
                    edtAddress.setError("Enter Address");
                } else if (!isValidLatitude(latitude)) {
                    edtLatitude.setError("Invalid Latitude");
                } else if (!isValidLongitude(longitude)) {
                    edtLongitude.setError("Invalid Longitude");
                } else {
                    realmHelper.updateDirectory(id,name,number,address,latitude,longitude);

                    Toast.makeText(UpdateDirectoryActivity.this, "Directory Update Successfully", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });

    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isValidLatitude(String latitude) {
        String PATTERN = "^(\\+|-)?(?:90(?:(?:\\.0{1,7})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,7})?))$";

        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(latitude);
        return matcher.matches();
    }

    private boolean isValidLongitude(String longitude) {
        String PATTERN = "^(\\+|-)?(?:180(?:(?:\\.0{1,7})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,7})?))$";

        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(longitude);
        return matcher.matches();
    }

}
