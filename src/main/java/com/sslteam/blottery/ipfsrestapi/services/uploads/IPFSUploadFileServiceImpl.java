package com.sslteam.blottery.ipfsrestapi.services.uploads;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * author: Sergey Khaylov
 * email: info@proextlab.ru
 * org: ProextLab
 * site: https://proextlab.ru
 * created: 19.08.2022
 **/

@Service
@RequiredArgsConstructor
public class IPFSUploadFileServiceImpl implements UploadFileService {
    private final WebClient ipfsWebClient;

    @Override
    public Mono<String> upload(Mono<FilePart> file) {
        return file
                .flatMap(filePart -> {
                    return ipfsWebClient
                            .post()
                            .uri("/add")
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .body(BodyInserters.fromMultipartAsyncData("file", filePart.content(), DataBuffer.class))
                            .retrieve()
                            .bodyToMono(Map.class)
                            .map(res -> (String) res.get("Hash"));
                });
    }

    @Override
    public Mono<String> upload(Mono<FilePart> file, String filenameWithoutExt) {
        return null;
    }

    @Override
    public Mono<String> upload(Flux<DataBuffer> content) {
        return ipfsWebClient
                .post()
                .uri("/add")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartAsyncData("file", content, DataBuffer.class))
                .retrieve()
                .bodyToMono(Map.class)
                .map(res -> (String) res.get("Hash"));
    }
}
