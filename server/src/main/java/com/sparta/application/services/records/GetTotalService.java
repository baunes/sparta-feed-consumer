package com.sparta.application.services.records;

import com.sparta.commons.Validation;
import com.sparta.domain.records.RecordRepository;
import org.springframework.stereotype.Service;

@Service
public class GetTotalService {

  private final RecordRepository recordRepository;

  public GetTotalService(RecordRepository recordRepository) {
    this.recordRepository = recordRepository;
  }

  public int execute(String provider) {
    Validation.checkNotNull(provider, "provider can not be null");
    return this.recordRepository.getTotalByProvider(provider);
  }

}
