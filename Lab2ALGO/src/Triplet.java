public class Triplet<L,M,R> {
    private L l;
    private M m;
    private R r;

    public Triplet(L l,M m, R r){
        this.l =l;
        this. m = m;
        this.r = r;
    }
    public L getLup(){
        return l;
    }

    public M getLdown() {
        return m;
    }

    public R getValue() {
        return r;
    }

    public void setLup(L l) {
        this.l = l;
    }

    public void setLDown(M m) {
        this.m = m;
    }

    public void setValue(R r) {
        this.r = r;
    }

}
