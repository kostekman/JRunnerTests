import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class SerializableTutor{

    private static final String FILE_OBJECT_DATA = "object.data";

    /**
     * Should write the data of Person to file FILE_OBJECT_DATA,
     * using ObjectOutputStream
     * @param person
     */
    public void writeToFile(Person person) {
        try(FileOutputStream fos = new FileOutputStream(FILE_OBJECT_DATA);
            ObjectOutputStream oos = new ObjectOutputStream(fos)){

            oos.writeObject(person);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Reads and returns data from file FILE_OBJECT_DATA
     * @return
     */
    public Person readFromFile() {
        try(FileInputStream fis = new FileInputStream(FILE_OBJECT_DATA);
            ObjectInputStream ois = new ObjectInputStream(fis)){

            return (Person)ois.readObject();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testObjectSerialize() {
        Person person = new Person("John Johnes", new Date("2000/10/10"));
        writeToFile(person);
        System.out.println("Age:"+person.age);
        Person personFromFile = readFromFile();
        System.out.println("Name from file:"+personFromFile.name);
        System.out.println("Age from file:"+personFromFile.age);
        assertEquals(personFromFile.name, person.name);
        assertFalse("Name age was not marked as transient",
                person.age.equals(personFromFile.age));
    }
    @Test
    public void testPersonAddressSerialize() {
        Person person = new Person("John Johnes", new Date("2000/10/10"));
        Address address = new Address("New York", "Water street", 10);
        person.addAddress(address);

        writeToFile(person);

        System.out.println("Age:"+person.age);
        Person personFromFile = readFromFile();
        System.out.println("Name from file:"+personFromFile.name);
        System.out.println("Age from file:"+personFromFile.age);
        assertEquals(personFromFile.name, personFromFile.name);
        assertFalse(person.age.equals(personFromFile.age));

        Address addressFromFile = personFromFile.addressList.get(0);
        System.out.println("City from file:"+addressFromFile.city);
        System.out.println("Appartment from file:"+addressFromFile.appartment);
        assertEquals(addressFromFile.city, address.city);
        assertEquals(addressFromFile.appartment, address.appartment);
    }

    /**
     * Make the class static, implement Serializable
     * and mark the field age as transient
     */
    static class Person implements Serializable{
        public String name;
        public Date birthdate;
        public List<Address> addressList = new ArrayList<SerializableTutor.Address>();
        transient Integer age;

        public Person(){
        }

        public Person(String name, Date birthdate) {
            super();
            this.name = name;
            this.birthdate = birthdate;
            age = new Date().getYear() - birthdate.getYear();
        }

        public void addAddress(Address address) {
            addressList.add(address);
        }
    }

    static class Address implements Serializable{
        public String city;
        public String street;
        public Integer appartment;

        public Address(){}

        public Address(String city, String street, Integer appartment) {
            super();
            this.city = city;
            this.street = street;
            this.appartment = appartment;
        }
    }

}
