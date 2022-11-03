package com.sslteam.blottery.ipfsrestapi.services.uploads;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * author: Sergey Khaylov
 * email: info@proextlab.ru
 * org: ProextLab
 * site: https://proextlab.ru
 * created: 19.08.2022
 **/

public interface UploadFileService {
    Mono<String> upload(Mono<FilePart> file);

    Mono<String> upload(Mono<FilePart> file, String filenameWithoutExt);

    Mono<String> upload(Flux<DataBuffer> content);
}
