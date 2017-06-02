package com.example.exception;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class UniqueViolationException extends RuntimeException {
    private HashMap<String, String> matches;

    public UniqueViolationException(String message) {
        super(message);
    }

    public UniqueViolationException(HashMap<String, String> matches) {
        this.matches = new HashMap<>(matches);
    }

    @Override
    public String getMessage() {
        if (matches != null && !matches.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> match : matches.entrySet()) {
                sb.append(match.getKey()).append("=").append("\"").append(match.getValue()).append("\" ");
            }
            sb.append(" already taken");
            return sb.toString();
        }
        return super.getMessage();
    }
}
