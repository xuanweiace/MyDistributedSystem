package test;

public class forfun {
    public static void main(String[] args) {
        String[][] p = {{"localhost", "1888"},{"localhost", "1889"}};
        for(int i = 0; i<p.length; i++) {
            for(int j = 0; j<p[i].length; j++) System.out.println(p[i][j]);
        }
//        System.out.println(p);
    }
}
