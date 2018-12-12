package org.atlas.mtglifecounter.util;

import java.util.Random;

/**
 * @author José Acuña
 * @version 1.3
 */
public class Math {

    public static Random random = new Random();

    /**
     * Gets a random number between two limits
     *
     * @param min left limit
     * @param max right limit
     * @return a random integer
     */
    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * Method that returns the num without passing a given limit
     *
     * @param num num to compare with the limit
     * @param min minimum number to return
     * @param max maximum number to return
     * @return an integer inside the domain
     */
    public static int clamp(int num, int min, int max) {
        if (num > max) {
            return max;
        }
        if (num < min) {
            return min;
        } else {
            return num;
        }
    }

    /**
     * Approach "num" towards "result" by "amount" and returns the result
     *
     * @param num    integer to approach
     * @param result integer expected
     * @param amount how much to approach
     * @return integer of the maximum approach it could do
     */
    public static int approach(int num, int result, int amount) {
        if (num < result) {
            num += amount;
            if (num > result)
                return result;
        } else {
            num -= amount;
            if (num < result)
                return result;
        }
        return num;
    }

    /**
     * Returns the division of an integer number to the ceiling
     *
     * @param num1 divided
     * @param num2 divisor
     * @return result of the operation
     */
    public static int ceilingDivision(int num1, int num2) {
        double result = num1 / num2;
        if (result * 10 % 10 >= 5) {
            result += 1;
        }
        return (int) result;
    }
}
