// Function to reverse a given string
function reverseString(sentence) {
    return sentence.split('').reverse().join('');
}

// Function to convert each character in a string to its ASCII value, 
// and return a space-separated string of these values
function sentenceToAscii(sentence) {
    return sentence.split('').map(char => char.charCodeAt(0)).join(' ');
}

// Function to modify a string based on its length
// If length is divisible by 3, reverse the string
// If length is divisible by 5, convert the string to ASCII values
// If length is divisible by both 3 and 5, convert the reversed string to ASCII values
function modifyString(sentence){
    let n = sentence.length;
    let modifiedSentence = sentence;
    // Reverse the string if its length is divisible by 3
    if(n % 3 === 0){
        modifiedSentence = reverseString(modifiedSentence);
    }
    // Convert the string to ASCII values if its length is divisible by 5
    if(n % 5 === 0){
        modifiedSentence = sentenceToAscii(modifiedSentence);
    }
    return modifiedSentence;
}

// Array of test cases with inputs and expected outputs
const testCases = [
    { input: "Hello World,how", expected: "119 111 104 44 100 108 114 111 87 32 111 108 108 101 72" }, // Length 15 (divisible by both 3 and 5)
    { input: "abcdefg", expected: "abcdefg" }, // Length 7 (not divisible by 3 or 5)
    { input: "Hello", expected: "72 101 108 108 111" }, // Length 5 (divisible by 5)
    { input: "abcdef", expected: "fedcba" }, // Length 6 (divisible by 3)
    { input: "abcde", expected: "97 98 99 100 101" }, // Length 5 (divisible by 5)
    { input: "abcd", expected: "abcd" }, // Length 4 (not divisible by 3 or 5)
    { input: "abcdefghij", expected: "97 98 99 100 101 102 103 104 105 106" }, // Length 10 (divisible by 5)
    { input: "abcdefgh", expected: "abcdefgh" }, // Length 8 (not divisible by 3 or 5)
    { input: "abcdefghijklmno", expected: "111 110 109 108 107 106 105 104 103 102 101 100 99 98 97" }, // Length 15 (divisible by both 3 and 5)
    { input: "a", expected: "a" }, // Length 1 (not divisible by 3 or 5)
];

// Iterate through each test case, apply the modifyString function, and log the results
testCases.forEach((test, index) => {
    const result = modifyString(test.input);
    console.log(`Test Case ${index + 1}:`);
    console.log(`Input: "${test.input}"`);
    console.log(`Expected: "${test.expected}"`);
    console.log(`Result: "${result}"`);
    console.log(result === test.expected ? "Passed" : "Failed");
    console.log('---');
});
