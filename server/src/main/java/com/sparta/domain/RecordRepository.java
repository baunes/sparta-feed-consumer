package com.sparta.domain;

import java.util.List;

public interface RecordRepository {

  /**
   * Save all records
   *
   * @param provider Provider
   * @param records  Records
   */
  void saveAllByProvider(String provider, List<Record> records);

  /**
   * Retrieves all the records of the procvider.
   *
   * @param provider Provider
   * @return Records of the provider
   */
  List<Record> getAllByProvider(String provider);

  /**
   * Retrieves the total records stores by provider
   *
   * @param provider Provider.
   * @return Total records stored.
   */
  int total(String provider);

}
