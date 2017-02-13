package liam.example.com.videoapplication.model

import java.io.Serializable

class EntryData : Serializable {
    var thumbnail_image: String? = null
    var synopsis: String? = null
    var title: String? = null
    var parental_rating: String? = null
    var type: String? = null
    var video: Video? = null
    var profiles: Profiles? = null
    var resources: Resources? = null


}
