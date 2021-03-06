package diditest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/5 上午12:08
 */
public class Test {
    public static void main(String[] args) {
        new Test().<String,String>get(1);
    }
    public <T,K> List<T> get(int i) {
        ArrayList<T> ts = new ArrayList<>();
        return ts;
    }
}
    