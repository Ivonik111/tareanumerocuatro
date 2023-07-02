package com.example.tareanumerocuatro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    public void login (View view){
        EditText usuario = (EditText)findViewById(R.id.editTextText);
        EditText contraseña = (EditText)findViewById(R.id.editTextTextPassword);

        String user, password;
        user= usuario.getText().toString();
        password=contraseña.getText().toString();

//DEPENDENCIA DE VOLLEY, DIAPOSITIVA
        RequestQueue queue = Volley.newRequestQueue(this);

        //WEBSERVICE PARA EL LOGIN
        String url ="https://api.uealecpeterson.net/public/login";


        JSONObject params = new JSONObject();
        try {
            params.put("correo", user);
            params.put("clave", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            // Extraer el token de la respuesta
                            String token = response.getString("access_token");



                            Bundle b =new Bundle();
                            b.putString("Token", token);
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtras(b);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                        // si el login es correcto
                        //Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                       // startActivity(intent);


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // mostrar mensaje de error si el login es incorrecto
                        TextView mensaje = (TextView)findViewById(R.id.textView7);
                        mensaje.setText("ACCESO EQUIVOCADO");

                    }
                });
        queue.add(request);





    }
}