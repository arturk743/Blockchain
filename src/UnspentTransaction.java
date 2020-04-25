import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnspentTransaction implements Serializable {
    private String hashOfTransaction; // hash of transaction that is unspent
    private List<Boolean> out=new ArrayList<Boolean>(Arrays.asList(new Boolean[2])); //inform whether transaction is spent(false) or unspent(true)

    public UnspentTransaction(String hashOfTransaction, boolean out1, boolean out2) {
        this.hashOfTransaction = hashOfTransaction;
        this.out.set(0,out1);
        this.out.set(1,out2);
    }

    @Override
    public String toString(){
        return this.hashOfTransaction + " " + this.out.get(0) + " " + this.out.get(1);
    }

    public boolean isSpent(){ //true means it needs to be removed from ArrayList in Blockchain
        if (out.get(0) || out.get(1)){
            return false;
        }else{
            return true;
        }
    }

    public boolean isOut(int index) {
        return out.get(index);
    }

    public String getHashOfTransaction() {
        return hashOfTransaction;
    }

    public void changeOut(int id){
        this.out.set(id,false);
    }


}
