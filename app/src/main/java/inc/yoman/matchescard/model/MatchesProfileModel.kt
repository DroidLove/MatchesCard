package inc.yoman.matchescard.model

data class MatchesProfileModel(var gender: String = "") {

//    class Info {
//        var version: String = ""
//        var page: Int = 0
//        var results: Int = 0
//        var seed: String = ""
//    }

    var nat: String = ""
    lateinit var picture: Picture
    lateinit var id: Id
    var cell: String = ""
    var phone: String = ""
    var registered: String = ""
    var dob: String = ""
    lateinit var login: Login
    var email: String = ""
    lateinit var location: Location
    lateinit var name: Name

    class Name {
        var last: String = ""
        var first: String = ""
        var title: String = ""
    }

    class Location {
        var postcode: Int = 0
        var state: String = ""
        var city: String = ""
        var street: String = ""
    }

    class Login {
        var sha256: String = ""
        var sha1: String = ""
        var md5: String = ""
        var salt: String = ""
        var password: String = ""
        var username: String = ""
    }

    class Id {
        var name: String = ""
    }

    class Picture {
        var thumbnail: String = ""
        var medium: String = ""
        var large: String = ""
    }
}
