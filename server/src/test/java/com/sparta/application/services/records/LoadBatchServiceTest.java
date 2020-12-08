package com.sparta.application.services.records;

import com.sparta.domain.records.Record;
import com.sparta.domain.records.RecordRepository;
import com.sparta.domain.records.Sensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class LoadBatchServiceTest {

  LoadBatchService loadBatchService;
  RecordRepository recodRepository;

  @BeforeEach
  void setupTest() {
    recodRepository = Mockito.mock(RecordRepository.class);
    loadBatchService = new LoadBatchService(recodRepository);
  }

  @Test
  void testExecuteWithProviderNull() {
    final List<Record> records = Collections.emptyList();
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
      () -> loadBatchService.execute(null, records));
    assertThat(ex.getMessage()).contains("provider can not be null");
  }

  @Test
  void testExecuteWithRecordsWithNull() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
      () -> loadBatchService.execute("Provider", null));
    assertThat(ex.getMessage()).contains("records can not be null");
  }

  @Test
  void testExecuteWithRecords() {
    String provider = "custom-provider";
    List<Record> records = Arrays.asList(
      new Record(1L, System.currentTimeMillis(), "Geneva", Arrays.asList(
        new Sensor("1", 1001),
        new Sensor("2", 1002)
      )),
      new Record(2L, System.currentTimeMillis(), "Madrid", Arrays.asList(
        new Sensor("3", 2001),
        new Sensor("4", 3002)
      ))
    );

    loadBatchService.execute(provider, records);

    verify(recodRepository, times(1)).saveAllByProvider(provider, records);
  }

}