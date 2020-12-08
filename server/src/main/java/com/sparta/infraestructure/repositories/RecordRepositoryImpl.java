package com.sparta.infraestructure.repositories;

import com.sparta.domain.records.Record;
import com.sparta.domain.records.RecordRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RecordRepositoryImpl implements RecordRepository {

  private final Map<String, List<Record>> store = Collections.synchronizedMap(new HashMap<>());

  @Override
  public synchronized void saveAllByProvider(String provider, List<Record> records) {
    this.store.computeIfAbsent(provider, prov -> new ArrayList<>()).addAll(records);
  }

  @Override
  public List<Record> getAllByProvider(String provider) {
    return Collections.unmodifiableList(this.store.get(provider));
  }

  @Override
  public int getTotalByProvider(String provider) {
    List<Record> records = this.store.get(provider);
    return records != null ? records.size() : 0;
  }
}
