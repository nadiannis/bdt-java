package com.nadiannis.asyncdemo.service;

import com.nadiannis.asyncdemo.model.GithubUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class GithubLookupService {

    @Autowired
    private RestTemplate restTemplate;

    @Async
    public CompletableFuture<GithubUser> findUser(String username) throws InterruptedException {
        log.info("Looking up " + username);
        String url = String.format("https://api.github.com/users/%s", username);
        GithubUser result = restTemplate.getForObject(url, GithubUser.class);

        // Artificial delay, just for demonstration purpose (to mimic real request to a server which usually have slow network)
        log.info("Delay 2s...");
        Thread.sleep(2000L);

        return CompletableFuture.completedFuture(result);
    }

    public GithubUser findUserSync(String username) throws InterruptedException {
        log.info("Looking up " + username);
        String url = String.format("https://api.github.com/users/%s", username);
        GithubUser result = restTemplate.getForObject(url, GithubUser.class);

        // Artificial delay, just for demonstration purpose (to mimic real request to a server which usually have slow network)
        log.info("Delay 2s...");
        Thread.sleep(2000L);

        return result;
    }

}
