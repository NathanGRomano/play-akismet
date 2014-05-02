package com.manta.akismet;

import static org.junit.Assert.*;
import org.junit.Test;

public class KeyTest {

  @Test
  public void test () {
    String key = Key.get();
    Key.set("somekey");
    System.out.println(Key.get());
    assertTrue(Key.get().equals("somekey"));
    Key.set(key);
  }

}
