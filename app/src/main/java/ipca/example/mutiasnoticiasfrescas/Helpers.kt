package ipca.example.mutiasnoticiasfrescas

import java.text.SimpleDateFormat
import java.util.*

fun String.parsePubDate() : Date? {
    //2022-10-18T19:51:46Z
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return format.parse(this)
}

fun Date.toShort() : String {
    val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return format.format(this)
}

fun Date.toServerFormat() : String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return format.format(this)
}