package pw.micmax.proxysms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        for (message in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            val phoneNumber = message.displayOriginatingAddress
            val text = message.displayMessageBody
            println("$phoneNumber: $text")
        }
    }
}