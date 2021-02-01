package com.example.tmaplocationtest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class CheckPermissionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 사용자 OS 버전 검사 -> 23 이상 버전인지 판단, 허가권 요청 정보 표시 O/X
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            int permissionCheck = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);

            //현재 어플리케이션이 권한에 대해 거부되었는지 확인
            if(permissionCheck== PackageManager.PERMISSION_DENIED){

                // 권한을 거부한 적이 있으면 true -> 최초실행 아님;
                // 권한을 거부한 적이 없으면 false -> 최초실행;
                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
                }else{
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
                }
            }else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1000){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        !=PackageManager.PERMISSION_DENIED){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(this,"권한 요청을 거부하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
