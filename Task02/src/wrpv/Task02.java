package wrpv;

import java.io.IOException;

public class Task02 {
    public static void main(String[] args)  {

        try {
            new FoodServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
