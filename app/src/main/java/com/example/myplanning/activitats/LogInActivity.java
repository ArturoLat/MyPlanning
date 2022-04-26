package com.example.myplanning.activitats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myplanning.model.Llista.ErrorLogIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myplanning.R;
import com.example.myplanning.databinding.LogInLayoutBinding;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.model.DocumentCollections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends Fragment {

    private LogInLayoutBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LogInViewModel viewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = LogInLayoutBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(this).get(LogInViewModel.class);

        final Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(String string) {
                Toast toastsuma =
                        Toast.makeText(getContext(), string.toString(), Toast.LENGTH_SHORT);
                toastsuma.show();
            }
        };
        binding.buttonRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.navigate(R.id.action_boton_registre_en_LogIN);
            }
        });
        binding.buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = binding.editTextUsername.getText().toString();
                String passUser = binding.editTextPassword.getText().toString();

                DocumentReference docRef = db.collection("users").document(user);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String pass = (String) document.getData().get("password");
                                if(pass.equals(passUser)){
                                    viewModel.logInCorrecte();
                                    navController.navigate(R.id.action_logIn);

                                }else{
                                    //contrasenya diferent
                                    viewModel.contrasenyaDiferent();
                                }
                            } else {
                                //error no document asociat
                                viewModel.usuariInexistent();
                            }
                        } else {
                            //fall de descarrega
                        }
                    }
                });
            }
        });

        binding.offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // navController.navigate(R.id);
            }
        });

        viewModel.getRespuesta().observe(getActivity(), observer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
