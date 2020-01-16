package pw.micmax.proxysms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

private const val CHUNK_SIZE = 160
private const val API_ENDPOINT = "https://www.google.com"

class SmsReceiver : BroadcastReceiver() {

    private val smsManager: SmsManager = SmsManager.getDefault()

    override fun onReceive(context: Context?, intent: Intent?) {
        for (message in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            val phoneNumber = message.displayOriginatingAddress
            val text = message.displayMessageBody
            println("$phoneNumber: $text")

            sendHttpAndReply(context, phoneNumber, text)
        }
    }

    private fun sendHttpAndReply(context: Context?, phoneNumber : String, text : String) {
        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(Request.Method.GET, API_ENDPOINT,
            Response.Listener<String> { response : String ->
                println("Response length: ${response.length}")
                // Alternative function: SmsManager.divideMessage(String)
                for (chunk in response.chunkedSequence(CHUNK_SIZE)) {
                    sendSms(phoneNumber, chunk)
                }
            },
            Response.ErrorListener {
                println("That didn't work!")
            }
        )

        queue.add(stringRequest)
    }

    private fun sendSms(phoneNumber : String, text : String) {
        // Alternative function: smsManager.sendTextMessageWithoutPersisting
        smsManager.sendTextMessage(phoneNumber, null, text, null, null)
    }
}