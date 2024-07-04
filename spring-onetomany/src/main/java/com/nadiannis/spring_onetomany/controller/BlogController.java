package com.nadiannis.spring_onetomany.controller;

import com.nadiannis.spring_onetomany.dto.BlogFormDto;
import com.nadiannis.spring_onetomany.model.Blog;
import com.nadiannis.spring_onetomany.model.Owner;
import com.nadiannis.spring_onetomany.service.BlogService;
import com.nadiannis.spring_onetomany.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private OwnerService ownerService;

    @Operation(summary = "Get all blogs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieving all blogs",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Blog.class)) }
            )
    })
    @GetMapping
    public List<Blog> getAll() {
        return blogService.findAll();
    }

    @Operation(summary = "Add a new blog")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Blog.class)) }
            )
    })
    @PostMapping
    public ResponseEntity<Blog> add(@RequestBody BlogFormDto body) {
        Blog blog = blogService.add(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(blog);
    }

    @Operation(summary = "Get a blog by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Blog.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Blog.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Blog.class))
            ),
            @ApiResponse(
                    description = "Gateway Timeout",
                    responseCode = "504",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Blog.class))
            ),
            @ApiResponse(
                    description = "Service Unavailable",
                    responseCode = "503",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Blog.class))
            ),
            @ApiResponse(
                    description = "Bad Gateway",
                    responseCode = "502",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Blog.class))
            )
    })
    @GetMapping("/{id}")
    public Blog getById(@PathVariable Long id) {
        return blogService.findById(id);
    }

}
