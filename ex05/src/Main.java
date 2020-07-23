import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.IntStream;

class Schedule{
    int Time;
    String WeekDay;
    public Schedule(int time, String weekDay)
    {
        Time=time;
        WeekDay=weekDay;
    }
}

class Day{
    int Day;
    String WeekDay;
    public Day(int day, String weekDay)
    {
        Day=day;
        WeekDay=weekDay;
    }

    @Override
    public String toString() {
        return this.Day+" "+this.WeekDay;
    }
}

class Visit{
    int Time;
    int Day;
    String Name;
    boolean IsHere;
    public Visit(String name, int time, int day, String isHere)
    {
        Name=name;
        Time=time;
        Day=day;
        IsHere=isHere.equals(new String("HERE"));
    }

}

public class Main {

    public static String getWeekDay(int day)
    {
       return LocalDate.parse(day+"/9/2020" , DateTimeFormatter.ofPattern( "d/M/uuuu" ))
                .getDayOfWeek()
                .getDisplayName(
                        TextStyle.SHORT_STANDALONE ,
                        Locale.US
                )
                .substring(0,2)
                .toUpperCase();
    }

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        String line = "";
        String[] names = new String[10];
        int index=0;
        while(true)
        {
            line= scanner.nextLine();
            if(line.equals(new String(".")))
                break;
            names[index]=line;
            index++;
        }
        index=0;
        Schedule[] schedules = new Schedule[10];
        while(true)
        {
            line= scanner.nextLine();
            if(line.equals(new String(".")))
                break;
            String[] split = line.split(" ");
            schedules[index] = new Schedule(Integer.parseInt(split[0]),split[1]);
            index++;
        }
        index=0;
        Visit[] visits = new Visit[10];
        while(true)
        {
            line= scanner.nextLine();
            if(line.equals(new String(".")))
                break;
            String[] split = line.split(" ");
            visits[index] = new Visit(split[0],Integer.parseInt(split[1]),Integer.parseInt(split[2]),split[3]);
            index++;
        }
        Day[] matchDays = IntStream.range(1, 31)
                .mapToObj(x -> new Day(x, getWeekDay(x)))
                .filter(x ->
                        Arrays.stream(schedules)
                                .filter(s->s!=null)
                                .anyMatch(s -> s.WeekDay.equals(x.WeekDay))
                )
                .toArray(Day[]::new);
        Arrays.stream(matchDays).forEach(System.out::println);
        StringBuilder builder = new StringBuilder();
        builder.append("          ");
        for (Day day : matchDays) {
            Arrays.stream(schedules)
                    .filter(x ->x!=null&& x.WeekDay.equals(day.WeekDay))
                    .forEach(x -> {
                        String space = "";
                        space = day.Day / 10 >= 1 ? " " : "  ";
                        builder.append(x.Time + ":00 " + x.WeekDay + space + day.Day);
                    });
            builder.append("|");
        }
        builder.append("\n");
        Arrays.stream(names)
                .filter(x->x!=null)
                .forEach(name->
        {
            String space = new String(new char[10-name.length()]).replace('\0', ' ');
            builder.append(space).append(name);
            for (Day day : matchDays) {
                Arrays.stream(schedules)
                        .filter(x ->x!=null&& x.WeekDay.equals(day.WeekDay))
                        .forEach(s -> {
                            Visit match= Arrays
                                    .stream(visits)
                                    .filter(v->v!=null&&v.Time==s.Time&&day.Day==v.Day&& name.equals(v.Name))
                                    .findFirst()
                                    .orElse(null);
                            if (match==null)
                            {
                                builder.append("          ");
                            }
                            else {
                                if(match.IsHere)
                                    builder.append("         1");
                                else
                                    builder.append("        -1");
                            }
                        });
                builder.append("|");
            }
            builder.append("\n");
        });
        System.out.println(builder);
    }
}

