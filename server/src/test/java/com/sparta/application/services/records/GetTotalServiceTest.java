package com.sparta.application.services.records;

import com.sparta.domain.records.RecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class GetTotalServiceTest {

  GetTotalService getTotalService;
  RecordRepository recodRepository;

  @BeforeEach
  void setupTest() {
    recodRepository = Mockito.mock(RecordRepository.class);
    getTotalService = new GetTotalService(recodRepository);
  }

  @Test
  void testExecuteWithProviderNull() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
      () -> getTotalService.execute(null));
    assertThat(ex.getMessage()).contains("provider can not be null");
  }

  @Test
  void testExecute() {
    String provider = "cusstom-provider";
    when(recodRepository.getTotalByProvider(provider)).thenReturn(1);

    int totalRecords = getTotalService.execute(provider);

    assertThat(totalRecords).isEqualTo(1);
  }

}