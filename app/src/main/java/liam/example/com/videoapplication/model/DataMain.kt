package liam.example.com.videoapplication.model

import java.io.Serializable

class DataMain : Serializable {

    var entries: Entries? = null
    var entry_count: Int = 0
    var title: String? = null
    var description: String? = null
    var code: String? = null
}
