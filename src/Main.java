import com.shapegame.App;
import com.shapegame.ThreadTest;

/*
 * Project : shapegame
 * Package : com.shapegame
 * Creator : Hasan Yusuf Ahmed
 * Date    : 3/12/18
 */
public class Main extends App {

    public int getWindowWidth(){
        return 400;
    }

    public int getWindowHeight(){
        return 400;
    }
    public static void main(String[] args){
        ThreadTest test = new ThreadTest();
        Thread t = new Thread(test, "Thread Test");
        t.start();
        new Main().init();
//        test.shouldRun = false;
//        System.out.println("Yoyoyo");
//        try{
//            t.join();
//        } catch(InterruptedException e){
//            System.out.println("There was some time of thing that happened");
//        }
    }
}
