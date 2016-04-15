import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class SerializableTutor{

    private static final String FILE_OBJECT_DATA = "files/object.data";

    /**
     * Should write the data of Person to file FILE_OBJECT_DATA,
     * using ObjectOutputStream
     * @param person
     */
    public void writeToFile(Person person) {
    }

    /**
     * Reads and returns data from file FILE_OBJECT_DATA
     * @return
     */
    public Person readFromFile() {
        Person person = null;
        return person;
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

    /**
     * Uncomment @Test and edit class Address,
     * so that it allows the address serialization
     */
    //@Test
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
    class Person {
        public String name;
        public Date birthdate;
        public List<Address> addressList = new ArrayList<SerializableTutor.Address>();
        Integer age;

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

    class Address {
        public String city;
        public String street;
        public Integer appartment;
        public Address(String city, String street, Integer appartment) {
            super();
            this.city = city;
            this.street = street;
            this.appartment = appartment;
        }
    }

}
