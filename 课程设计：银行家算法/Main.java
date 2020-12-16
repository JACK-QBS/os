package CurriculumDesign;

// 主类，用来实例化银行家算法，然后调用对应的银行家安全算法
public class Main {
    public static void main(String[] args) {
        /** 开始调用银行家算法进行资源请求(判断是不是能形成一个安全序列) */
        //银行家对象
        Bank banker = new Bank();
        //通过对象调用银行家算法
        banker.BankAlgorithm();
    }
}
