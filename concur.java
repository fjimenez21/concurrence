import java.util.Random;

class operation extends Thread {

    private int[] arr;

    private int lo, hi, partial;

    public operation(int[] arr, int lo, int hi)

    {

        this.arr = arr;

        this.lo = lo;

        this.hi = Math.min(hi, arr.length);

    }

    public int getPartialSum()

    {

        return partial;

    }

    public void run()

    {

        partial = sum(arr, lo, hi);

    }

    public static int sum(int[] arr)

    {

        return sum(arr, 0, arr.length);

    }

    public static int sum(int[] arr, int lo, int hi)

    {

        int total = 0;

        for (int i = lo; i < hi; i++) {

            total += arr[i];

        }

        return total;

    }

    public static int parallelThread(int[] arr)

    {

        return parallelThread(arr, Runtime.getRuntime().availableProcessors());

    }

    public static int parallelThread(int[] arr, int threads)

    {

        int size = (int) Math.ceil(arr.length * 1.0 / threads);

        operation[] sums = new operation[threads];

        for (int i = 0; i < threads; i++) {

            sums[i] = new operation(arr, i * size, (i + 1) * size);

            sums[i].start();

        }

        try {

            for (operation sum : sums) {

                sum.join();

            }

        } catch (InterruptedException e) { }

        int total = 0;

        for (operation sum : sums) {

            total += sum.getPartialSum();

        }

        return total;

    }

}

public class concur {

    public static void main(String[] args)

    {

        Random rand = new Random();

        int[] arr = new int[200000000];

        for (int i = 0; i < arr.length; i++) {

            arr[i] = rand.nextInt(10) + 1;

        }

        long start = System.currentTimeMillis();

        System.out.println("Single Sum:" +(operation.sum(arr)));

        System.out.println("Single Thread time: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();

        System.out.println("Parallel Sum:" + (operation.parallelThread(arr)));

        System.out.println("Parallel Thread time: " + (System.currentTimeMillis() - start));

    }

}