import java.util.TreeMap;

public class ArrayString {

    public static void reverseArray(int[] arr, int start_index, int end_index) {
        int n = arr.length;
        if (0 <= start_index && start_index <= end_index && end_index < n) {
            while (start_index < end_index) {
                int temp = arr[start_index];
                arr[start_index] = arr[end_index];
                arr[end_index] = temp;
                start_index++;
                end_index--;
            }
        }
    }


    // input -> 1 2 3 4 5 6 7
    // k = +2
    // output -> 3 4 5 6 7 1 2
    //
    public static void rotateByK(int[] arr, int k) {
        int n = arr.length;
        k %= n;
        if (k < 0) {
            k += n;
        }

        // rotate whole
        // 7 6 5 4 3 2 1
        // rotate last K
        // 7 6 5 4 3 1 2
        // rorate first n - K
        // 3 4 5 6 7 1 2
        reverseArray(arr, 0, n - 1);
        reverseArray(arr, 0, n - k - 1);
        reverseArray(arr, n - k, n - 1);
    }


    // input -> 1 -2 3 4 -5
    // output -> -2 -5 1 3 4
    public static void segregatePositiveAndNegative(int[] arr) {
        int n = arr.length;
        int start_index = 0;
        int end_index = n - 1;
        // just shift all positive numbers last
        while (start_index < end_index) {
            if (arr[start_index] >= 0) {
                if (arr[end_index] < 0) {
                    swap(arr, start_index, end_index);
                    start_index++;
                    end_index--;

                } else {
                    end_index--;
                }

            } else {
                start_index++;
            }
        }
    }


    public static void segregateZeroAndOne(int[] arr) {
        int n = arr.length;
        // define region
        // [0,ptr) => 0
        // [ptr,itr) => 1
        // [itr,n) => unexplored
        int ptr = 0;            // pointer
        int itr = 0;            // iterator
        while (itr < n) {
            if (arr[itr] == 0) {
                swap(arr, ptr++, itr);
            }
            itr++;
        }
    }


    public static void segregateZeroOneTwo(int[] arr) {
        int n = arr.length;
        // define region
        // [0,i) => 0
        // [i,j) => 1
        // [j,k) => 2
        // [k,n) => explore
        int i = 0;
        int j = 0;
        int k = 0;
        while (k < n) {
            if (arr[k] == 1) {
                swap(arr, j++, k);
            }
            if (arr[k] == 0 ) {
                swap(arr, i++, k);
                swap(arr, j++, k);
            }
            k++;
        }
    }


    public static int maxSumInTheConfiguration(int[] arr) {
        int n = arr.length;
        int currSum = 0;
        int total = 0;
        for (int i = 0; i < n; i++) {
            total += arr[i];
            currSum += i * arr[i];
        }

        int maxSum = currSum;
        // System.out.println(0 + " " + currSum);
        for (int i = 1; i < n; i++) {
            currSum = currSum - total + n * arr[i - 1];
            // System.out.println(i + " " + currSum);
            maxSum = Math.max(maxSum, currSum);
        }

        return maxSum;
    }


    public int maxArea(int[] arr) {
        int n = arr.length;
        int i = 0;
        int j = n - 1;
        int maxArea = 0;
        while (i < j) {
            int minHeight = Math.min(arr[i], arr[j]);
            int currArea = minHeight * (j - i);
            maxArea = Math.max(maxArea, currArea);
            // elimination
            if (arr[i] < arr[j]) {
                // there is no rectage with starting edge I & area > maxArea
                i++;
            } else {
                // there is no rectage with ending edge J & area > maxArea
                j--;
            }
        }
        return maxArea;
    }


    // Leetcode 3. Longest Substring Without Repeating Characters
    public int lengthOfLongestSubstring(String str) {
        int n = str.length();
        int si = 0, ei = 0;
        // my sub-string [si,ei)
        int[] freq = new int[128];
        int maxAns = 0;
        boolean flag = false;
        while (ei < n) {
            if ((freq[str.charAt(ei++)]++) >= 1) {
                flag = true;
            }

            while (flag) {
                if ((freq[str.charAt(si++)])-- >= 2) {
                    flag = false;
                }
            }

            maxAns = Math.max(maxAns, ei - si);
        }
        return maxAns;
    }


    // Leetcode 159
    // Leetcode 340
    // Find the longest substring with k unique characters in a given string
    public int longestkSubstr(String str, int k) {
        int n = str.length();
        int si = 0, ei = 0;
        int maxAns = -1;
        int[] freq = new int[128];
        int distinctCount = 0;
        while (ei < n) {
            if ((freq[str.charAt(ei++)]++) == 0) {
                distinctCount++;
            }
            while (distinctCount > k) {
                if ((freq[str.charAt(si++)]--) == 1) {
                    distinctCount--;
                }
            }
            // my sub-string [si,ei)
            if (distinctCount == k) {
                maxAns = Math.max(maxAns, ei - si);
            }
        }
        return maxAns;
    }


    // Leetcode 76. Minimum Window Substring
    public String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();
        if ( m < n ) {
            return "";
        }
        // +ve means kitna required hai
        // 0 means exactly same
        // -ve means kitna extra hai
        // count -> total kitna character required hai
        int[] freq = new int[128];
        for (int i = 0; i < n; i++) {
            freq[t.charAt(i)]++;
        }
        int count = n;

        int si = 0, ei = 0;
        int gsi = 0, gei = 0;
        int minAns = (int)1e9;
        while (ei < m) {
            if (freq[s.charAt(ei)] > 0) {
                count--;
            }
            freq[s.charAt(ei)]--;
            ei++;

            while (count == 0) {
                // System.out.println(si + "" + ei);
                if (ei - si < minAns) {
                    minAns = ei - si;
                    gsi = si;
                    gei = ei;
                }
                if (freq[s.charAt(si)] == 0) {
                    count++;
                }
                freq[s.charAt(si)]++;
                si++;
            }
        }
        return s.substring(gsi, gei);
    }


    // Smallest window that contains all characters of string itself
    public int findSubString( String s) {
        int n = s.length();
        int[] freq = new int[128];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (freq[s.charAt(i)] == 0) {
                freq[s.charAt(i)] = 1;
                count++;
            }
        }

        int si = 0, ei = 0;
        int minAns = (int)1e9;
        while (ei < n) {
            if (freq[s.charAt(ei)] > 0) {
                count--;
            }
            freq[s.charAt(ei)]--;
            ei++;

            while (count == 0) {
                if (ei - si < minAns) {
                    minAns = ei - si;
                }
                if (freq[s.charAt(si)] == 0) {
                    count++;
                }
                freq[s.charAt(si)]++;
                si++;
            }
        }
        return minAns;
    }


    // Leetcode 1456. Maximum Number of Vowels in a Substring of Given Length
    public boolean checkVowel(char c) {
        if ((c == 'a') || (c == 'e') || (c == 'i') ||  (c == 'o') || (c == 'u')) {
            return true;
        }
        return false;
    }

    public int maxVowels(String str, int k) {
        int n = str.length();
        if ( k > n) {
            return 0;
        }
        int count = 0;
        int maxAns = 0;
        for (int i = 0; i < n; i++) {
            count += checkVowel(str.charAt(i)) ? 1 : 0;
            if (i >= k) {
                count -= checkVowel(str.charAt(i - k)) ? 1 : 0;
            }
            maxAns = Math.max(maxAns, count);
        }
        return maxAns;
    }


    // Count all sub-arrays with atmost K distinct integers
    public static int subarraysWithAtmostKDistinct(int[] arr, int k) {
        int n = arr.length;
        if (k > n) {
            return 0;
        }
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int si = 0, ei = 0;
        int count = 0;
        int ans = 0;
        while (ei < n) {
            if (map.getOrDefault(arr[ei], 0) == 0) {
                count++;
            }
            map.put(arr[ei], map.getOrDefault(arr[ei], 0) + 1);
            ei++;
            while (count > k) {
                if (map.getOrDefault(arr[si], 0) == 1) {
                    count--;
                }
                map.put(arr[si], map.getOrDefault(arr[si], 0) - 1);
                si++;
            }
            // my window [si,ei)
            ans += ei - si;
        }

        return ans;
    }


    // Leetcode 992. Subarrays with K Different Integers
    // $$$$$$$$$$$$$$$$$$$$ IMPORTANT $$$$$$$$$$$$$$$$$$$$
    public static int subarraysWithKDistinct(int[] arr, int k) {
        int n = arr.length;
        if (k > n) {
            return 0;
        }
        int countKDistinct = subarraysWithAtmostKDistinct(arr, k);
        int countKMinusOneDistinct = subarraysWithAtmostKDistinct(arr, k - 1);
        return countKDistinct - countKMinusOneDistinct;
    }


    // Count all sub-arrays with atmost K odd integers
    public int subarraysWithAtmostKOdd(int[] arr, int k) {
        int n = arr.length;
        if (k > n) {
            return 0;
        }
        int si = 0, ei = 0;
        int count = 0;
        int ans = 0;
        while (ei < n) {
            if ((arr[ei] % 2) == 1) {
                count++;
            }
            ei++;
            while (count > k) {
                if ((arr[si] % 2) == 1) {
                    count--;
                }
                si++;
            }
            // my window [si,ei)
            ans += ei - si;
        }

        return ans;
    }


    // Leetcode 1248. Count Number of Nice Subarrays
    public int numberOfSubarrays(int[] arr, int k) {
        // if k = 3
        // subarraysWithAtmostKOdd(arr, k) -> all sub-arrays with odd integers either 0 | 1 | 2 | 3
        // subarraysWithAtmostKOdd(arr, k-1) -> all sub-arrays with odd integers either 0 | 1 | 2
        // ans -> all sub-arrays with odd integers  (0 | 1 | 2 | 3) - (0 | 1 | 2) = exactly 3
        return subarraysWithAtmostKOdd(arr, k) - subarraysWithAtmostKOdd(arr, k - 1);
    }


    // Find the longest substring with k unique characters in a given string
    public int longestkSubstr(String str, int k) {
        int n = str.length();
        if ( k > n ) {
            return 0;
        }
        int si = 0, ei = 0, gsi = 0, gei = 0;
        int count = 0;
        int maxAns = -1;
        int[] freq = new int[128];
        while (ei < n) {
            if (freq[str.charAt(ei++)]++ == 0) {
                count++;
            }
            while (count > k) {
                if (freq[str.charAt(si++)]-- == 1) {
                    count--;
                }
            }

            if (count == k) {
                maxAns = Math.max(maxAns, ei - si);
            }
        }

        return maxAns;
    }


    // Leetcode 395. Longest Substring with At Least K Repeating Characters
    public int longestSubstring(String str, int k) {
        // m unique characters , lognest sub-string
        // followup freq of each character > k
        // loop m from 1-26, find max
    }


    // Leetcode 904. Fruit Into Baskets
    // longest sub-array with atmost 2 disticnt integers
    public int totalFruit(int[] arr) {
        int n = arr.length;
        int si = 0, ei = 0;
        int[] freq = new int[1e5 + 10];
        int count = 0;
        int maxAns = 0;
        while (ei < n) {
            if (freq[arr[ei++]]++ == 0) {
                count++;
            }
            while (count > 2) {
                if (freq[arr[si++]]-- == 1) {
                    count--;
                }
            }

            maxAns = Math.max(maxAns, ei - si);
        }

        return maxAns;
    }


    // Leetcode 930. Binary Subarrays With Sum
    // countAllSubArraysWithSumAtmostK(goal) - countAllSubArraysWithSumAtmostK(goal-1)
    public int numSubarraysWithSum(int[] arr, int goal) {
        if (goal == 0) {
            return countAllSubArraysWithSumAtmostK(arr, goal);
        }
        return countAllSubArraysWithSumAtmostK(arr, goal) - countAllSubArraysWithSumAtmostK(arr, goal - 1);
    }

    public int countAllSubArraysWithSumAtmostK(int[] arr, int k) {
        int n = arr.length;
        int si = 0, ei = 0, sum = 0, ans = 0;
        while (ei < n) {
            sum += arr[ei++];
            while (sum > k) {
                System.out.println(si + "asd" + ei);
                sum -= arr[si++];
            }
            ans += ei - si;
        }
        return ans;
    }


    // Leetcode 485. Max Consecutive Ones
    // longest sub-array with 1 disticnt character and here the character is '1'
    public int findMaxConsecutiveOnes(int[] arr) {
        int n = arr.length;
        int si = 0, ei = 0, maxAns = 0;
        boolean flag = false;
        while (ei < n) {
            if (arr[ei++] == 0) {
                flag = true;
            }
            while (flag == true) {
                si = ei;
                flag = false;
            }
            maxAns = Math.max(maxAns, ei - si);
        }
        return maxAns;
    }



    // Find zeroes to be flipped so that number of consecutive 1’s is maximized
    // m is maximum of number zeroes allowed to flip
    int findZeroes(int arr[], int n, int m) {
        int si = 0, ei = 0, count = 0, maxAns = 0;
        while (ei < n) {
            if (arr[ei++] == 0) {
                count++;
            }
            while (count > m) {
                if (arr[si++] == 0) {
                    count--;
                }
            }

            maxAns = Math.max(maxAns, ei - si);
        }
        return maxAns;
    }


    // Leetcode 974. Subarray Sums Divisible by K
    // $$$$$$$$$$$$$$$$$$$$ IMPORTANT $$$$$$$$$$$$$$$$$$$$
    public int subarraysDivByK(int[] arr, int k) {
        int n = arr.length;
        if (n == 0) {
            return 0;
        }
        int ans = 0;
        int si = 0, ei = 0, sum = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        // before starting we have a (sum = 0) => (remainder = 0)
        map.put(0, 1);
        while (ei < n) {
            sum += arr[ei++];
            int remainder = ((sum % k) + k) % k;
            ans += map.getOrDefault(remainder, 0);
            map.put(remainder, map.getOrDefault(remainder, 0) + 1);
        }

        return ans;
    }


    // Count subarrays with equal number of 1’s and 0’s
    // $$$$$$$$$$$$$$$$$$$$ IMPORTANT $$$$$$$$$$$$$$$$$$$$
    static int countSubarrWithEqualZeroAndOne(int arr[], int n) {
        // repalce 0 -> -1
        // count all sub-arrays with equal 0’s & 1’s -> count all sub-arrays with sum = 0
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0)
                arr[i] = -1;
        }
        int sum = 0;
        int ans = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(0, 1);
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            ans += map.getOrDefault(sum, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return ans;
    }


    // Leetcode 525. Contiguous Array
    public int findMaxLength(int[] arr) {
        // repalce 0 -> -1
        // count all sub-arrays with equal 0’s & 1’s -> count all sub-arrays with sum = 0
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0) {
                arr[i] = -1;
            }
        }
        int sum = 0;
        int ans = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(0, -1);
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            int firstIndex = map.getOrDefault(sum, -7);
            if (firstIndex == -7) {
                // we got this sum for the first time
                // storing the first occurence of sum in map
                map.put(sum, i);
            } else {
                // we got this sum before
                ans = Math.max(ans, i - firstIndex);
            }
        }

        return ans;

    }


    // Leetcode 239. Sliding Window Maximum
    // O(n * k)
    public int[] maxSlidingWindow(int[] arr, int k) {
        int n = arr.length;
        int [] ans = new int[n - k + 1];
        for (int i = 0; i <= n - k; i++) {
            int max = arr[i];
            for (int j = i; j < i + k; j++) {
                max = Math.max(max, arr[j]);
            }
            ans[i] = max;
        }

        return ans;
    }


    // $$$$$$$$$$$$$$$$$$$$ IMPORTANT $$$$$$$$$$$$$$$$$$$$
    public int[] maxSlidingWindow(int[] arr, int k) {

    }


    // Kadane's Algorithm
    int maxSubarraySum(int arr[]) {
        int n = arr.length;
        int gsum = -(int)1e9;
        int csum = 0;
        for (int i = 0; i < n; i++) {
            csum += arr[i];
            gsum = Math.max(gsum, csum);
            if (csum <= 0) {
                csum = 0;
            }
        }

        return gsum;
    }


    //  11 6 4 -1 -2 5 -300 2 100
    // Kadane's Algorithm find sub-array
    int maxSubarraySum(int arr[]) {
        int n = arr.length;
        int gsum = -(int)1e9;
        int csum = 0;
        int csi = 0, gsi = 0, gei = 0;
        for (int i = 0; i < n; i++) {
            csum += arr[i];
            if (csum > gsum) {
                gsum = csum;
                gsi = csi;
                gei = i;
            }
            if (csum <= 0) {
                csum = 0;
                csi = i + 1;
            }
        }

        return gsum;
    }


    // Leetcode 1191. K-Concatenation Maximum Sum
    public int kConcatenationMaxSum(int[] arr, int k) {

    }


























    public static void swap(int []arr, int i, int j) {
        int c = arr[i];
        arr[i] = arr[j];
        arr[j] = c;
    }

    public static void printArray(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("");
    }


    public static void main(String[] args) {
        System.out.println("Hello, World!");
        // int[] arr = new int[] { -1, 2, 3, 4, -5, 6, -7};
        // rotateByK(arr, -9);
        // segregatePositiveAndNegative(arr);

        // int[] arr = new int[] {1, 0, 1, 1, 0, 0, 1};
        // segregateZeroAndOne(arr);

        // int[] arr = new int[] {1, 0, 2, 2, 0, 0, 2, 1, 0};
        // segregateZeroOneTwo(arr);

        // int[] arr = new int[] {4, 6, 1, 2, 7};
        // System.out.println(maxSumInTheConfiguration(arr));

        int[] arr = new int[] {1, 2, 3, 4};
        printArray(arr);
        int k = 3;
        System.out.println(subarraysWithKDistinct(arr, k));
        System.out.println(subarraysWithAtmostKDistinct(arr, k));
    }
}



