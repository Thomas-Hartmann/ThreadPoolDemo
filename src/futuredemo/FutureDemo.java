/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futuredemo;

/**
 *
 * @author tha
 */
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * Java program to show how to use Future in Java. Future allows to write
 * asynchronous code in Java, where Future promises result to be available in future
 */
public class FutureDemo {

    private static final ExecutorService threadpool = Executors.newFixedThreadPool(3);

    public static void main(String args[]) throws InterruptedException, ExecutionException {
        FactorialCalculator task = new FactorialCalculator(10);
        System.out.println("Submitting Task ...");
        Future<Long> future = threadpool.submit(task);
        System.out.println("Task is submitted");
        while (!future.isDone()) { // Check if the call method in the callable has finished. Warning: This is not necessary - only for the purpose of seeing the execution flow. Generally dont do the busy waiting.
            System.out.println("Task is not completed yet....");
            Thread.sleep(1); //sleep for 1 millisecond before checking again 
        }
        System.out.println("Task is completed, let's check result");
        long factorial = future.get();
        System.out.println("Factorial of 1000000 is : " + factorial);
        threadpool.shutdown();
    }

    private static class FactorialCalculator implements Callable<Long> { // Callable interfacet has one method: E call() that returns an object of type E

        private final int number;

        public FactorialCalculator(int number) {
            this.number = number;
        }

        @Override
        public Long call() {
            long output = 0;
            try {
                output = factorial(number);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            return output;
        }

        private long factorial(int number) throws InterruptedException {
            if (number < 0) {
                throw new IllegalArgumentException("Number must be greater than zero");
            }
            long result = 1;
            while (number > 0) {
                Thread.sleep(1); // adding delay for the sake of example. 
                result = result * number;
                number--;
            }
            return result;
        }
    }
}

// http://javarevisited.blogspot.com/2015/01/how-to-use-future-and-futuretask-in-Java.html#ixzz3kbjdUqjk
