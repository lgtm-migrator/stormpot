package stormpot;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TimeBasedDeallocationRuleTest {

  private DeallocationRule<Poolable> createRule(int ttl) {
    return new TimeBasedDeallocationRule(ttl, TimeUnit.MILLISECONDS);
  }
  
  private SlotInfo<?> infoWithAge(final long ageMillis) {
    return new SlotInfo<Poolable>() {
      public long getAgeMillis() {
        return ageMillis;
      }

      @Override
      public long getClaimCount() {
        return 0;
      }

      @Override
      public Poolable getPoolable() {
        return null;
      }
    };
  }
  
  @Test(expected = IllegalArgumentException.class) public void
  timeUnitCannotBeNull() {
    new TimeBasedDeallocationRule(10, null);
  }
  
  @Test public void
  youngSlotsAreNotInvalid() {
    DeallocationRule<Poolable> rule = createRule(2);
    SlotInfo<?> info = infoWithAge(1);
    assertFalse(rule.isInvalid(info));
  }

  @Test public void
  slotsAtTheMaximumPermittedAgeAreNotInvalid() {
    DeallocationRule<Poolable> rule = createRule(2);
    SlotInfo<?> info = infoWithAge(2);
    assertFalse(rule.isInvalid(info));
  }
  
  @Test public void
  slotsOlderThanTheMaximumPermittedAgeAreInvalid() {
    DeallocationRule<Poolable> rule = createRule(2);
    SlotInfo<?> info = infoWithAge(3);
    assertTrue(rule.isInvalid(info));
  }
  
  @Test(expected = IllegalArgumentException.class) public void
  maxPermittedAgeCannotBeLessThanOne() {
    createRule(0);
  }
}