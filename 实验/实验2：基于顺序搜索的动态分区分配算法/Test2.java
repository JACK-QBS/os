package Test2;

public class Test2 {
    public static void main(String[] args) {
        Memory memory=new Memory(500);

        memory.allocation(320);
        memory.allocation(20);
        memory.allocation(100);
        memory.showZones();
        memory.collection(0);
        memory.showZones();
    }
}
