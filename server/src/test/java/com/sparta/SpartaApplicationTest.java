package com.sparta;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpartaApplicationTest {

  @Autowired
  ApplicationContext context;

  @Test
  void contextLoads() {
    // Validate taht app runs Ok
    assertThat(context).isNotNull();
  }

}