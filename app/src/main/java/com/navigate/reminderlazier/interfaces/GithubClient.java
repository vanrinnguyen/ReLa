package com.navigate.reminderlazier.interfaces;

import com.navigate.reminderlazier.models.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tony on 20/01/2018.
 */

public interface GithubClient {
    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(
            @Path("user") String user
    );
}
