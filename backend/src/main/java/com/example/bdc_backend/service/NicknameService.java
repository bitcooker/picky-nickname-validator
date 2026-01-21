package com.example.bdc_backend.service;

import com.example.bdc_backend.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NicknameService {

    private final Set<String> takenNicknames = new HashSet<>();

    public Optional<NegotiationResponse> validate(String nickname) {
        List<ReasonCode> reasons = new ArrayList<>();

        if (!nickname.toLowerCase().contains("o")) {
            reasons.add(ReasonCode.MISSING_O);
        }

        if (nickname.toLowerCase().contains("cat")) {
            reasons.add(ReasonCode.CONTAINS_CAT);
        }

        if (nickname.matches(".*\\d$") && !nickname.matches(".*\\d{2,}$")) {
            reasons.add(ReasonCode.ENDS_WITH_SINGLE_DIGIT);
        }

        if (takenNicknames.contains(nickname)) {
            reasons.add(ReasonCode.ALREADY_TAKEN);
        }

        if (reasons.isEmpty()) {
            takenNicknames.add(nickname);
            return Optional.empty();
        }

        return Optional.of(
                new NegotiationResponse(
                        reasons,
                        buildExplanation(reasons),
                        generateSuggestions(nickname)
                )
        );
    }

    private String buildExplanation(List<ReasonCode> reasons) {
        return "This nickname doesn’t quite meet our rules. Let’s tweak it!";
    }

    private List<String> generateSuggestions(String nickname) {
        List<String> suggestions = new ArrayList<>();

        String base = nickname.toLowerCase();

        // Remove "cat"
        base = base.replaceAll("cat", "");

        // Ensure at least one 'o'
        if (!base.contains("o")) {
            base = base + "o";
        }

        // Fix single-digit ending
        if (base.matches(".*\\d$") && !base.matches(".*\\d{2,}$")) {
            base = base + "9";
        }

        if (base.isBlank()) {
            base = "coolname";
        }

        // Generate variants and filter taken ones
        List<String> candidates = List.of(
                base,
                base + "99",
                "the" + base,
                base + "x"
        );

        for (String c : candidates) {
            if (!takenNicknames.contains(c)) {
                suggestions.add(c);
            }
        }

        return suggestions.stream()
                .distinct()
                .limit(4)
                .toList();
    }
}
