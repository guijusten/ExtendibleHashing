import java.util.ArrayList;
import java.util.Arrays;

public class Directory {

    private Bucket[] buckets;
    private int globalDepth;
    private int bucketSize;
    private int numBits;
    private int directorySize;

    public Directory(int bucketSize, int numBits){
        this.globalDepth = 1;
        this.bucketSize = bucketSize;
        this.numBits = numBits;
        Bucket b = new Bucket(bucketSize);
        this.buckets = new Bucket[] {b, b};
        this.directorySize = (int)Math.pow(2, this.globalDepth);
    }

    public void insertPseudoKey(String pseudoKey){
        //Pegando os n primeiros digitos da pseudochave e acessando o balde adequado
        String identifier = Utils.getNFirstDigits(pseudoKey, this.globalDepth);
        int bucketIndex = Utils.binaryToInteger(identifier);
        Bucket bucket = this.buckets[bucketIndex];

        boolean inserted = bucket.insert(pseudoKey);
        if(!inserted){
            if(bucket.getLocalDepth() == this.globalDepth){
                this.duplicateDirectory();
            }
            //Realizar a divisao do balde e incrementar depth local
            divideBucket(bucketIndex);
            //Inserir a pseudochave no balde adequado
            identifier = Utils.getNFirstDigits(pseudoKey, this.globalDepth);
            bucketIndex = Utils.binaryToInteger(identifier);
            bucket = this.buckets[bucketIndex];
            bucket.insert(pseudoKey);
        }
    }

    public boolean searchPseudoKey(String pseudoKey){
        String identifier = Utils.getNFirstDigits(pseudoKey, this.globalDepth);
        Bucket bucket = this.buckets[Utils.binaryToInteger(identifier)];

        return bucket.search(pseudoKey);
    }

    public void divideBucket(int dividedBucketIndex){
        //Criando dois novos baldes e armazenando o conteudo do balde a ser dividido
        Bucket toDivideBucket = this.buckets[dividedBucketIndex];
        toDivideBucket.incrementLocalDepth();
        String[] aux = toDivideBucket.getPseudoKeys();
        Bucket newBucket = new Bucket(toDivideBucket);

        //Pegando os n maiores bits para fazer a inserção correta das pseudochaves nos dois baldes novos
        String nBiggerFirstDigits = Utils.getNFirstDigits(aux[this.bucketSize-1], toDivideBucket.getLocalDepth());

        toDivideBucket.clear();
        newBucket.clear();
        for(int i = 0; i < this.bucketSize; i++){
            if(aux[i] != null) {
                if (aux[i].startsWith(nBiggerFirstDigits)) {
                    toDivideBucket.insert(aux[i]);
                } else {
                    newBucket.insert(aux[i]);
                }
            }
        }

        //Reorganizando os ponteiros
        this.buckets[dividedBucketIndex] = newBucket;
        this.buckets[dividedBucketIndex].recalculateSizeLeft();

        this.buckets[dividedBucketIndex + 1] = toDivideBucket;
        this.buckets[dividedBucketIndex + 1].recalculateSizeLeft();
    }

    public void duplicateDirectory(){
        //Criando um novo diretorio com tamanho duplicado, e reorganizando os ponteiros
        Bucket[] newBuckets = new Bucket[this.directorySize * 2];
        for(int i = 0; i < this.directorySize; i++){
            newBuckets[i*2] = this.buckets[i];
            newBuckets[i*2 + 1] = this.buckets[i];
        }
        this.buckets = newBuckets;
        this.globalDepth += 1;
        this.directorySize *= 2;
    }

    public void print(){
        for(int i = 0; i < this.buckets.length; i++){
            System.out.println("\nBalde " + i);
            Bucket bucket = this.buckets[i];
            bucket.print();
        }
    }
}