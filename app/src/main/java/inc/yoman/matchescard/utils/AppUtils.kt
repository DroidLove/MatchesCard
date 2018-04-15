package inc.yoman.matchescard.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ParseException
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import inc.yoman.matchescard.R
import java.text.SimpleDateFormat
import java.util.*

class AppUtils {
    companion object {
        fun logMe(tag: String, message: String) {
            Log.e(tag, message)
        }

        fun toastMessage(mContext: Context, message: String) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
        }

        fun loadImageInImageView(url : String, imageView : ImageView, gender: String) {
            url?.let {
                if(!TextUtils.isEmpty(it)) {
                    if(TextUtils.equals("female", gender))
                         Picasso.get().load(url).placeholder(R.drawable.placeholder_female).into(imageView)
                    else
                        Picasso.get().load(url).placeholder(R.drawable.placeholder_male).into(imageView)
                }
            }
        }

        fun callIntent(activity: Activity, number: String) {
            if (!TextUtils.isEmpty(number)) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$number")
                activity.startActivity(intent)
            } else {
                toastMessage(activity, activity.getString(R.string.invalid_mobile_found))
            }
        }

        fun sendEmail(activity: Activity,emailId: String){
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", emailId, null))
            activity.startActivity(Intent.createChooser(emailIntent, "Send email"))
        }

        fun calculateUserAge(date: String): Int {
            var age = 0
            try {
                val format = "yyyy-MM-dd HH:mm:ss"
                val dateFormat = SimpleDateFormat(format, Locale.ENGLISH);

                val date1 = dateFormat.parse(date)
                val now = Calendar.getInstance()
                val dob = Calendar.getInstance()
                dob.time = date1

//                if (dob.after(now)) {
//                    throw IllegalArgumentException("Can't be born in the future")
//                }
                val year1 = now.get(Calendar.YEAR)
                val year2 = dob.get(Calendar.YEAR)
                age = year1 - year2
                val month1 = now.get(Calendar.MONTH)
                val month2 = dob.get(Calendar.MONTH)
                if (month2 > month1) {
                    age--
                } else if (month1 == month2) {
                    val day1 = now.get(Calendar.DAY_OF_MONTH)
                    val day2 = dob.get(Calendar.DAY_OF_MONTH)
                    if (day2 > day1) {
                        age--
                    }
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return age
        }
    }
}