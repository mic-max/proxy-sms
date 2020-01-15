package pw.micmax.proxysms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        for (message in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            val phoneNumber = message.displayOriginatingAddress
            val text = message.displayMessageBody
            println("$phoneNumber: $text")

            sendHTTP(context)
        }
    }

    fun sendHTTP(context: Context?) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://www.google.com"

        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response : String ->
                println("Response is: ${response.substring(0, 500)}")
            },
            Response.ErrorListener {
                println("That didn't work!")
            }
        )
        
        queue.add(stringRequest)
    }
}