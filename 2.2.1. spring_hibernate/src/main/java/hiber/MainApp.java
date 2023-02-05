package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      try {
         User user1 = new User("Vladimir", "Putin", "thefather@kremlin.ru");
         User user2 = new User("Barak", "Obama", "therealblackpanther@gmail.com");
         User user3 = new User("Emanuel", "Macron", "baguette@fr.fr");
         Car car1 = new Car("LADA", 2110);
         Car car2= new Car("Chrysler", 300);
         Car car3 = new Car("Peugeot", 406);
         user1.setCar(car1);
         user2.setCar(car2);
         user3.setCar(car3);
         userService.add(user1);
         userService.add(user2);
         userService.add(user3);
         List<User> users = userService.listUsers();
         for (User user : users) {
            System.out.println(user.getLastName() + "--" + user.getCar().getModel()
                    + " " + user.getCar().getSeries());
         }

         String model1 = "LADA";
         String model2 = "Chrysler";
         String model3 = "Peugeot";
         int series1 = 2110;
         int series2 = 300;
         int series3 = 406;

         User user4 = userService.getUser(model1, series1);
         User user5 = userService.getUser(model2, series2);
         User user6 = userService.getUser(model3, series3);

         System.out.println(user4.getLastName());
         System.out.println(user5.getLastName());
         System.out.println(user6.getLastName());

      } catch (Exception e) {
         e.printStackTrace();
      }

      context.close();
   }
}
