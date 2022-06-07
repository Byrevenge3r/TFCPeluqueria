package com.dam.peluqueriacanina.mainActivity.tienda;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.comunicacion.ComunicacionProductos;
import com.dam.peluqueriacanina.dao.CestaDao;
import com.dam.peluqueriacanina.db.CestaDB;
import com.dam.peluqueriacanina.entity.Cesta;
import com.dam.peluqueriacanina.mainActivity.tienda.fragmentos.BorrarCantidadFragment;
import com.dam.peluqueriacanina.model.BotonBorrarProducto;
import com.dam.peluqueriacanina.utils.CarritoAdapter;
import com.dam.peluqueriacanina.utils.MostrarDatosTusCitasAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MostrarCompraActivity extends AppCompatActivity implements View.OnClickListener, ComunicacionProductos {
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    String TAG ="main";
    final int UPI_PAYMENT = 0;

    RecyclerView rv,rvBorrar;
    LinearLayoutManager llm;
    CarritoAdapter adapter;
    ArrayList<Cesta> listaCompra;
    CestaDB db;
    CestaDao dao;
    TextView tvPrecioTotal;
    Button btnPagar;
    MostrarDatosTusCitasAdapter adapterDetallesCita;
    BotonBorrarProducto boton;
    Cesta cesta;
    BorrarCantidadFragment borrar;
    Bundle bundle;
    PaymentsClient paymentsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dam.peluqueriacanina.R.layout.activity_mostrar_compra);

        Wallet.WalletOptions walletOptions =
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build();

        paymentsClient = Wallet.getPaymentsClient(this,walletOptions);

        db = CestaDB.getDatabase(this);
        dao = db.cestaDao();

        borrar = new BorrarCantidadFragment();

        bundle = new Bundle();

        tvPrecioTotal = findViewById(R.id.precioTotal);
        btnPagar = findViewById(R.id.btnPagar);
        btnPagar.setOnClickListener(this);
        listaCompra = new ArrayList<>();
        rv = findViewById(R.id.rvTusCompras);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(llm);

        boton = new BotonBorrarProducto();

        listaCompra = (ArrayList<Cesta>) dao.sacarTodo();
        adapterDetallesCita = new MostrarDatosTusCitasAdapter(boton.getBoton());
        calcularPrecioTotal();
        adapter = new CarritoAdapter(listaCompra);
        rv.setAdapter(adapter);

        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.llCompra:

                        rvBorrar = v.findViewById(R.id.rlContainerCompra).findViewById(R.id.rvBorrar);
                        rvBorrar.setLayoutManager(new LinearLayoutManager(v.findViewById(R.id.llCompra).getContext()));
                        rvBorrar.setAdapter(adapterDetallesCita);

                        adapterDetallesCita.setListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                cesta = listaCompra.get(rv.getChildAdapterPosition(v));

                                bundle.putParcelable("producto",cesta);
                                getSupportFragmentManager().setFragmentResult("key", bundle);
                                borrar.show(getSupportFragmentManager(),"Borrar");

                                calcularPrecioTotal();
                                adapter.setDatos(listaCompra);
                                rv.setAdapter(adapter);

                            }
                        });
                    break;
                }
            }
        });
    }

    private void calcularPrecioTotal() {
        int precioTotal = 0;
        for (int i = 0; i < listaCompra.size(); i++) {
            precioTotal += listaCompra.get(i).getPrecio() * listaCompra.get(i).getCantidad();
        }
        tvPrecioTotal.setText(getString(R.string.precio_total, String.valueOf(precioTotal)));
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnPagar)) {
            try {
                IsReadyToPayRequest readyToPayRequest = IsReadyToPayRequest.fromJson(baseConfigurationJson().toString());

                Task<Boolean> task=paymentsClient.isReadyToPay(readyToPayRequest);
                task.addOnCompleteListener(this,new OnCompleteListener<Boolean>(){
                    public void onComplete(@NonNull Task<Boolean> completeTask) {
                        if (completeTask.isSuccessful()) {
                            try {
                                showGooglePayButton(completeTask.getResult());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Handle the error accordingly
                        }
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showGooglePayButton(Boolean result) throws JSONException {
        if (result) {
            final JSONObject paymentRequestJson =baseConfigurationJson();
            paymentRequestJson.put("transactionInfo",new JSONObject()
                    .put("totalPrice","1")
                    .put("totalPriceStatus","FINAL")
                    .put("currencyCode","EUR"));
            paymentRequestJson.put("merchantInfo",new JSONObject()
                    .put("merchantId","01234567890123456789")
                    .put("merchantName","Example Merchant"));

            final PaymentDataRequest request=
                    PaymentDataRequest.fromJson(paymentRequestJson.toString());
        AutoResolveHelper.resolveTask(
            paymentsClient.loadPaymentData(request),
                    this,0);
        } else {

        }
    }


    private static JSONObject baseConfigurationJson() throws JSONException {
        return new JSONObject()
                .put("apiVersion", 2)
                .put("apiVersionMinor", 0)
                .put("allowed Payment Methods",
                        new JSONArray()
                                .put("AMEX")
                                .put("DISCOVER")
                                .put("INTERAC")
                                .put("JCB")
                                .put("MASTERCARD")
                                .put("MIR")
                                .put("VISA"));
    }




    @Override
    public void comunicacion(Cesta cesta) {
        listaCompra = (ArrayList<Cesta>) dao.sacarTodo();
        adapter.setDatos(listaCompra);
        rv.setAdapter(adapter);
        calcularPrecioTotal();

    }
}