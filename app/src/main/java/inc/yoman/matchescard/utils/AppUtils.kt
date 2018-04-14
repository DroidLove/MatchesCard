package inc.yoman.matchescard.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import inc.yoman.matchescard.R



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
    }
}