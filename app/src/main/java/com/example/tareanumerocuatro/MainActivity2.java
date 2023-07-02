package com.example.tareanumerocuatro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        //mostrar token

        //TextView recado = (TextView)findViewById(R.id.textView8);
        //Bundle b=this.getIntent().getExtras();
        //recado.setText("Token"+b.getString("Token"));


    }
    public void mostrar (View view)
    {

        Bundle b=this.getIntent().getExtras();
        TextView recado = (TextView)findViewById(R.id.textView8);
        RequestQueue queue = Volley.newRequestQueue(this);
        //WEBSERVICE CON INFO DE LOS CLIENTES
        String url ="https://api.uealecpeterson.net/public/clientes/search";
        JSONObject jsonobject = new JSONObject();

        try {
            //parametro fuente=1 en el postman
            jsonobject.put("fuente", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonobject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //PARCIAR
                        //CODIGO PRACTICA 2

                        String llamar ="";
                        try {
                            JSONArray jsonLista = response.getJSONArray("clientes");
                            for(int i=0; i< jsonLista.length();i++) {
                                JSONObject cliente = jsonLista.getJSONObject(i);
                                llamar = llamar + cliente.getString("identificacion").toString()+
                                        " "+ cliente.getString("nombre").toString()+"\n";
                            }
                        } catch (JSONException ver) {
                            throw new RuntimeException(ver);
                        }
                        recado.setText(llamar);

                        //recado.setText("Respuesta"+"\n"+ response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        recado.setText("INCORRECTO");

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                //TOKEN
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer"+ " " + b.getString("Token"));
                return headers;
            }
        };

        queue.add(request);
                }

}