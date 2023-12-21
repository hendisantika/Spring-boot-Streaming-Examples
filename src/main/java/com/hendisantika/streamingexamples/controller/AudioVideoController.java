package com.hendisantika.streamingexamples.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : Spring-boot-Streaming-Examples
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/21/23
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/audiovideo")
public class AudioVideoController {
    public static final String VIDEO_PATH = "/static/videos";
    public static final String AUDIO_PATH = "/static/audios";

    public static final int BYTE_RANGE = 128; // increase the byterange from here
}
