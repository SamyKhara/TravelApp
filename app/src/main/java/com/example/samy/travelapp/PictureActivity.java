package com.example.samy.travelapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PictureActivity extends Fragment {

    View root;

    Button Camera;
    ImageView ImgView;
    Button Share;
    EditText Message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root != null && (ViewGroup) root.getParent() != null) {
            ((ViewGroup) root.getParent()).removeView(root);
            return root;
        }
        root = inflater.inflate(R.layout.picture_activity, container, false);

        Camera = (Button)root.findViewById(R.id.camerabtn);
        ImgView = (ImageView)root.findViewById(R.id.imgView);
        Share = (Button)root.findViewById(R.id.sharebtn);
        Message = (EditText)root.findViewById(R.id.msgtext);

        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("*/*");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString() + "/postcard.jpg")));
                share.putExtra(Intent.EXTRA_TEXT, Message.getText().toString());
                startActivity(Intent.createChooser(share,"Share via"));
            }
        });
        return root;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        ImgView.setImageBitmap(bitmap);
        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOutputStream = null;
        File file = new File(path + "/postcard.jpg");
        try {
            fOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOutputStream);
            fOutputStream.flush();
            fOutputStream.close();
            MediaStore.Images.Media.insertImage(getContext().getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Save Failed", Toast.LENGTH_SHORT).show();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Save Failed", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
