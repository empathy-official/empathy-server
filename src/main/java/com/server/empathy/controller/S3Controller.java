package com.server.empathy.controller;

import com.server.empathy.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/s3test")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Uploader s3Uploader;

    @GetMapping("/upload")
    public String checkGet(){
        return "upload";
    }

    @PostMapping("/del")
    public void del(@RequestBody String objectURL){
        s3Uploader.deleteS3(objectURL);
    }

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3Uploader.upload(multipartFile, "filter");
    }

}
