package com.sparta.infraestructure.controllers;

import com.sparta.application.services.records.GetTotalService;
import com.sparta.application.services.records.LoadBatchService;
import com.sparta.domain.records.Record;
import com.sparta.infraestructure.mappers.ByteArrayToRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class MainController {

  private static final Logger logger = LoggerFactory.getLogger(MainController.class);

  private final LoadBatchService loadBatchService;
  private final GetTotalService getTotalService;

  public MainController(LoadBatchService loadBatchService, GetTotalService getTotalService) {
    this.loadBatchService = loadBatchService;
    this.getTotalService = getTotalService;
  }

  @PostMapping("/load/{provider}")
  public int load(@PathVariable("provider") String provider, @RequestBody byte[] content) throws IOException {
    Record[] records = ByteArrayToRecordMapper.toRecordArray(content);
    loadBatchService.execute(provider, Arrays.asList(records));
    logger.debug("Load Batch: Provider {}: {} records", provider, records.length);
    return records.length;
  }

  @GetMapping("/data/{provider}/total")
  public int total(@PathVariable("provider") String provider) {
    return getTotalService.execute(provider);
  }

}
