package com.hendisantika.streamingexamples.config;

import org.springframework.content.commons.repository.Store;
import org.springframework.content.rest.StoreRestResource;

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
@StoreRestResource(path = "/audios")
public interface AudioStore extends Store<String> {
}
