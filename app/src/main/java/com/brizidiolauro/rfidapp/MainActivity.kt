package com.brizidiolauro.rfidapp

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val TAG = "MQTT"
    private val TOPIC = "dell"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var clientId = MqttClient.generateClientId()

        var client = MqttAndroidClient(this.applicationContext, "tcp://10.0.0.114:1883", clientId)
        client.connect().actionCallback = object : IMqttActionListener {

            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Toast.makeText(this@MainActivity,"Conectado",Toast.LENGTH_LONG).show()

                try{
                    client.subscribe(TOPIC,1)
                }
                catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Toast.makeText(this@MainActivity,"Falha",Toast.LENGTH_LONG).show()
            }
        }

       client.setCallback(object: MqttCallback{
           override fun connectionLost(cause: Throwable?) {
               TODO("Not yet implemented")
           }

           override fun messageArrived(topic: String?, message: MqttMessage?) {
               Toast.makeText(this@MainActivity,message?.payload?.decodeToString(),Toast.LENGTH_LONG).show()
           }

           override fun deliveryComplete(token: IMqttDeliveryToken?) {
               TODO("Not yet implemented")
           }
       })

    }
}