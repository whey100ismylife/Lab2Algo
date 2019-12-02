/**
 *
 * @param <L> Left generic
 * @param <M> Middle generic
 * @param <R> Right generic
 *
 * Triplet class, represent an object that can hold 3 different generics.
 *
 * @author roblof-8, johlax-8 and vesjon-5
 *
 *
 */

public class Triplet<L,M,R> {
    private L l;
    private M m;
    private R r;

    /**
     *
     * @param l left generic
     * @param m right generic
     * @param r middle generic
     */
    public Triplet(L l,M m, R r){
        this.l =l;
        this. m = m;
        this.r = r;
    }

    /**
     *
     * @return the value of LUp
     */
    public L getLup(){
        return l;
    }

    /**
     *
     * @return the value of LDown
     */
    public M getLdown() {
        return m;
    }

    /**
     *
     * @return the value at the given index
     */
    public R getValue() {
        return r;
    }

    /**
     *
     * @param l value given to left generic
     */
    public void setLup(L l) {
        this.l = l;
    }

    /**
     *
     * @param m value given to middle generic
     */
    public void setLDown(M m) {
        this.m = m;
    }

    /**
     *
     * @param r value given to right generic
     */
    public void setValue(R r) {
        this.r = r;
    }
}
