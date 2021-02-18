import java.util.ArrayList;
import java.util.Arrays;

public class Bucket {

    private int localDepth;
    private int totalSize;
    private String[] pseudoKeys;
    private int sizeLeft;

    //Criar um novo balde do zero
    public Bucket(int size){
        this(size, 1);
    }

    public Bucket(int size, int depth){
        this.localDepth = depth;
        this.totalSize = size;
        this.pseudoKeys = new String[size];
        this.sizeLeft = size;
    }

    //Criar cópia de um balde
    public Bucket(Bucket b){
        this.localDepth = b.getLocalDepth();
        this.totalSize = b.getTotalSize();
        this.sizeLeft = b.getSizeLeft();
        this.pseudoKeys = new String[this.totalSize];
        for(int i = 0; i < b.totalSize; i++){
            this.pseudoKeys[i] = b.getSinglePseudoKey(i);
        }
    }

    public boolean insert(String newKey){
        if(this.sizeLeft > 0){
            for(int i = 0; i < this.pseudoKeys.length; i++){
                if(this.pseudoKeys[i] == null || newKey.compareTo(this.pseudoKeys[i]) < 0){
                    //Inserindo a pseudochave na posição correta do balde com auxilio de um ArrayList
                    ArrayList<String> auxList = new ArrayList<String>(Arrays.asList(this.pseudoKeys));
                    auxList.add(i, newKey);
                    auxList.remove(auxList.size()-1);
                    this.pseudoKeys = auxList.toArray(this.pseudoKeys);
                    break;
                }
            }
            this.sizeLeft -= 1;
            return true;
        }
        return false;
    }

    public boolean search(String pseudoKey){
        for(String str: this.pseudoKeys){
            if(str.equals(pseudoKey)){
                return true;
            }
        }
        return false;
    }

    public void recalculateSizeLeft(){
        this.sizeLeft = this.totalSize;
        for(int i = 0; i < this.totalSize; i++){
            if(this.pseudoKeys[i] == null){
                this.sizeLeft--;
            }
        }
    }

    public void incrementLocalDepth(){
        this.localDepth += 1;
    }

    public int getLocalDepth() {
        return localDepth;
    }

    public int getTotalSize() { return totalSize; }

    public int getSizeLeft() {return sizeLeft; }

    public String getSinglePseudoKey(int index) {
        return pseudoKeys[index];
    }

    public String[] getPseudoKeys(){
        String[] str = new String[this.totalSize];
        for(int i = 0; i < this.totalSize; i++){
            str[i] = this.pseudoKeys[i];
        }
        return str;
    }

    public void clear(){
        this.pseudoKeys = new String[this.totalSize];
        this.sizeLeft = this.totalSize;
    }

    public void print(){
        for(int i = 0; i < this.pseudoKeys.length; i++){
            System.out.print("Chave " + i + ": " + this.pseudoKeys[i] + "\n");
        }
    }
}
