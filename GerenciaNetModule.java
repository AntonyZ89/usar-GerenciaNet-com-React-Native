package com.fooddelivery_pro;

import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.gson.Gson;

import javax.annotation.Nonnull;

import br.com.gerencianet.gnsdk.config.Config;
import br.com.gerencianet.gnsdk.config.Constants;
import br.com.gerencianet.gnsdk.interfaces.IGnListener;
import br.com.gerencianet.gnsdk.lib.Endpoints;
import br.com.gerencianet.gnsdk.models.CreditCard;
import br.com.gerencianet.gnsdk.models.Error;
import br.com.gerencianet.gnsdk.models.PaymentData;
import br.com.gerencianet.gnsdk.models.PaymentToken;

public class GerenciaNetModule extends ReactContextBaseJavaModule {

    GerenciaNetModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @Nonnull
    public String getName() {
        return "GerenciaNet";
    }

    @ReactMethod
    public void getPaymentToken(String brand, String cvv, String number, String expiration_month, String expiration_year, Callback callback) {
        Config config = new Config();
        config.setAccountCode("ACCOUNT_CODE"); // localizado em https://sistema.gerencianet.com.br/minha-conta/validacoes em "Identificador de Conta"
        config.setSandbox(true); // Troque para False quando usado em PRODUÇÃO

        CreditCard creditCard = new CreditCard();
        creditCard.setBrand(brand);
        creditCard.setCvv(cvv);
        creditCard.setNumber(number);
        creditCard.setExpirationMonth(expiration_month);
        creditCard.setExpirationYear(expiration_year);

        Endpoints gnEndpoints = new Endpoints(config, new IGnListener() {
            @Override
            public void onInstallmentsFetched(PaymentData paymentData) {
                callback.invoke(new Gson().toJson(paymentData));
            }

            @Override
            public void onPaymentTokenFetched(PaymentToken paymentToken) {
                callback.invoke(paymentToken.getHash());
            }

            @Override
            public void onError(Error error) {
                callback.invoke(new Gson().toJson(error));
            }
        });

        gnEndpoints.getPaymentToken(creditCard);
    }
}
