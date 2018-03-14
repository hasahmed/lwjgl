import com.shapegame.App;
import com.shapegame.Color;
import com.shapegame.ThreadTest;
import com.shapegame.shapes.Square;

/*
 * Project : shapegame
 * Package : com.shapegame
 * Creator : Hasan Yusuf Ahmed
 * Date    : 3/12/18
 */
public class Main extends App {

    public int getWindowWidth(){
        return 800;
    }

    public int getWindowHeight(){
        return 800;
    }
    public static void main(String[] args){
        ThreadTest test = new ThreadTest();
        Thread t = new Thread(test, "Thread Test");
        t.start();
        Main main = new Main();
        float r = 0;
        float g = 0;
        float b = 0;
        int x = 0;
        int y = 0;
        int SQUARESIZE = 100;
        float cChange = 0.0001f;
        for (int i = 0; i < 10000; i += 1) {
            if (i % main.getWindowWidth() == 0 && i > 0) {
//                y += SQUARESIZE;
                y += 1;
                x = 0;
            }
            main.getScene().addChild(new Square(x, y, SQUARESIZE, new Color(r, g, b)));
            r += cChange;
            g += cChange;
            b += cChange;
            x++;
        }
//        main.getScene().addChild(new Square(0, 100, 100, new Color(r, g, b)));
//        main.getScene().addChild(new Square(102, 0, 100, new Color(r, g, b)));
//        main.getScene().addChild(new Square(204, 0, 100, new Color(r, g, b)));
//        main.getScene().addChild(new Square(0, 0, 100, Color.WHITE));
//        main.getScene().addChild(new Square(102, 0, 100, Color.WHITE));
        main.run();
//        test.shouldRun = false;
//        System.out.println("Yoyoyo");
//        try{
//            t.join();
//        } catch(InterruptedException e){
//            System.out.println("There was some time of thing that happened");
//        }
    }
}
