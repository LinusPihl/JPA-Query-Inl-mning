import javax.persistence.*;
import java.util.List;

@Entity
class Address {
    // Address entity code...
}

@Entity
class Subject {
    // Subject entity code...
}

@Entity
class Tutor {
    // Tutor entity code...
}

@Entity
class Student {
    // Student entity code...
}

public class JPAQueriesAssignment {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourPersistenceUnit");
        EntityManager em = emf.createEntityManager();

        // Task-1: Navigating across relationships (Using member of)
        TypedQuery<Student> query1 = em.createQuery(
                "SELECT s FROM Student s WHERE s.tutor.subjects MEMBER OF s.tutor.subjects AND s.tutor.subjects.name = 'Science'",
                Student.class);
        List<Student> students = query1.getResultList();

        System.out.println("Students whose tutor can teach science:");
        for (Student student : students) {
            System.out.println(student.getName());
        }

        // Task-2: Report Query- Multiple fields (Use join)
        TypedQuery<Object[]> query2 = em.createQuery(
                "SELECT s.name, t.name FROM Student s JOIN s.tutor t",
                Object[].class);
        List<Object[]> results = query2.getResultList();

        System.out.println("\nStudent and Tutor Names:");
        for (Object[] result : results) {
            String studentName = (String) result[0];
            String tutorName = (String) result[1];
            System.out.println("Student: " + studentName + ", Tutor: " + tutorName);
        }

        // Task-3: Report Query- Aggregation
        TypedQuery<Double> query3 = em.createQuery(
                "SELECT AVG(s.semesterLength) FROM Subject s",
                Double.class);
        Double averageSemesterLength = query3.getSingleResult();

        System.out.println("\nAverage Semester Length: " + averageSemesterLength);

        // Task-4: Query With Aggregation
        TypedQuery<Integer> query4 = em.createQuery(
                "SELECT MAX(t.salary) FROM Tutor t",
                Integer.class);
        Integer maxSalary = query4.getSingleResult();

        System.out.println("\nMax Salary: " + maxSalary);

        // Task-5: NamedQuery
        TypedQuery<Tutor> query5 = em.createNamedQuery("Tutor.findBySalary", Tutor.class);
        query5.setParameter("salary", 10000);
        List<Tutor> tutors = query5.getResultList();

        System.out.println("\nTutors with Salary > 10000:");
        for (Tutor tutor : tutors) {
            System.out.println(tutor.getName());
        }

        em.close();
        emf.close();
    }
}
