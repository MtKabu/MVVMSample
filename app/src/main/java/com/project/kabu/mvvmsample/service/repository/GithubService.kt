package com.project.kabu.mvvmsample.service.repository

import com.project.kabu.mvvmsample.service.model.Readme
import com.project.kabu.mvvmsample.service.model.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * github接続用インターフェース(retrofitを使用)
 */
interface GithubService {

    //Retrofitインターフェース(APIリクエストを管理)
    companion object {
        val HTTPS_API_GITHUB_URL = "https://api.github.com/"
    }

    /** ユーザーのリポジトリー一覧を取得 */
    @GET("/users/{user}/repos")
    fun getRepo(
            @Path("user") user : String
    ): Call<ArrayList<Repo>>

    /** リポジトリーのreadmeを取得 */
    @GET("/repos/{user}/{repo}/readme")
    fun getReadme(
            @Path("user") user : String,
            @Path("repo") repo : String
    ): Call<Readme>

}