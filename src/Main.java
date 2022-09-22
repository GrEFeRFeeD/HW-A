import collections.map.MyHashMap;
import collections.map.MyMap;
import model.Person;
import sorters.QuickSorter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Person> personList = initPersonList();
        System.out.println("Person list: " + personList);

        QuickSorter.sort(personList, Comparator.comparing(Person::getHeight));
        System.out.println("Person list sorted by height: " + personList);

        QuickSorter.sort(personList, Comparator.comparing(Person::getWeight));
        System.out.println("Person list sorted by weight: " + personList);

        QuickSorter.sort(personList, Comparator.comparing(Person::getAge));
        System.out.println("Person list sorted by    age: " + personList);

        System.out.println("Max count of persons with same weight and different height: " +
                 getSameWeightDiffHeightCount(personList) + ", algorithm complexity is linear - O(n).");
    }

    static List<Person> initPersonList() {
        return new ArrayList<>(List.of(
                new Person(12, 13, 3),
                new Person(13, 14, 23),
                new Person(14, 16, 4),
                new Person(15, 13, 53),
                new Person(16, 11, 14),
                new Person(12, 13, 16),
                new Person(13, 17, 32),
                new Person(11, 14, 41),
                new Person(13, 10, 134),
                new Person(15, 1, 1),
                new Person(11, 11, 143)
        ));
    }

    static int getSameWeightDiffHeightCount(List<Person> list) {

        list.sort(Comparator.comparing(Person::getWeight)); // O(n)

        int maxCount = 0;
        int curCount = 0;
        int curWeight = list.get(0).getWeight();

        MyMap<Integer, Person> map = new MyHashMap<>();

        for (Person person : list) { // O(n)
            if (map.get(person.getHeight()) == null) { // O(1)

                map.put(person.getHeight(), person); // O(1)

                if (curWeight == person.getWeight()) {
                    curCount++;
                } else {
                    map.clear();
                    map.put(person.getHeight(), person);

                    maxCount = Math.max(maxCount, curCount);
                    curCount = 1;
                    curWeight = person.getWeight();
                }
            }
        }

        return maxCount;
    }
}