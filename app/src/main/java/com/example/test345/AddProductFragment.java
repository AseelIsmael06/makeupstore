package com.example.test345;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddProductFragment extends Fragment
{
    private static final String TAG = "AddProductFragment";
    private EditText etName, etInfo;
    private Spinner spProduct;
    private Button btnAddProAddProduct;
    private ImageView ivPhoto;
    private EditText etPrice;
    private EditText etCompany;
    private StorageReference storageReference;
    private String refAfterSuccessfullUpload = null;
    private String downloadableURL = "";
    private FirebaseServices fbs;
    private void connectComponents() {
        etName = getView().findViewById(R.id.etProductNameAddProduct);
        spProduct= getView().findViewById(R.id.spProduct);
        etInfo = getView().findViewById(R.id.etdInfoAddProduct);
        ivPhoto = getView().findViewById(R.id.ivPhotoAddProduct);
        etPrice=getView().findViewById(R.id.etProPriceAddProduct);
        etCompany=getView().findViewById(R.id.etProductCompanyAddPro);
        btnAddProAddProduct=getView().findViewById(R.id.btnAddProAddProduct);
        fbs = FirebaseServices.getInstance();
        spProduct.setAdapter(new ArrayAdapter<ProductCat>(this, android.R.layout.simple_list_item_1, ProductCat.values()));    }
    public void add(View view) {
        // check if any field is empty
        String productName, proInfo, proCompany, proPhoto, proPrice;
        productName = etName.getText().toString();
        proInfo = etInfo.getText().toString();
        proPrice = etPrice.getText().toString();
        proCompany = etCompany.getText().toString();
        if (ivPhoto.getDrawable() == null)
            proPhoto = "no_image";
        else proPhoto = ivPhoto.getDrawable().toString();
        if (productName.trim().isEmpty() || proInfo.trim().isEmpty()
                || proPhoto.trim().isEmpty() || proCompany.trim().isEmpty())
        {
            Toast.makeText(getContext(), "SomeFieldsAreEmpty", Toast.LENGTH_SHORT).show();
            return;
        }
        btnAddProAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Product product = new Product(productName, proInfo, proCompany, proPhoto, proPrice);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("products")
                        .add(product)
                        .addOnSuccessListener(new  OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
        }}
}
