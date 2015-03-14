package com.huertix.turistguide;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoCaptureExample extends Activity implements OnTouchListener
{
        protected Button _button;
        protected ImageView image;
        protected TextView field;
        protected String path;
        protected boolean taken;
        protected Bitmap bitmap;
        protected AlertDialog alert;
       
        protected static final String PHOTO_TAKEN       = "photo_taken";
               
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.main);
       
        //compose and create our popup window
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Testing")
               .setCancelable(false)
               .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                   }
               });
        alert = builder.create();
       
        image = (ImageView) findViewById(R.id.image);
        field = (TextView) findViewById(R.id.field);
        _button = (Button) findViewById(R.id.button);
        //_button.setOnClickListener( new ButtonClickHandler() );
        image.setOnTouchListener(this);
       
       
        path = Environment.getExternalStorageDirectory() + "/images/make_machine_example.jpg";
    }
   
    /**
    public class ButtonClickHandler implements View.OnClickListener
    {
        public void onClick( View view ){
                Log.i("MakeMachine", "ButtonClickHandler.onClick()" );
                startCameraActivity();
        }
    }
    */
   
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
           startCameraActivity();
         return true;
       }
       return false;
    }
   
    protected void startCameraActivity()
    {
        Log.i("MakeMachine", "startCameraActivity()" );
        File file = new File(path);
        Uri outputFileUri = Uri.fromFile(file);
       
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
       
        startActivityForResult(intent, 0);
    }
   
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {  
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch(resultCode)
        {
                case 0:
                        Log.i("MakeMachine", "User cancelled");
                        break;
                       
                case -1:
                        onPhotoTaken();
                        break;
        }
    }
   
    protected void onPhotoTaken()
    {
        Log.i("MakeMachine", "onPhotoTaken");
       
        taken = true;
       
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
       
        bitmap = BitmapFactory.decodeFile(path, options);
       
        image.setImageBitmap(bitmap);
       
        field.setVisibility(View.GONE);
    }
   
    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState){
        Log.i("MakeMachine", "onRestoreInstanceState()");
        if( savedInstanceState.getBoolean(PhotoCaptureExample.PHOTO_TAKEN)) {
                onPhotoTaken();
        }
    }
   
    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        outState.putBoolean(PhotoCaptureExample.PHOTO_TAKEN, taken);
    }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
                Log.i("MakeMachine", "onTouch()");
                if(bitmap != null & taken == true){
                        Log.i("MakeMachine", "bitmap is not null and taken is true");
                        int color = bitmap.getPixel((int)event.getX(), (int)event.getY());
                       
                        int red = Color.red(color);
                        int green = Color.green(color);
                        int blue = Color.blue(color);
                       
                        Log.i("MakeMachine", "red: "+red+"// green: "+green+"// blue: "+blue);
                        return true;

                }
                return false;
        }
}
