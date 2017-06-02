package com.example.exception;

import com.example.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public final class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static void checkUserForUniqueEmailOrLogin(User candidate, List<User> usersInDB) {
        if (!usersInDB.isEmpty()) {
            Optional<User> sameEmail = usersInDB
                    .stream()
                    .filter(u -> u.getEmail().equals(candidate.getEmail())
                            && !u.getId().equals(candidate.getId()))
                    .findFirst();
            Optional<User> sameLogin = usersInDB
                    .stream()
                    .filter(u -> u.getLogin().equals(candidate.getLogin())
                            && !u.getId().equals(candidate.getId()))
                    .findFirst();

            if (!sameEmail.isPresent() && !sameLogin.isPresent()) {
                return;
            }

            HashMap<String, String> matches = new HashMap<>();
            sameEmail.ifPresent(user -> matches.put("email", user.getEmail()));
            sameLogin.ifPresent(user -> matches.put("login", user.getLogin()));

            throw new UniqueViolationException(matches);
        }
    }

}
