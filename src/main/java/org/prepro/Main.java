package org.prepro;

public class Main {
    public static void main(String[] args) {

        Grid test = new Grid();
        test.addValue(0, 0, 9);
        test.addValue(1, 0, 7);

        test.addValue(0, 1, 8);
        test.addValue(1, 1, 6);


        test.addValue(0, 2, 3);
        test.addValue(2, 2, 2);

        test.print();
        System.out.println(test.k_upletsTest(3,0,0,2,2)); //doit etre vrai
    }
}
