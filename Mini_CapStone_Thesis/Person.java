public abstract class Person {
    protected String id;
    protected String password;
    protected String name;
    protected int age;
    protected String contactNumber;

    public Person(String id, String password, String name, int age, String contactNumber) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
    }

    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getContactNumber() { return contactNumber; }

    public abstract void displayInfo();
}