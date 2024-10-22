package bunchbysoh;

public class Main {

    static class CountsBySoH {
        public int healthy = 0;
        public int exchange = 0;
        public int failed = 0;
    }

    static CountsBySoH countBatteriesByHealth(int[] presentCapacities) {
        CountsBySoH counts = new CountsBySoH();
        
        for (int capacity : presentCapacities) {
            double soh = (100.0 * capacity) / 120; // Direct use of ratedCapacity
            
            // Classification logic
            if (soh > 83) {
                counts.healthy++;
            } else if (soh >= 63) {
                counts.exchange++;
            } else {
                counts.failed++;
            }
        }
        return counts;
    }

    static void testBucketingHealth() {
        System.out.println("Counting batteries by SoH... \n");

        // Original test
        int[] presentCapacities1 = {113, 116, 80, 95, 92, 70};
        CountsBySoH counts1 = countBatteriesByHealth(presentCapacities1);
        assert (counts1.healthy == 2) : "Healthy count failed";
        assert (counts1.exchange == 3) : "Exchange count failed";
        assert (counts1.failed == 1) : "Failed count failed";
        System.out.println("Test 1 passed");

        // New test: Boundary condition for healthy (83%).
        int[] presentCapacities2 = {100, (int) (120 * 0.83)}; // 100 Ah (83.33%) and 120 Ah (100%)
        CountsBySoH counts2 = countBatteriesByHealth(presentCapacities2);
        assert (counts2.healthy == 2) : "Boundary test for healthy failed";
        System.out.println("Test 2 passed");

        // New test: Boundary condition for exchange (63%).
        int[] presentCapacities3 = {(int) (120 * 0.63), (int) (120 * 0.83) - 1}; // 63% and just below 83%
        CountsBySoH counts3 = countBatteriesByHealth(presentCapacities3);
        assert (counts3.healthy == 0) : "Boundary test healthy (none) failed";
        assert (counts3.exchange == 2) : "Boundary test exchange failed";
        System.out.println("Test 3 passed");

        // New test: Failed battery at 0% capacity.
        int[] presentCapacities4 = {0, (int) (120 * 0.62)}; // 0% and 62% (below 63%)
        CountsBySoH counts4 = countBatteriesByHealth(presentCapacities4);
        assert (counts4.failed == 2) : "Failed boundary test failed";
        System.out.println("Test 4 passed");

        // New test: 100% SoH, extreme healthy.
        int[] presentCapacities5 = {120}; // Should classify as healthy
        CountsBySoH counts5 = countBatteriesByHealth(presentCapacities5);
        assert (counts5.healthy == 1) : "100% healthy test failed";
        System.out.println("Test 5 passed");
        
        System.out.println("All tests passed!");
    }

    public static void main(String[] args) {
        testBucketingHealth();
    }
}
