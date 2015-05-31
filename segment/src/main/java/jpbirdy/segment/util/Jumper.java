/**
 * Created by jpbirdy on 14-11-23.
 */

package jpbirdy.segment.util;


import jpbirdy.segment.SegToken;

/**
 * @author jpbirdy
 * @project Segmentation
 * @class Jumper
 * @date 14-11-23 11:00
 * @desc
 */
public class Jumper {
    private double minDistance;
    private SegToken token;

    public Jumper() {
        minDistance = 0;
        token = null;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public SegToken getToken() {
        return token;
    }

    public void setToken(SegToken token) {
        this.token = token;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
    }
}
