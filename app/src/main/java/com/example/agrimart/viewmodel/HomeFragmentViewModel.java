package com.example.agrimart.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.agrimart.data.model.Category;
import com.example.agrimart.data.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class HomeFragmentViewModel extends ViewModel {
    public MutableLiveData<List<Category>> categories;
    public MutableLiveData<List<Product>> products;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<Boolean> loading;

    public static MutableLiveData<DocumentSnapshot> lastVisible;
    public HomeFragmentViewModel() {
        categories = new MutableLiveData<>();
        products = new MutableLiveData<>();
        loading=new MutableLiveData<>();
        lastVisible=new MutableLiveData<>();
    }

    public void getData() {
        db.collection("categories")
                .orderBy("id")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Category> categoryList = task.getResult().toObjects(Category.class);
                        categories.setValue(categoryList);
                    } else {
                        Log.e("HomeFragmentViewModel", "Error getting categories: ", task.getException());
                    }
                });
    }

    public void getProducts() {
        db.collection("products")
                .orderBy("product_id")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Product> productList = task.getResult().toObjects(Product.class);
                        products.setValue(productList);
                    } else {
                        Log.e("HomeFragmentViewModel", "Error getting products: ", task.getException());
                    }
                });
    }

    public void getFirstProducts(){
        MutableLiveData<List<Product>> firstProducts = new MutableLiveData<>();
        Query first= db.collection("products")
                .orderBy("product_id")
                .limit(10);

        first.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Product> productList = queryDocumentSnapshots.toObjects(Product.class);
                        products.setValue(productList);

                        lastVisible.setValue(queryDocumentSnapshots.getDocuments()
                                .get(queryDocumentSnapshots.size()-1));
                    }
                });
    }

    public void getMoreProducts(){
        Query next= db.collection("products")
                .orderBy("product_id")
                .startAfter(lastVisible)
                .limit(10);

        next.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Product> currentList = products.getValue();
                        List<Product> newList = queryDocumentSnapshots.toObjects(Product.class);

                        if(currentList!=null){
                            currentList.addAll(newList);
                            products.setValue(currentList);
                        }

                        if(!newList.isEmpty()){
                            lastVisible.setValue(queryDocumentSnapshots.getDocuments()
                                    .get(queryDocumentSnapshots.size()-1));
                        }
                    }
                });
    }


}

