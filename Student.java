import java.io.*;
import java.util.*;

public class Student implements Comparable<Student>{

    enum Major{CS, ECE, ME, CE, ChE, Phy, Math, Bio, Phm, Eco, Hum};
    public String iD;
    public String name;
    public Major major;
    public String email;
    public Student(String id, String name, Major maj,String email){
        this.iD = id;
        this.name = name;
        this.major = maj;
        this.email = email;
    }

    @Override public int compareTo(Student s)
    {
        // if the IDs are not equal
        if (this.iD.compareTo(s.iD) != 0) {
            return this.iD.compareTo(s.iD);
        }
        else {
            return 0; //both iDs are same
        }
    }
}
