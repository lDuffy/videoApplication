package liam.example.com.videoapplication.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Resources : Serializable {
    @SerializedName("video-thumbnail")
    var videoThumbnail: VideoThumbnail? = null

}
