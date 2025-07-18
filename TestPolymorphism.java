class Animal { 
    void sound() { 
        System.out.println("Animal makes a sound"); 
    } 
} 
 
class Dog extends Animal { 
    void sound() { 
        System.out.println("Dog barks"); 
    } 
} 
 
public class TestPolymorphism { 
    public static void main(String[] args) { 
        Animal a; 
        a = new Dog(); 
        a.sound();  // Runtime polymorphism 
    } 
}