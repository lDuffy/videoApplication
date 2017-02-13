package liam.example.com.videoapplication.model

import java.io.Serializable

class VideoData : Serializable {

    var duration: Double? = 0.0
    var source: String? = null
    var view_count: Int = 0
    var title: String? = null

}