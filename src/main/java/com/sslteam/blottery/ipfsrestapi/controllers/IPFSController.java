package com.sslteam.blottery.ipfsrestapi.controllers;

import com.sslteam.blottery.ipfsrestapi.configurations.IPFSConfiguration;
import com.sslteam.blottery.ipfsrestapi.dtos.IPFSUploadResponse;
import com.sslteam.blottery.ipfsrestapi.services.uploads.UploadFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * author: Sergey Khaylov
 * email: <a href="mailto:info@proextlab.ru">info@proextlab.ru</a>
 * org: ProextLab
 * site: <a href="https://proextlab.ru">ProextLab</a>
 **/

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(tags = "File")
public class IPFSController {

    private final UploadFileService uploadFileService;
    private final IPFSConfiguration configuration;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Загрузка файла в IPFS", code = 201, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Успешная загрузка файла в IPFS", response = IPFSUploadResponse.class)
    })
    public Mono<IPFSUploadResponse> createByUser(@RequestPart Mono<FilePart> file) {
        return uploadFileService.upload(file)
                .map(fileHash -> IPFSUploadResponse.of(fileHash, configuration.getHost()));
    }

}
