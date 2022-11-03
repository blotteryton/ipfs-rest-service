package com.sslteam.blottery.ipfsrestapi.services.uploads;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

/**
 * author: Sergey Khaylov
 * email: info@proextlab.ru
 * org: ProextLab
 * site: https://proextlab.ru
 * created: 19.08.2022
 **/

public class LocalUploadFileServiceImpl implements UploadFileService {
    @Value("${local-file-path}")
    private String LOCAL_FILE_PATH;

    @Override
    public Mono<String> upload(Mono<FilePart> file) {
        return file
                .flatMap(filePart -> {
                    Path destPath = Path.of(LOCAL_FILE_PATH, filePart.filename());
                    filePart.transferTo(destPath).subscribe();
                    return Mono.just(destPath.toString());
                });
    }

    @Override
    public Mono<String> upload(Mono<FilePart> file, String filenameWithoutExt) {
        return file
                .flatMap(filePart -> {
                    Path destPath = Path.of(LOCAL_FILE_PATH, filenameWithoutExt + filePart.filename().substring(filePart.filename().indexOf(".")));
                    filePart.transferTo(destPath).subscribe();
                    return Mono.just(destPath.toString());
                });
    }

    @Override
    public Mono<String> upload(Flux<DataBuffer> content) {
        return Mono.error(new RuntimeException("Not implemented yet"));
    }
}
