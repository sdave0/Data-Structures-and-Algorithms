
//Shubham H. Dave 

import java.util.Arrays;

public class BinaryNumber {
    private int[] data;
    private boolean overflow;

    public BinaryNumber(int length) {
        if (length < 1) {
            throw new IllegalArgumentException( "Length cannot be less than 1");
        }
        data = new int[length]; 
        for(int i = 0; i < length; i++) {
            data[i] = 0;
        }
        overflow = false;  
    }

    // Constructor to create a binary number from a binary string
    public BinaryNumber(String str) {
        if (str.length() == 0) {
            throw new IllegalArgumentException("Sting cannot be Empty");
        }
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("Sting cannot have Empty Spaces");
        }
        if (str.length() != str.trim().length()) {
            throw new IllegalArgumentException("Sting cannot have Leading or Trailing Spaces");
        }

        int length = str.length();
        data = new int[length];

        for (int i = 0; i < length; i++) {
            char digitChar = str.charAt(i);
            if (digitChar != '0' && digitChar != '1') {
                throw new IllegalArgumentException("Sting can only have 0s and 1s.");
            }
            data[i] = Character.getNumericValue(digitChar);
        }

        overflow = false;
    }


    // Operation to get the length of the binary number
    public int getLength() {
        return data.length;
    }

    // Operation to get a digit of the binary number given an index
    public int getDigit(int index) {
        	if (index < 0) {
        		throw new IndexOutOfBoundsException("Index cannot be less than 0!");
          } else if (index >= data.length) {
        		throw new IndexOutOfBoundsException("Index Out of Bounds!");
          } else {
        		return data[index];
        }
    }

    private int[] reallocate(int amount) {
        return Arrays.copyOf(data, data.length + amount);
    }

    // Operation to shift the digits in a binary number to right
    public void shiftR(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cant be less than 0");
        }
        int[] shiftedData = reallocate(amount);
        int temp;
        int rightElement;
        for (int i = 0; i < amount; i++) {
            rightElement = shiftedData[shiftedData.length - 1];

            for (int j = 0; j < shiftedData.length; j++) {
                temp = shiftedData[j];
                shiftedData[j] = rightElement;
                rightElement = temp;
            }
        }
        data = shiftedData;
    }


    // Operation of Binary Numbers
    public void add(BinaryNumber aBinaryNumber) {
        if (data.length != aBinaryNumber.getLength()) {
            throw new IllegalArgumentException("Lengths of binary numbers are not equal!");
        } else {
            int maxLength = Math.max(data.length, aBinaryNumber.getLength());
            int[] result = new int[maxLength + 1];
            int carry = 0;

            for (int i = 0; i < maxLength; i++) {
                int sum = getDigit(i) + aBinaryNumber.getDigit(i) + carry;

                	if (sum == 0 || sum == 1) {
                		result[i] = sum;   // No carry
                		carry = 0;
                 } 	else if (sum == 2) {
                    	result[i] = 0;     // Carry 1
                    	carry = 1;
                 } 	else {
                    	result[i] = 1;     // Carry 1
                    	carry = 1;
                 }
            }

            if (carry > 0) {
                result[maxLength] = carry;
                overflow = true;
            } else {
                overflow = false;
            }

            data = result;
        }
    }


    // Operation to transform the binary number to a string
    public String toString() {
        if (overflow) {
            return "Overflow";
        } else {
            String result = "";
            for (int i = 0; i < data.length; i++) {
                result += data[i];
            }
            return result;
        }
    }


    // Operation to transform the binary number to its decimal form
    public int toDecimal() {
        int decimal = 0;
        for (int i = 0; i < data.length; i++) {
            decimal = (int) (Math.pow(2, i) * data[i]) + decimal;
        }
        return decimal;
    }

    
    // Operation to clear the overflow flag
    public void clearOverflow() {
        overflow = false;
    }
    
    
 // Example
    
    public static void main(String[] args) {    
        BinaryNumber n1 = new BinaryNumber(4);
        BinaryNumber n2 = new BinaryNumber("1011");

        System.out.println("Binary Number 1: " + n1.toString());
        System.out.println("Binary Number 2: " + n2.toString());

        n1.add(n2);

        System.out.println("Result after addition: " + n1.toString());
        System.out.println("Decimal value of result: " + n1.toDecimal());
    }

}
