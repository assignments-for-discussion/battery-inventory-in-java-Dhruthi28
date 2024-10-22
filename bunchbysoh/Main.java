package bunchbysoh;

public class Main {
  static class CountsBySoH {
    public int healthy = 0;
    public int exchange = 0;
    public int failed = 0;
  };

  static CountsBySoH countBatteriesByHealth(int[] presentCapacities) {
    CountsBySoH counts = new CountsBySoH();
    //int ratedCapacity = 120;
    for(int capacity : presentCapacities){
      double soh = (100.0 * capacity)/120; //Direct use of ratedCapacity

      if(soh>83){
        counts.healthy++;
      } else if(soh>=63 && soh<=83){
        counts.exchange++;
      } else{
        counts.failed++;
      }
    }
    return counts;
  }

  static void testBucketingByHealth() {
    System.out.println("Counting batteries by SoH...\n");
    //Original Test
    int[] presentCapacities1 = {113, 116, 80, 95, 92, 70};
    CountsBySoH counts1 = countBatteriesByHealth(presentCapacities1);
    assert(counts1.healthy == 2) : "Healthy count failed";
    assert(counts1.exchange == 3) : "Exchange count failed";
    assert(counts1.failed == 1) : "Failed count failed";
    System.out.println("Test 1 passed");

    //New test: boundary condition for healthy (83%)
    int[] presentCapacities2 = {100,83*120/100}; //Should classify both as healthy
    CountsBySoH counts2 = countBatteriesByHealth(presentCapacities2);
    assert(counts2.healthy == 2) : "Boundary test for healthy failed";
    System.out.println("Test 2 passed");

    //New test: boundary condition for exchange(63%)
    int[] presentCapacities3 = {63*120/100, 83*120/100 -1}; //83% and 63% exactly
    CountsBySoH counts3 = countBatteriesByHealth(presentCapacities3);
    assert(counts3.healthy == 0): "Boundary test healthy(none) failed";
    assert(counts3.healthy == 2): "Boundary test exchange failed";
    System.out.println("Test 3 passed");

    //New test: failed battery at 0% capacity
    int[] presentCapacities4 = {0,62*120/100}; //should classify both as failed
    CountsBySoH counts4 = countBatteriesByHealth(presentCapacities4);
    assert(counts4.failed == 2): "Failed boundary test failed";
    System.out.println("Test 4 passed");

    //New test: 100% SoH, extreme healthy
    int[] presentCapacities5 = {120}; //should classify as healthy
    CountsBySoH counts5 = countBatteriesByHealth(presentCapacities5);
    assert(counts5.healthy == 1) : "100% healthy test failed";
    
    System.out.println("All tests passed");
  }

  public static void main(String[] args) {
    testBucketingByHealth();
  }
}
