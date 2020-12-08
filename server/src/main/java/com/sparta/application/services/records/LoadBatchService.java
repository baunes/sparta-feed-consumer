package com.sparta.application.services.records;

import com.sparta.commons.Validation;
import com.sparta.domain.records.Record;
import com.sparta.domain.records.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadBatchService {

  private final RecordRepository recordRepository;

  public LoadBatchService(RecordRepository recordRepository) {
    this.recordRepository = recordRepository;
  }

  public void execute(String provider, List<Record> records) {
    Validation.checkNotNull(provider, "provider can not be null");
    Validation.checkNotNull(records, "records can not be null");
    this.recordRepository.saveAllByProvider(provider, records);
  }

}
