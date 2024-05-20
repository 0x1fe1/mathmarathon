package page.pango.mathmarathon.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import page.pango.mathmarathon.entity.User;
import page.pango.mathmarathon.entity.UserDTO;
import page.pango.mathmarathon.entity.UserRankDTO;
import page.pango.mathmarathon.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    public User getUserByName(String userName) {
        return userRepository.getUserByName(userName);
    }

    public Optional<User> findUserByName(String username) {
        return userRepository.findUserByName(username);
    }

    public boolean addUser(UserDTO newUser) {
        if (!userRepository.existsByName(newUser.getName())) {
            User user = new User(
                null,
                newUser.getName(),
                newUser.getEmail(),
                encoder.encode(newUser.getPassword()),
                0L
            );
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean updateUserRank(String username, Long rank) {
        if (!userRepository.existsByName(username)) {
            return false;
        }
        User user = userRepository.getUserByName(username);
        user.setRank(user.getRank() + rank);
        userRepository.save(user);
        return true;
    }

    public List<UserRankDTO> getRankings() {
        List<User> users = userRepository.findAll();
        return users.stream()
            .map(user -> new UserRankDTO(user.getName(), user.getRank()))
            .collect(Collectors.toList());
    }

    public void addFakeUsers(int numberOfUsers) {
        Random random = new Random();
        List<String> firstNames = List.of(
            "John", "Jane", "Alice", "Robert",
            "Emily", "Michael", "Jessica", "William",
            "Sarah", "David"
        );

        List<String> lastNames = List.of(
            "Doe", "Smith", "Johnson", "Brown",
            "Davis", "Wilson", "Garcia", "Martinez",
            "Lee", "Harris"
        );

        List<User> fakeUsers = IntStream.range(0, numberOfUsers)
            .mapToObj(i -> new User(
                null,
                firstNames.get(random.nextInt(firstNames.size())) + " " +
                    lastNames.get(random.nextInt(lastNames.size())),
                "fakeuser" + i + "@example.com",
                encoder.encode("password"),
                (long) random.nextInt(201) // Rank between 0 and 200
            ))
            .collect(Collectors.toList());
        userRepository.saveAll(fakeUsers);
    }

    public void deleteFakeUsers() {
        List<User> fakeUsers = userRepository.findAll().stream()
            .filter(user -> user.getEmail().endsWith("@example.com"))
            .collect(Collectors.toList());
        userRepository.deleteAll(fakeUsers);
    }
}
