package com.example.projetofinalpart1.model

class FilmeApi (
    var title: String,
    var released: String,
    var runtime: String,
    var genre: String,
    var actors: String,
    var plot: String,
    var poster: String,
    var imdbRating: String,
    var imdbVotes: String,
    var imdbID: String,
    var type: String,
){
    override fun toString(): String {
        return "FilmeApi(title='$title', released='$released', runtime='$runtime', genre='$genre', actors='$actors', plot='$plot', poster='$poster', imdbRating='$imdbRating', imdbVotes='$imdbVotes', imdbID='$imdbID', type='$type')"
    }

}