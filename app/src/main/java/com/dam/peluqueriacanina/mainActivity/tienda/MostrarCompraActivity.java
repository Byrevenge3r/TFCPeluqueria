package com.dam.peluqueriacanina.mainActivity.tienda;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.dam.peluqueriacanina.utils.pago.PaymentsUtil;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Optional;

public class MostrarCompraActivity extends AppCompatActivity implements View.OnClickListener, ComunicacionProductos {

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
    int precioTotalGoogle = 0;
    //Pagos
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    private static final long SHIPPING_COST_CENTS = 4 * PaymentsUtil.CENTS_IN_A_UNIT.longValue();

    // A client for interacting with the Google Pay API.
    private PaymentsClient paymentsClient;

    private View googlePayButton;

    private JSONArray garmentList;
    private JSONObject selectedGarment;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dam.peluqueriacanina.R.layout.activity_mostrar_compra);

        // Initialize a Google Pay API client for an environment suitable for testing.
        // It's recommended to create the PaymentsClient object inside of the onCreate method.
        paymentsClient = PaymentsUtil.createPaymentsClient(this);
        possiblyShowGooglePayButton();


        db = CestaDB.getDatabase(this);
        dao = db.cestaDao();

        borrar = new BorrarCantidadFragment();

        bundle = new Bundle();

        tvPrecioTotal = findViewById(R.id.precioTotal);
        googlePayButton = findViewById(R.id.googlePayButton);
        googlePayButton.setOnClickListener(this);

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
        precioTotalGoogle = precioTotal;
        tvPrecioTotal.setText(getString(R.string.precio_total, String.valueOf(precioTotal)));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v.equals(googlePayButton)) {
            requestPayment(v);
        }
    }



    @Override
    public void comunicacion(Cesta cesta) {
        listaCompra = (ArrayList<Cesta>) dao.sacarTodo();
        adapter.setDatos(listaCompra);
        rv.setAdapter(adapter);
        calcularPrecioTotal();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void possiblyShowGooglePayButton() {

        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!isReadyToPayJson.isPresent()) {
                return;
            }
        }

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        IsReadyToPayRequest request = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        }
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(this,
                new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            setGooglePayAvailable(task.getResult());
                        } else {
                            Log.w("isReadyToPay failed", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                }

                // Re-enables the Google Pay payment button.
                googlePayButton.setClickable(true);
        }
    }

    private void handlePaymentSuccess(PaymentData paymentData) {

        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        final String paymentInfo = paymentData.toJson();
        if (paymentInfo == null) {
            return;
        }

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".

            final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
            final String token = tokenizationData.getString("token");
            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            Toast.makeText(
                    this, getString(R.string.payments_show_name, billingName),
                    Toast.LENGTH_LONG).show();

            // Logging token string.
            Log.d("Google Pay token: ", token);

        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }

    private void handleError(int statusCode) {
        Log.e("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }

    private void setGooglePayAvailable(boolean available) {
        if (available) {
            googlePayButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, R.string.googlepay_status_unavailable, Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void requestPayment(View view) {

        // Disables the button to prevent multiple clicks.
        googlePayButton.setClickable(false);

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.

        long garmentPriceCents = Math.round(precioTotalGoogle * PaymentsUtil.CENTS_IN_A_UNIT.longValue());
        long priceCents = garmentPriceCents + SHIPPING_COST_CENTS;

        Optional<JSONObject> paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!paymentDataRequestJson.isPresent()) {
                return;
            }
        }

        PaymentDataRequest request =
                null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            request = PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());
        }

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        if (request != null) {
            AutoResolveHelper.resolveTask(
                    paymentsClient.loadPaymentData(request),
                    this, LOAD_PAYMENT_DATA_REQUEST_CODE);
        }

    }
}