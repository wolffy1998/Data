package cn.mango.reflection;

/**
 * @Author mango
 * @Since 2020/3/12 20:04
 **/
public class Person extends Play {


    static {
        System.out.println("Persion is initialization");
    }

    static class LuWang extends Jump {
        static {
            System.out.println("hello");
        }
    }

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) {
        Object object = new Person.LuWang();
    }

}
