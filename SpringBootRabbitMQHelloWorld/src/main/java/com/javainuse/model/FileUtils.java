package com.javainuse.model;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Service
public class FileUtils {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public List<MonitoredData> citesteFisier() throws IOException {

        String basePath = new File("").getAbsolutePath();
        System.out.println(basePath);
        return Files.lines(Paths.get(basePath+"\\activity.txt")).map(line -> line.split("\t\t", 3))
                .map(x -> {
                    MonitoredData md = new MonitoredData();
                    LocalDateTime startTime = LocalDateTime.parse(x[0], formatter);
                    md.setStartTime(startTime);
                    md.setStartDate(startTime.toLocalDate());
                    LocalDateTime endTime = LocalDateTime.parse(x[1], formatter);
                    md.setEndTime(endTime);
                    md.setEndDate(endTime.toLocalDate());
                    md.setActivity(x[2]);
                    return md;
                })
                .collect(Collectors.toList());
    }

}
