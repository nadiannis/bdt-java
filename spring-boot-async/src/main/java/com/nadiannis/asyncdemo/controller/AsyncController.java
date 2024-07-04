package com.nadiannis.asyncdemo.controller;

import com.nadiannis.asyncdemo.model.GithubUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nadiannis.asyncdemo.service.GithubLookupService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class AsyncController {

    @Autowired
    private GithubLookupService service;

    @GetMapping("/users")
    public ResponseEntity<List<GithubUser>> findGithubUser() throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<GithubUser> page1 = service.findUser("nadiannis");
        CompletableFuture<GithubUser> page2 = service.findUser("melaniesfr");
        CompletableFuture<GithubUser> page3 = service.findUser("anekekarina99");

        // Wait until they are all done
        CompletableFuture.allOf(page1, page2).join();

        // Print results, including elapsed time
        log.info("Elapsed time: " + (System.currentTimeMillis() - start));
        log.info("--> " + page1.get());
        log.info("--> " + page2.get());
        log.info("--> " + page3.get());

        List<GithubUser> users = new ArrayList<>();
        users.add(page1.get());
        users.add(page2.get());
        users.add(page3.get());

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users-sync")
    public ResponseEntity<List<GithubUser>> findGithubUserSync() throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, synchronous lookups
        GithubUser page1 = service.findUserSync("nadiannis");
        GithubUser page2 = service.findUserSync("melaniesfr");
        GithubUser page3 = service.findUserSync("anekekarina99");

        // Print results, including elapsed time
        log.info("Elapsed time: " + (System.currentTimeMillis() - start));
        log.info("--> " + page1);
        log.info("--> " + page2);
        log.info("--> " + page3);

        List<GithubUser> users = new ArrayList<>();
        users.add(page1);
        users.add(page2);
        users.add(page3);

        return ResponseEntity.ok(users);
    }

}
