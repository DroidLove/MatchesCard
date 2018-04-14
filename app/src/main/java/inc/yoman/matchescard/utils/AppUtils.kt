package inc.yoman.matchescard.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

class AppUtils {
    companion object {
        fun logMe(tag: String, message: String) {
            Log.e(tag, message)
        }

        fun toastMessage(mContext: Context, message: String) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
        }
    }
}