package com.sparta.infraestructure.repositories;

import com.sparta.domain.Record;
import com.sparta.domain.RecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RecordRepositoryImplTest {

  RecordRepository recordRepository;

  @BeforeEach
  void setupTest() {
    recordRepository = new RecordRepositoryImpl();
  }

  @Test
  void testSaveAll() {
    recordRepository.saveAllByProvider("provider1", Arrays.asList(
      new Record(1L, System.currentTimeMillis(), "Valencia", Collections.emptyList())
    ));
    recordRepository.saveAllByProvider("provider2", Arrays.asList(
      new Record(1L, System.currentTimeMillis(), "Geneva", Collections.emptyList()),
      new Record(2L, System.currentTimeMillis(), "Madrid", Collections.emptyList())
    ));
    recordRepository.saveAllByProvider("provider2", Arrays.asList(
      new Record(3L, System.currentTimeMillis(), "Madrid", Collections.emptyList())
    ));

    List<Record> recordsProvider1 = recordRepository.getAllByProvider("provider1");
    assertThat(recordsProvider1).hasSize(1);
    assertThat(recordsProvider1.get(0).getRecordIndex()).isEqualTo(1L);
    List<Record> recordsProvider2 = recordRepository.getAllByProvider("provider2");
    assertThat(recordsProvider2).hasSize(3);
    assertThat(recordsProvider2.get(0).getRecordIndex()).isEqualTo(1L);
    assertThat(recordsProvider2.get(1).getRecordIndex()).isEqualTo(2L);
    assertThat(recordsProvider2.get(2).getRecordIndex()).isEqualTo(3L);
  }

  @Test
  void testCount() {
    recordRepository.saveAllByProvider("provider1", Arrays.asList(
      new Record(1L, System.currentTimeMillis(), "Valencia", Collections.emptyList())
    ));
    recordRepository.saveAllByProvider("provider2", Arrays.asList(
      new Record(1L, System.currentTimeMillis(), "Geneva", Collections.emptyList()),
      new Record(2L, System.currentTimeMillis(), "Madrid", Collections.emptyList())
    ));
    recordRepository.saveAllByProvider("provider2", Arrays.asList(
      new Record(3L, System.currentTimeMillis(), "Madrid", Collections.emptyList())
    ));

    assertThat(recordRepository.getAllByProvider("provider1")).hasSize(1);
    assertThat(recordRepository.getAllByProvider("provider2")).hasSize(3);
  }

}