package com.example.test345;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;


public class AddProductFragment extends Fragment
{
    private ImageView ProductImage;
    private ImageButton btnGoToMain;
    private EditText etProductName,etProductBrand,etProductInfo,etProductPrice;
    private Button btnAddProduct;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference requestsCollection;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public void Add() {
        ProductImage = getView().findViewById(R.id.ProductImage);
        btnGoToMain = getView().findViewById(R.id.btnGoToMain);
        etProductName = getView().findViewById(R.id.etProductName);
        etProductBrand = getView().findViewById(R.id.etProductBrand);
        etProductInfo = getView().findViewById(R.id.etProductInfo);
        etProductPrice = getView().findViewById(R.id.etProductPrice);
        btnAddProduct = getView().findViewById(R.id.btnAddProduct);
        btnGoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInFragment LogInFragment = new LogInFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.FrameLayout, LogInFragment, LogInFragment.getTag()).commit();
            }
        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                AddProduct(product);
            }
        });
    }
    private void AddProduct(Product product) {

      /*try{
                    db.collection("Products")
                            .add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getContext(), "DATA SAVED", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "something failed", Toast.LENGTH_SHORT).show();
                                    //try to save
                                    Log.e("", e.getMessage());
                                }});}
                catch (Exception ex){
            Log.e("",ex.getMessage());
        }
 */
        requestsCollection = FirebaseFirestore.getInstance().collection("Products");
        requestsCollection.add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "product added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "something failed", Toast.LENGTH_SHORT).show();
                    }});
    }
    public AddProductFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Add();
    }
}