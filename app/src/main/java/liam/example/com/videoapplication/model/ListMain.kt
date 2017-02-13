package liam.example.com.videoapplication.model

import java.io.Serializable

class ListMain : Serializable {

    var id: String? = null
    var data: EntryData? = null
    var type: String? = null

    fun getVideoSource() : String? {
        return data!!.video!!.data!!.source
    }

}
