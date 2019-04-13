import etc.MyCashe;

public class main {

    public static void main(String[] args) throws InterruptedException {

        //создадим кеш
        MyCashe<Integer, String> myCashe =
                new MyCashe<>(10, 10000, 1000);

        myCashe.put(1, "odin");
        myCashe.put(2, "dva");
        myCashe.put(3, "tri");



        System.out.println("el1 = " + myCashe.get(1));
        System.out.println("el2 = " + myCashe.get(2));
        System.out.println("el4 = " + myCashe.get(4));
        System.out.println(myCashe.getHit() + "/" + myCashe.getMiss());
        Thread.sleep(1000);

        System.out.println("el1 = " + myCashe.get(1));
        System.out.println(myCashe.getHit() + "/" + myCashe.getMiss());
        Thread.sleep(1000);
        System.out.println("el1 = " + myCashe.get(1));
        System.out.println("el2 = " + myCashe.get(2));
        System.out.println(myCashe.getHit() + "/" + myCashe.getMiss());


        for (int i = 0; i <= 10; i++){
            Thread.sleep(1000);
            System.out.println("el1 = " + myCashe.get(1));
            System.out.println(myCashe.getHit() + "/" + myCashe.getMiss());
        }

    }

}

