package com.example.microservices.todomicroservices;

import com.example.microservices.todomicroservices.entities.ToDo;
import com.example.microservices.todomicroservices.entities.User;
import com.example.microservices.todomicroservices.repository.ToDoDao;
import com.example.microservices.todomicroservices.repository.UserDao;
import com.example.microservices.todomicroservices.utilities.EncryptionUtils;
import com.example.microservices.todomicroservices.utilities.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

import static com.example.microservices.todomicroservices.utilities.Priority.HIGH;
import static com.example.microservices.todomicroservices.utilities.Priority.LOW;

@SpringBootApplication
public class ToDoMicroservicesApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToDoMicroservicesApplication.class.getName());

    @Autowired
    private UserDao userDao;

    @Autowired
    private ToDoDao toDoDao;

    @Autowired
    private EncryptionUtils encryptionUtils;

	public static void main(String[] args) {
		SpringApplication.run(ToDoMicroservicesApplication.class, args);
	}

    @Override
    public void run(String... args) {
        LOGGER.info("filling H2 database");

        userDao.save(createUser("some.email@provider.com", "Steve Bronner", "Passw0rd"));
        userDao.save(createUser("otheremail@someotherprovider.com", "Marco Lewis", "stonehenge"));
        userDao.save(createUser("wicked@dummy.eu", "Danzo Kim", "shifTed"));

        toDoDao.save(createToDo("some microservice related description", HIGH, "some.email@provider.com"));
        toDoDao.save(createToDo("spring boot comin'", LOW, "some.email@provider.com"));

        toDoDao.save(createToDo("Got some news", HIGH, "otheremail@someotherprovider.com"));
        toDoDao.save(createToDo("grocery", HIGH, "otheremail@someotherprovider.com"));

        toDoDao.save(createToDo("you have to see this!", HIGH, "dummy.eu"));
        toDoDao.save(createToDo("bunny stuff", LOW, "wicked@dummy.eu"));

        LOGGER.info("H2 database filling has finished");
    }

    private User createUser(String email, String name, String password) {
        return new User(email, name, encryptionUtils.encrypt(password));
    }

    private ToDo createToDo(String description, Priority priority, String user) {
        return new ToDo(null, description, new Date(), priority.getLevel(), user);
    }

}
