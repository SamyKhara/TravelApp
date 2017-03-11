package com.example.samy.travelapp;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class HomepageActivity extends Fragment implements View.OnClickListener{

    View root;

    EditText name;
    EditText budget;
    EditText origin;
    Button nextPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (root != null && (ViewGroup) root.getParent() != null) {
            ((ViewGroup) root.getParent()).removeView(root);
            return root;
        }
        root = inflater.inflate(R.layout.login_page, container, false);

        name = (EditText)root.findViewById(R.id.nameText);
        budget = (EditText)root.findViewById(R.id.budgetText);
        origin = (EditText)root.findViewById(R.id.originText);
        nextPage = (Button)root.findViewById(R.id.nextActivityBtn);

        nextPage.setOnClickListener(this);

        if (ActivityCompat.checkSelfPermission(HomepageActivity.this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomepageActivity.this.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomepageActivity.this.getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        if (ActivityCompat.checkSelfPermission(HomepageActivity.this.getContext(), Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HomepageActivity.this.getActivity(), new String[]{
                    Manifest.permission.CAMERA},2);
        }
        if (ActivityCompat.checkSelfPermission(HomepageActivity.this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HomepageActivity.this.getActivity(), new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},3);
        }
        return root;
    }


    @Override
    public void onClick(View v) {
        try {
            MainActivity.Budget = Double.parseDouble(budget.getText().toString());

//        MainActivity.attractList.add("nothing");
//        MainActivity.attractList.add(origin.getText().toString());

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(),"Enter Budget!",Toast.LENGTH_LONG).show();
        }
        AttractionsList nextFrag = new AttractionsList();
        this.getFragmentManager().beginTransaction()
                .replace(this.getId(), nextFrag, null)
                .addToBackStack(null)
                .commit();
    }

}