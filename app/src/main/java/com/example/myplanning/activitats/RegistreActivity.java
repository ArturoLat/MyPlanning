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
import androidx.navigation.fragment.NavHostFragment;

import com.example.myplanning.R;
import com.example.myplanning.databinding.LogInLayoutBinding;
import com.example.myplanning.databinding.RegistreLayoutBinding;
import com.example.myplanning.model.Llista.ComprobarDades;
import com.example.myplanning.model.Llista.ErrorRegistre;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RegistreActivity extends Fragment {

    private RegistreLayoutBinding binding;
    private ComprobarDades comprobarDades;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RegistreViewModel viewModel;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {

        binding = RegistreLayoutBinding.inflate(inflater, container, false);




        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        viewModel = new ViewModelProvider(this).get(RegistreViewModel.class);
        comprobarDades = new ComprobarDades();
        final Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(String string) {
                Toast toastsuma =
                        Toast.makeText(getContext(), string.toString(), Toast.LENGTH_SHORT);
                toastsuma.show();
            }
        };
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cridem al metode de registrar-se

                String mail = binding.registerMail.getText().toString();
                String user = binding.userRegister.getText().toString();
                String pass = binding.userPassRegister.getText().toString();
                String pass1 = binding.userPassRegister2.getText().toString();
                /*byte [] hash = new byte[] {};
                byte [] hash1 = new byte[] {};

                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    hash = md.digest(pass.getBytes(StandardCharsets.UTF_8));
                    hash1 = md.digest(pass1.getBytes(StandardCharsets.UTF_8));

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                if(Arrays.equals(hash, hash1)){
                    Map<String,Object> userData = new HashMap<>();
                    userData.put("mail",mail);
                    userData.put("password",hash.toString());
                    db.collection("users").document(user).set(userData);

                }*/

                if(pass.equals(pass1)){
                    String respuesta = comprobarDades.isSecure(pass, mail);
                    if(respuesta.equals("Format de l'email incorrecte")){

                        viewModel.emailIncorrecte();
                    }else if(respuesta.equals("Format de la contrasenya incorrecte")){
                        viewModel.contrasenyaIncorrecte();
                    }else{
                        Map<String,Object> userData = new HashMap<>();
                        userData.put("mail",mail);
                        userData.put("password",pass);
                        db.collection("users").document(user).set(userData);
                        viewModel.registreCorrecte();
                        viewModel.getRespuesta().observe(getActivity(), observer);
                        navController.navigate(R.id.action_boton_registre_en_Registre);
                    }
                }
                viewModel.getRespuesta().observe(getActivity(), observer);

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
