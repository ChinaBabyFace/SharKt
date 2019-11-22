package com.sharkt.api

import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("/orgs/octokit/repos")
    fun getOctokitRepos(): Observable<BaseDo>

    @GET("/emojis")
    fun getEmojis():Observable<BaseDo>

}
