package iticbcn.xifratge;

public class AlgorismeMonoAlfabetic extends AlgorismeFactory {

    @Override 
    public Xifrador creaXifrador() {
        return new XifradorMonoalfabetic();
    }

}
