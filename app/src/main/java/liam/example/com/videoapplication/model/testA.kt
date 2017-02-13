package liam.example.com.videoapplication.model

import android.net.Uri
import java.util.*

/**
 * Created by lduf0001 on 12/02/2017.
 */

class testA {

    internal var feed = Feed()

    fun getList(position: Int): List<Uri> {
        val list = feed.getMainList()
        val uris = ArrayList<Uri>(list!!.size)

        for (i in list.indices) {
            val source = list[i].getVideoSource()
            uris[i] = Uri.parse(source)
        }
        Collections.rotate(uris, position)
        return uris

    }
}
