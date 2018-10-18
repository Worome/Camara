package com.trianacodes.script.camara;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final String RUTA_IMAGEN = "imagenes/fotos";
    public String ruta;
    public ImageView imageAroma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        hacerFoto();
    }

    private void hacerFoto() {

        File archivoImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isExisteImagen = archivoImagen.exists();
        String nombreImagen="";
        Toast.makeText(this,"Inicio",Toast.LENGTH_LONG).show();
        if(!isExisteImagen){

            Toast.makeText(this,"No existe imagen",Toast.LENGTH_LONG).show();
            isExisteImagen = archivoImagen.mkdirs();

        } else {

            Toast.makeText(this,"Existe imagen",Toast.LENGTH_LONG).show();
            nombreImagen = (System.currentTimeMillis() / 1000) + ".jpg";

        }

        ruta = Environment.getExternalStorageDirectory() + File.separator + RUTA_IMAGEN +
                File.separator + nombreImagen;

        File imagenRuta = new File(ruta);
        Intent intent = null;
        //Toast.makeText(this,"Crea intent",Toast.LENGTH_LONG).show();
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            //Toast.makeText(this,"Si la versión es = o mayor que Nougat",Toast.LENGTH_LONG).show();
            String authorities = getApplicationContext().getPackageName()+".provider";
            Toast.makeText(this,"La autoridad es:" + authorities,Toast.LENGTH_LONG).show();
            Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), authorities, imagenRuta);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {

            Toast.makeText(this,"Si la versión es menor que Nougat",Toast.LENGTH_LONG).show();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagenRuta));

        }

        startActivityForResult(intent,20);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Toast.makeText(this,"En activityresult",Toast.LENGTH_LONG).show();
        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{ruta},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String s, Uri uri) {

                    }
                });

        // Asignamos la foto tomada a nuestro ImageView
        Toast.makeText(this,"Asignar imagen",Toast.LENGTH_LONG).show();
        Bitmap bitmap = BitmapFactory.decodeFile(ruta);
        imageAroma.setImageBitmap(bitmap);


    }
}
