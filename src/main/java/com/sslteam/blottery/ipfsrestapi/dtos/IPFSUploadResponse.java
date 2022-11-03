package com.sslteam.blottery.ipfsrestapi.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author: Sergey Khaylov
 * email: <a href="mailto:info@proextlab.ru">info@proextlab.ru</a>
 * org: ProextLab
 * site: <a href="https://proextlab.ru">ProextLab</a>
 **/

@Data
public class IPFSUploadResponse {

    @ApiModelProperty(value = "Хеш загруженного файла", example = "ajrfleskurhgaaijliecalrvbelkaruv")
    private String fileHash;

    @ApiModelProperty(value = "URL, по которому можно получить содержимое файла")
    private String url;

    public static IPFSUploadResponse of(String fileHash, String ipfsServerHost) {
        IPFSUploadResponse resp = new IPFSUploadResponse();
        resp.setFileHash(fileHash);
        resp.setUrl(IPFSUploadResponse.generateIPFSUrl(fileHash, ipfsServerHost));
        return resp;
    }

    public static String generateIPFSUrl(String fileHash, String ipfsServerHost) {
        return String.format("%s/ipfs/%s", ipfsServerHost, fileHash);
    }
}
