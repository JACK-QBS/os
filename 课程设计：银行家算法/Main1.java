package test.CurriculumDesign;

import java.util.Scanner;
// 主类，用来实例化银行家算法，然后调用对应的银行家安全算法
public class Main {
    public static void main(String[] args) {
        boolean Choose = true; // 用来控制是否继续的循环
        final String C = "n";  //字符串常量
        String Choice;   //用来保存用户的选择
        Scanner input = new Scanner(System.in);

        System.out.println("请输入进程数目：");
        int thread = input.nextInt();
        System.out.println("请输入资源数目：");
        int resource = input.nextInt();

        Bank bank = new Bank(resource,thread); //创建一个Bank类的对象
        bank.setAvailable(); //设置可用资源量
        bank.setMatrixVariable();   //调用方法初始化各类矩阵

        while (Choose == true) {
            bank.setRequest();
            Choice = input.nextLine();
            if (C.equalsIgnoreCase(Choice)) {  //忽略大小写
                System.out.println("已成功退出程序！");
                Choose = false;
            }
        }
        input.close();
    }
}
