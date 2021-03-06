package com.sharkt.api

import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("/orgs/octokit/repos")
    fun getOctokitRepos(): Observable<BaseDo<Any>>

    @GET("/emojis")
    fun getEmojis():Observable<BaseDo<Any>>

}
