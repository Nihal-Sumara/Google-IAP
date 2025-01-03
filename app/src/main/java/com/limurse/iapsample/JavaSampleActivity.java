package com.limurse.iapsample;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.limurse.iap.DataWrappers;
import com.limurse.iap.IapConnector;
import com.limurse.iap.PurchaseServiceListener;
import com.limurse.iap.SubscriptionServiceListener;
import com.limurse.iapsample.databinding.ActivityMainBinding;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class JavaSampleActivity extends AppCompatActivity {
    MutableLiveData<Boolean> isBillingClientConnected  = new MutableLiveData<>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isBillingClientConnected.setValue(false);

        List<String> nonConsumablesList = Collections.singletonList("lifetime");
        List<String> consumablesList = Arrays.asList("base", "moderate", "quite", "plenty", "yearly");
        List<String> subsList = Collections.singletonList("subscription");

        IapConnector iapConnector = new IapConnector(
                this,
                nonConsumablesList,
                consumablesList,
                subsList,
                getString(R.string.licenseKey),
                true
        );


        iapConnector.addBillingClientConnectionListener((status, billingResponseCode) -> {
            Log.d("JSA", "This is the status: "+status+" and response code is: "+billingResponseCode);
            isBillingClientConnected.setValue(status);
        });

        iapConnector.addPurchaseListener(new PurchaseServiceListener() {
            public void onPricesUpdated(@NotNull Map iapKeyPrices) {

            }

            public void onProductPurchased(@NonNull DataWrappers.PurchaseInfo purchaseInfo) {
                if (purchaseInfo.getSku().equals("plenty")) {

                } else if (purchaseInfo.getSku().equals("yearly")) {

                } else if (purchaseInfo.getSku().equals("moderate")) {

                } else if (purchaseInfo.getSku().equals("base")) {

                } else if (purchaseInfo.getSku().equals("quite")) {

                }
            }

            public void onProductRestored(@NonNull DataWrappers.PurchaseInfo purchaseInfo) {

            }

            @Override
            public void onPurchaseFailed(@Nullable DataWrappers.PurchaseInfo purchaseInfo, @Nullable Integer billingResponseCode) {
                Toast.makeText(getApplicationContext(), "Your purchase has been failed", Toast.LENGTH_SHORT).show();
            }
        });
        iapConnector.addSubscriptionListener(new SubscriptionServiceListener() {
            public void onSubscriptionRestored(@NonNull DataWrappers.PurchaseInfo purchaseInfo) {
            }

            public void onSubscriptionPurchased(@NonNull DataWrappers.PurchaseInfo purchaseInfo) {
                if (purchaseInfo.getSku().equals("subscription")) {

                }
            }

            public void onPricesUpdated(@NotNull Map iapKeyPrices) {

            }

            @Override
            public void onPurchaseFailed(@Nullable DataWrappers.PurchaseInfo purchaseInfo, @Nullable Integer billingResponseCode) {
            }
        });

        binding.btPurchaseCons.setOnClickListener(it ->
                iapConnector.purchase(this, "base", null, null)
        );

        binding.btnMonthly.setOnClickListener(it ->
                iapConnector.subscribe(this, "subscription", "", null, null)
        );

        binding.btnYearly.setOnClickListener(it ->
                iapConnector.subscribe(this, "yearly", "", null, null)
        );

        binding.btnQuite.setOnClickListener(it ->
                iapConnector.purchase(this, "quite", null, null)
        );

        binding.btnModerate.setOnClickListener(it ->
                iapConnector.purchase(this, "moderate", null, null)
        );

        binding.btnUltimate.setOnClickListener(it ->
                iapConnector.purchase(this, "plenty", null, null)
        );
    }
}
