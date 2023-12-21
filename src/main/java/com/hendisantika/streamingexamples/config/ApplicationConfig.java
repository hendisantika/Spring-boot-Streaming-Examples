package com.hendisantika.streamingexamples.config;

import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by IntelliJ IDEA.
 * Project : Spring-boot-Streaming-Examples
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/21/23
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableFilesystemStores
public class ApplicationConfig {

    @Bean
    public File filesystemRoot() {
        try {
            return Files.createTempDirectory("").toFile();
        } catch (IOException ioe) {
        }
        return null;
    }

    @Bean
    public FileSystemResourceLoader fileSystemResourceLoader() {
        return new FileSystemResourceLoader(filesystemRoot().getAbsolutePath());
    }
}
