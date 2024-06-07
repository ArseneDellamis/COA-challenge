function isSubArrayFromSum(arr, sum) {
    
    let currentSum = 0;
    const sumMap = new Map();  // map to store sums at each index

    for (let i = 0; i < arr.length; i++) { 
        currentSum += arr[i];  // Update current sum

        // Check if current sum equals the target sum
        if (currentSum === sum) {
            return true;
        }

        // Check if there's a subarray that sums to the target
        if (sumMap.has(currentSum - sum)) {
            return true;
        }

        // Store the current sum in the map
        sumMap.set(currentSum, i);
    }

    return false;  // Return false if no subarray is found
}

// Test cases
const testCases = [
    { input: [4, 2, 7, 1, 9, 5], sum: 14, expected: true },
    { input: [1, 2, 3, 4, 5], sum: 9, expected: true },
    { input: [10, 2, -2, -20, 10], sum: -10, expected: true },
    { input: [1, 4, 20, 3, 10, 5], sum: 33, expected: true },
    { input: [1, 4], sum: 0, expected: false },
    { input: [1, -1, 5, -2, 3], sum: 3, expected: true },
    { input: [3, 3, 3, 3, 3], sum: 6, expected: true },
    { input: [5, 5, 5, 5, 5, 5], sum: 15, expected: true },
    { input: [1000000000, -1000000000, 1000000000], sum: 1000000000, expected: true },
    { input: new Array(100000).fill(1).concat([999999900]), sum: 1000000000, expected: true },
];

// Running the test cases
testCases.forEach((test, index) => {
    const result = isSubArrayFromSum(test.input, test.sum);
    console.log(`Test Case ${index + 1}:`);
    console.log(`Input: "${test.input}"`);
    console.log(`Sum: ${test.sum}`);
    console.log(`Expected: "${test.expected}"`);
    console.log(`Result: "${result}"`);
    console.log(result === test.expected ? "Passed" : "Failed");
    console.log('---');
});
