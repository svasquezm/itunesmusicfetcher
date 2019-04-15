package cl.svasquezm.itunesmusicfetcher.framework.models.retrofitmodels

import cl.svasquezm.itunesmusicfetcher.domain.models.TrackModel

class TrackResultsList(
    var resultCount: Int,
    var results: List<TrackModel>
)