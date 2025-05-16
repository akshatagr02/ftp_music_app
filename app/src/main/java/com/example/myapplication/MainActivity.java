package com.example.myapplication;

import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_MEDIA_VIDEO;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    //private TextView connect;
    private  static String urip;
    private  static  String[] title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
       ActivityCompat.requestPermissions(this,
           new String[]{READ_MEDIA_IMAGES,READ_MEDIA_VIDEO}, PackageManager.PERMISSION_GRANTED
       );

    // connect = findViewById(R.id.connect);
        RecyclerView recyclerView = findViewById(R.id.Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));






        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
       ftp ftp = new ftp();
       ftp.ftp_setup();


        adapter adapter = new adapter(title, new adapter.click() {
            @Override
            public void onClick(String data) {
                Toast.makeText(MainActivity.this, "Loading..", Toast.LENGTH_SHORT).show();
    try {
    ftp.file(data);
    ftp.logout();
    }catch (Exception e){

    }

                Intent intent = new Intent(MainActivity.this,main_music.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title",data);
                intent.putExtra("fileuri",urip);
                startActivity(intent);

            }
        });

        recyclerView.setAdapter(adapter);







    }


    private class  ftp   {
            StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
            StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);

            FTPClient ftpClient = new FTPClient();
        private void ftp_setup() {
            try {
                ftpClient.connect("192.168.29.133");
                ftpClient.login("ftp-u", "akshat");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                ftpClient.sendCommand("OPTS UTF8 ON");

                title = ftpClient.listNames();





                // urip = file.getPath();
            } catch (Exception e) {
                //connect.setText(e.toString());

            }


        }

        void file(String title) throws IOException {
            File file = new File(storageVolume.getDirectory().getPath() + "/Download/" + System.currentTimeMillis() + ".mp4");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            boolean status = ftpClient.retrieveFile(title, fileOutputStream);
            //Toast.makeText(MainActivity.this, "this is status "+status, Toast.LENGTH_SHORT).show();
            urip = file.getPath();
            fileOutputStream.flush();
            fileOutputStream.close();

        }
        void logout() throws IOException {

            ftpClient.logout();
            ftpClient.disconnect();
        }

    }

}