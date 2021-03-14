package com.mertkavrayici.marvelkarakterlerideneme.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import com.mertkavrayici.marvelkarakterlerideneme.R
import com.squareup.picasso.Picasso
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
//Genel Fonksiyonları Burada Tanımladım.
fun getUrlImage(path:String, extension:String, type:String ):String{
    return "$path/$type.$extension"
}

fun getHash(ts: String): String {
    try {

        val md = MessageDigest.getInstance("MD5")

        //Dökümanda oluşturulan anahtar
        val messageDigest = md.digest(ts.toByteArray()
                + API_PRIVATE_KEY.toByteArray()
                + API_PUBLIC_KEY.toByteArray())

        val no = BigInteger(1, messageDigest)

        var hashtext = no.toString(16)
        while (hashtext.length < 32) {
            hashtext = "0$hashtext"
        }
        return hashtext
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException(e)
    }
}
//Uygulamanın sağlıklı olması açısından bir internet kontrol fonksiyonu ve SDK kontrolü yazılmalıydı.Aşağıdaki kod
//bloğunu oluşturdum ancak alıntı yapmak zorunda kaldım.Bu kısmı düzenlemeye çalışıyorum.
fun hasInternet(context: Context?): Boolean {
    val connectivityManager =
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        val network = connectivityManager?.activeNetwork
        val connection = connectivityManager?.getNetworkCapabilities(network)
        return connection != null && (
                connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    } else {
        val activeNetwork = connectivityManager?.activeNetworkInfo
        if (activeNetwork != null) {
            return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
        }
        return false
    }
}

fun loadImageView(image: ImageView, imageUrl: String?) {
    Picasso.get().load(imageUrl)
        .placeholder(R.drawable.ic_baseline_account_circle_24)
        .error(R.drawable.ic_baseline_account_circle_24)
        .into(image)
}