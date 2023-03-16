package com.ob.permission;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.ob.permission.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Button btnReqPer = binding.btnReqPer;

        btnReqPer.setOnClickListener(view1 -> {
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Snackbar.make(view1, "Permission Needed For Gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", view2 -> permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)).show();
                }
                else {
                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }
            else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(galleryIntent);
            }
        });

        registerLauncher();
    }

    private void registerLauncher() {

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK) {
                Intent intentResult = result.getData();

                if(intentResult!=null) {
                    Uri selectedPicture = intentResult.getData();
                    Toast.makeText(this, selectedPicture.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if(result) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(galleryIntent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Permission Needed", Toast.LENGTH_LONG).show();
            }
        });
    }
}