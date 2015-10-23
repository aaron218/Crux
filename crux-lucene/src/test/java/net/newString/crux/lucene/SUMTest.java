package net.newString.crux.lucene;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/24 0024.
 */
public class SUMTest {


    @Test
    public void Sum() {

        List<Some> w = new ArrayList<>();

        //init w

        double[][] res = new double[w.size()][2];


    }

    private class Some {
        double[] x;

        public double[] getX() {
            return x;
        }

        public Some setX(double[] x) {
            this.x = x;
            return this;
        }
    }
}
