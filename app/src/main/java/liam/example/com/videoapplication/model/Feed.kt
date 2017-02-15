package liam.example.com.videoapplication.model

import android.net.Uri
import java.io.Serializable
import java.util.*

open class Feed : Serializable {

    var id: String? = null
    var data: DataMain? = null
    var type: String? = null

    fun getMainList(): List<ListMain>? {
        return (data!!.entries!!.list!!.asList())
    }

    fun getListOrderedByPosition(position: Int): List<Uri> {
        val list = getMainList()
        val uris: MutableList<Uri> = mutableListOf()

        for (i in list!!.indices) {
            val source = list[i].getVideoSource()
            uris.add(i, Uri.parse(source))
        }
        Collections.rotate(uris, -position)
        return uris
    }

}
