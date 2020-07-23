import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String line = "";
        int weekCounter=0;
        int[] pointArray = new int[18];
        while (!line.equals(new String("42"))&&weekCounter<18)
        {
            System.out.println("Week "+(weekCounter+1));
            line = scanner.nextLine();
            int minPoint = Arrays.asList(line
                    .split(" "))
                    .stream()
                    .mapToInt(Integer::parseInt)
                    .min()
                    .getAsInt();
            pointArray[weekCounter]=minPoint;
            weekCounter++;
        }
        for (int i = 0; i < pointArray.length ; i++) {
            if(pointArray[i]==0)
                continue;
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < pointArray[i] ; j++) {
                stringBuilder.append("=");
            }
            stringBuilder.append(">");
            System.out.println("Week"+(i+1)+" "+stringBuilder.toString());
        }
    }
}
