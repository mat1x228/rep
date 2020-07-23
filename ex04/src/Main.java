import javafx.beans.binding.MapBinding;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String chars = scanner.nextLine();
        Map<Character, Integer> charCounts = new HashMap<Character, Integer>();
        chars.chars()
                .forEach(x->{
                    Character character = (char)x;
                    charCounts.putIfAbsent(character,0);
                    charCounts.put(character, charCounts.get(character)+1);
                });
        Map result= charCounts
                .entrySet()
                .stream()
                .sorted((x,y)->{//sort
                    if (x.getValue().equals(y.getValue())) {
                        return x.getKey().compareTo(y.getKey());
                    } else {
                        return y.getValue().compareTo(x.getValue());
                    }
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        final Integer[] values = new Integer[result.values().size()];
        System.arraycopy(result.values().toArray(),0,values,0,result.values().size());
        int min = values[charCounts.values().size()-1];
        int max = values[0];
        System.out.println("max: "+max+";min: "+min);
        result.forEach((k,v)->{//make values between 0-10
            int count = (int)v;
            count=(count-min)*10/(max-1);
            result.put(k,count);
        });
        System.arraycopy(result.values().toArray(),0,values,0,result.values().size());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int finalI = i;
            result.forEach((k, v) -> {
                int count = (int)v;
                if (count==10-finalI)
                    builder.append(charCounts.get(k));
                if(10-finalI<count&&finalI!=11)
                    builder.append("#");
                if(finalI==11)
                    builder.append(k);
                builder.append(" ");
            });
            builder.append("\n");
        }
        System.out.println(builder.toString());
    }
}
