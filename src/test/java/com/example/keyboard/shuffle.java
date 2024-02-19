package com.example.keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class shuffle {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }

        for(int i = 1; i <= 20; i++ ){
            Collections.shuffle(numbers);
            System.out.println("셔플 테스트"+ i +": " + numbers);
        }
    }
}
