package com.schools.nycschools.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import java.lang.Exception
import androidx.core.content.ContextCompat.startActivity




class Utils {

    companion object {
        fun makeCall(phone: String, context: Context) {
            try {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + phone)
                context.startActivity(dialIntent)
            }catch (e:Exception){}
        }

        fun sendEmail(email: String, context: Context) {
            try {
                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact us:")
                context.startActivity(Intent.createChooser(emailIntent, "Chooser Email"))
            }catch (e:Exception){}
        }

        fun openUrl(url: String, context: Context) {
            try {
                var newUrl = url
                if(!url.startsWith("https") || !url.startsWith("http"))
                    newUrl = "https://${url}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newUrl))
                context.startActivity(intent)
            }catch (e:Exception){}
        }
    }
}