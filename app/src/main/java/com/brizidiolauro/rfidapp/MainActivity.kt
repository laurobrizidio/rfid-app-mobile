package com.brizidiolauro.rfidapp

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val TOPIC = "uea"
    private val URL = "tcp://10.0.0.175:1883"

    private val adapter: ParkingAdapter by lazy {
        ParkingAdapter()
    }

    private var vehicleList: MutableList<VehicleDTO> = mutableListOf()

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.list_vehicle)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        var clientId = MqttClient.generateClientId()
        var client = MqttAndroidClient(this.applicationContext, URL, clientId)
        connectMQTT(client)
        receiveCallbackMQTT(client, adapter)


    }

    private fun receiveCallbackMQTT(client: MqttAndroidClient, adapter: ParkingAdapter) {
        client.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                TODO("Not yet implemented")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {

                val message: String? = message?.payload?.decodeToString()
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()

                vehicleList.add(VehicleDTO(message, generatePlateByCode(message), isCodeAuthorized(message)))

                runOnUiThread {
                    adapter.submitList(vehicleList)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Toast.makeText(this@MainActivity, "deliveryComplete", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun connectMQTT(client: MqttAndroidClient) {
        client.connect().actionCallback = object : IMqttActionListener {

            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Toast.makeText(this@MainActivity, "Conectado", Toast.LENGTH_LONG).show()

                try {
                    client.subscribe(TOPIC, 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Toast.makeText(this@MainActivity, "Falha", Toast.LENGTH_LONG).show()
            }
        }
    }
}