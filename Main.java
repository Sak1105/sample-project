class Student { 
    private String name; 
    private int roll; 
 
    public Student(String name, int roll) { 
        this.name = name; 
        this.roll = roll; 
    } 
 
    public void display() { 
        System.out.println("Name: " + name + ", Roll: " + roll); 
    } 
} 
 
public class Main { 
    public static void main(String[] args) { 
        Student s = new Student("Alice", 101); 
        s.display(); 
    } 
} 