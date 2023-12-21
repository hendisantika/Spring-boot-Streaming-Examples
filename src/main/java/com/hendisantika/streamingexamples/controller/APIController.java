package com.hendisantika.streamingexamples.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hendisantika.streamingexamples.model.Student;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

import java.io.*;
import java.nio.file.Files;
import java.time.Duration;

/**
 * Created by IntelliJ IDEA.
 * Project : Spring-boot-Streaming-Examples
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/21/23
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
@RequestMapping("/api/stream")
@RestController
public class APIController {
    @GetMapping(value = "/data")
    public ResponseEntity<StreamingResponseBody> streamData() {
        StreamingResponseBody responseBody = response -> {
            for (int i = 1; i <= 1000; i++) {
                try {
                    Thread.sleep(10);
                    response.write(("Data stream line - " + i + "\n").getBytes());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }

    @GetMapping(value = "/data/flux", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Object> streamDataFlux() {
        return Flux.interval(Duration.ofSeconds(1)).map(i -> "Data stream line - " + i);
    }

    @GetMapping("/json")
    public ResponseEntity<StreamingResponseBody> streamJson() {
        int maxRecords = 1000;
        StreamingResponseBody responseBody = response -> {
            for (int i = 1; i <= maxRecords; i++) {
                Student st = new Student("Name" + i, i);
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(st) + "\n";
                response.write(jsonString.getBytes());
                response.flush();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(responseBody);
    }

    @GetMapping(value = "/json/flux", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Student> streamJsonObjects() {
        //return studentRepository.findAll();
        return Flux.interval(Duration.ofSeconds(1)).map(i -> new Student("Name" + i, i.intValue()));
    }

    @GetMapping("/textfile")
    public ResponseEntity<StreamingResponseBody> streamContentAsFile() {
        StreamingResponseBody responseBody = response -> {
            for (int i = 1; i <= 1000; i++) {
                response.write(("Data stream line - " + i + "\n").getBytes());
                response.flush();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test_data.txt")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody);
    }

    @GetMapping("/pdfFile")
    public ResponseEntity<StreamingResponseBody> streamPdfFile() throws FileNotFoundException {
        String fileName = "Technicalsand.com sample data.pdf";
        File file = ResourceUtils.getFile("classpath:static/" + fileName);
        StreamingResponseBody responseBody = outputStream -> {
            Files.copy(file.toPath(), outputStream);
        };
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Downloaded_" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(responseBody);
    }

    @GetMapping(value = "/csv")
    public ResponseEntity<StreamingResponseBody> getCsvFile() {
        StreamingResponseBody stream = output -> {
            Writer writer = new BufferedWriter(new OutputStreamWriter(output));
            writer.write("name,rollNo" + "\n");
            for (int i = 1; i <= 10000; i++) {
                Student st = new Student("Name" + i, i);
                writer.write(st.getName() + "," + st.getRollNo() + "\n");
                writer.flush();
            }
        };
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(stream);
    }
}
