package TP02;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class archive {
    public static void main(String[] args) throws FileNotFoundException {
        for (int i = 1; i <= 40; i++) {
            RandomAccessFile file = new RandomAccessFile("pmed" + i + ".txt", "rw");

        }

    }
}
