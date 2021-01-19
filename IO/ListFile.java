import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListFile {
    public static void main(String[] args) {
        File dir = new File("D:\\Code\\os\\src");
        List<File> files = listDir(dir);
        //jdk1.8 集合框架使用stream操作，可以lambda表达式
        files.stream().forEach(System.out::println);

    }

    public static List<File> listDir(File dir){
        List<File> list = new ArrayList<>();
        if (dir.isFile()) {
            list.add(dir);
        } else if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    List<File> files = listDir(child);
                    list.addAll(files);
                }
            }
        }
        return list;
    }

    //优化：
    public static List<File> listDir2(File dir){
        List<File> list = new ArrayList<>();
        if (dir.isDirectory()) {
            //获取目录下一级的子文件、子文件夹
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    if (child.isDirectory()) {
                        //如果是子文件夹，递归调用获取
                        list.addAll(listDir2(child));
                    } else {
                        list.add(child);
                    }
                }
            }
        }
        return list;
    }
}
