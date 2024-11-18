package com.example.justjordansapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cashapppay.sdk.* // Import the required Cash App Pay SDK classes

class MainActivity : AppCompatActivity(), CashAppPayListener {

    private lateinit var cashAppPay: CashAppPay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Cash App Pay SDK
        cashAppPay = CashAppPayFactory.createSandbox("sandboxClientID") // Replace with your sandbox client ID
        cashAppPay.registerForStateUpdates(this)

        // Create customer request
        createCustomerRequest()

        // Set up Cash App Pay Button
        findViewById<Button>(R.id.cashAppPayButton).setOnClickListener {
            cashAppPay.authorizeCustomerRequest()
        }
    }

    private fun createCustomerRequest() {
        val redirectUri = "cashapppay://checkout"
        val oneTimePayment = OneTimeAction(
            currency = Currency.USD,
            amount = 500, // $5.00 in cents
            scopeId = "YOUR_SCOPE_ID" // Replace with your actual scope ID
        )
        cashAppPay.createCustomerRequest(oneTimePayment, redirectUri)
    }

    override fun cashAppPayStateDidChange(newState: CashAppPayState) {
        when (newState) {
            is CashAppPayState.ReadyToAuthorize -> {
                findViewById<Button>(R.id.cashAppPayButton).isEnabled = true
            }
            is CashAppPayState.Approved -> {
                val grants = newState.grants
                sendGrantsToBackend(grants)
            }
            is CashAppPayState.Declined -> {
                Toast.makeText(this, "Payment declined", Toast.LENGTH_SHORT).show()
            }
            is CashAppPayState.CashAppPayExceptionState -> {
                Log.e("CashAppPay", "Error: ${newState.exception.message}")
            }
        }
    }

    private fun sendGrantsToBackend(grants: List<Grant>) {
        // TODO: Implement backend API call to process payment
        Log.d("CashAppPay", "Grants sent to backend: $grants")
    }

    override fun onDestroy() {
        super.onDestroy()
        cashAppPay.unregisterFromStateUpdates()
    }
}
