package org.openmrs.module.coreapps.triage;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class PediatricTriageService {

    public String determineTriageLevel(Triage triage) {
        if (triage == null || triage.getVitals() == null || triage.getPatient() == null) {
            return "GREEN"; // Pas de données → niveau de sécurité
        }

        Vitals v = triage.getVitals();
        Date birth = triage.getPatient().getPerson().getBirthdate();
        int ageMonths = calculateAgeInMonths(birth);

        String airway = checkAirway(v);
        String breathing = checkBreathing(v, ageMonths);
        String circulation = checkCirculation(v, ageMonths);
        String pain = classifyPain(v);
        String temperature = classifyTemperature(v);

        return getHighestPriority(airway, breathing, circulation, pain, temperature);
    }


    // ------------------------- AIRWAY (A) -------------------------
    private String checkAirway(Vitals v) {
        if (v == null) return "GREEN";

        // Glasgow ≤ 8
        if (v.getGlasgow() != null && v.getGlasgow() <= 8) return "RED";

        // AVPU
        String avpu = v.getAvpu();
        if (avpu == null) return "GREEN";

        switch (avpu) {
            case "U": return "RED";       // Ne réagit pas
            case "P": return "ORANGE";    // Réagit aux stimuli douloureux
            case "V": return "ORANGE";    // Réagit aux stimuli verbaux
            case "I": return "YELLOW";    // Irritable
            case "A": return "GREEN";     // Alerte
            case "C": return "YELLOW";    // Consolable
            case "F": return "YELLOW";    // Confus
            case "G": return "ORANGE";    // Agité / grognement
            default: return "GREEN";
        }
    }

    // ------------------------- AVPU -------------------------
    private String checkConsciousness(Vitals v) {

        String avpu = v.getAvpu();
        if (avpu == null) return "GREEN";

        if (avpu.equals(TriageConstants.AVPU_UNRESPONSIVE_CONCEPT)
                || avpu.equals(TriageConstants.AVPU_SEIZURE_CONCEPT)) {
            return "RED";
        }

        if (avpu.equals(TriageConstants.AVPU_PAIN_CONCEPT)
                || avpu.equals(TriageConstants.AVPU_VOICE_CONCEPT)
                || avpu.equals(TriageConstants.AVPU_LETHARGIC_CONCEPT)
                || avpu.equals(TriageConstants.AVPU_CONFUSED_CONCEPT)
                || avpu.equals(TriageConstants.AVPU_AGITATED_CONCEPT)) {
            return "ORANGE";
        }

        if (avpu.equals(TriageConstants.AVPU_IRRITABLE_CONCEPT)
                || avpu.equals(TriageConstants.AVPU_CONSOLABLE_CONCEPT)) {
            return "YELLOW";
        }

        if (avpu.equals(TriageConstants.AVPU_ALERT_CONCEPT)) {
            return "GREEN";
        }

        return "GREEN";
    }

    // ------------------------- BREATHING (B) -------------------------
    private String checkBreathing(Vitals v, int ageMonths) {

        if (v == null) {
            return "GREEN";
        }

        // -------- SpO2 --------
        Integer spo2 = v.getSpO2();
        if (spo2 != null && spo2 < 90) {
            return "RED";
        }

        // -------- Apnée / détresse --------
        if (Boolean.TRUE.equals(v.isApnea()) ||
                Boolean.TRUE.equals(v.isRespiratoryDistress())) {
            return "RED";
        }

        // -------- Stridor / wheezing / grognement --------
        if (Boolean.TRUE.equals(v.isStridor()) ||
                Boolean.TRUE.equals(v.isWheezing()) ||
                Boolean.TRUE.equals(v.isGrognement())) {
            return "ORANGE";
        }

        // -------- Fréquence respiratoire --------
        Integer fr = v.getRespiratoryRate();

        if (fr != null) {

            int[] rrLimits = getRespiratoryRateLimits(ageMonths);

            if (rrLimits != null && rrLimits.length >= 4) {

                int redLow = rrLimits[0];
                int redHigh = rrLimits[1];
                int orangeLow = rrLimits[2];
                int orangeHigh = rrLimits[3];

                if (fr < redLow || fr > redHigh) {
                    return "RED";
                }

                if (fr < orangeLow || fr > orangeHigh) {
                    return "ORANGE";
                }
            }
        }

        return "GREEN";
    }

    // ------------------------- CIRCULATION (C) -------------------------
    private String checkCirculation(Vitals v, int ageMonths) {

        if (v == null) {
            return "GREEN";
        }

        // -------- Cyanose / Purpura --------
        if (Boolean.TRUE.equals(v.isCyanosis()) ||
                Boolean.TRUE.equals(v.isPurpura())) {
            return "RED";
        }

        // -------- TRC --------
        Double trc = v.getTrc();
        if (trc != null && trc > 2) {
            return "RED";
        }

        // -------- Pression systolique --------
        Integer systolic = v.getSystolic();
        if (systolic != null) {

            int[] sysLimits = getSystolicLimits(ageMonths);

            if (sysLimits != null && sysLimits.length >= 2) {

                int redLimit = sysLimits[0];
                int orangeLimit = sysLimits[1];

                if (systolic < redLimit) {
                    return "RED";
                }

                if (systolic < orangeLimit) {
                    return "ORANGE";
                }
            }
        }

        // -------- Signes cutanés --------
        if (Boolean.TRUE.equals(v.isPale()) ||
                Boolean.TRUE.equals(v.isMottledSkin())) {
            return "ORANGE";
        }

        // -------- Pouls --------
        Integer pulse = v.getPulse();

        if (pulse != null) {

            int[] pulseLimits = getPulseLimits(ageMonths);

            if (pulseLimits != null && pulseLimits.length >= 4) {

                int redLow = pulseLimits[0];
                int redHigh = pulseLimits[1];
                int orangeLow = pulseLimits[2];
                int orangeHigh = pulseLimits[3];

                if (pulse < redLow || pulse > redHigh) {
                    return "RED";
                }

                if (pulse < orangeLow || pulse > orangeHigh) {
                    return "ORANGE";
                }
            }
        }

        return "GREEN";
    }

    // ------------------------- PAIN (P) -------------------------
    private String classifyPain(Vitals v) {
        if (v == null || v.getPainScore() == null) return "GREEN";

        int pain = v.getPainScore();
        if (pain >= 8) return "ORANGE";
        if (pain >= 4) return "YELLOW";
        return "GREEN";
    }

    // ------------------------- TEMPERATURE (T) -------------------------
    private String classifyTemperature(Vitals v) {
        if (v == null || v.getTemperature() == null) return "GREEN";

        double temp = v.getTemperature();
        if (temp >= 40 || temp < 35) return "ORANGE"; // Hyper ou hypothermie critique
        if (temp >= 38.5) return "YELLOW";           // Fièvre modérée
        return "GREEN";
    }

    // ------------------------- PRIORITY -------------------------
    private String getHighestPriority(String... levels) {
        int highest = Integer.MAX_VALUE;
        String result = "GREEN";

        for (String level : levels) {
            int score;
            switch (level) {
                case "RED": score = 1; break;
                case "ORANGE": score = 2; break;
                case "YELLOW": score = 3; break;
                case "GREEN": score = 4; break;
                default: score = 5; break;
            }
            if (score < highest) {
                highest = score;
                result = level;
            }
        }

        return result;
    }

    // ------------------------- UTILS -------------------------
    public int calculateAgeInMonths(Date birthDate) {
        if (birthDate == null) return 0;

        LocalDate birthLocalDate = birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate now = LocalDate.now();
        int months = (now.getYear() - birthLocalDate.getYear()) * 12
                + now.getMonthValue() - birthLocalDate.getMonthValue();
        return months;
    }

    private int[] getRespiratoryRateLimits(int ageMonths) {
        if (ageMonths <= 1) return new int[]{30, 60, 40, 50};      // Nouveau-né
        if (ageMonths <= 12) return new int[]{20, 50, 25, 45};     // Nourrisson
        if (ageMonths <= 60) return new int[]{15, 40, 20, 35};     // Enfant
        return new int[]{12, 30, 15, 25};                          // Adolescent
    }

    private int[] getPulseLimits(int ageMonths) {
        if (ageMonths <= 1) return new int[]{100, 180, 120, 160};
        if (ageMonths <= 12) return new int[]{90, 170, 100, 150};
        if (ageMonths <= 60) return new int[]{70, 140, 80, 130};
        return new int[]{60, 120, 70, 110};
    }

    private int[] getSystolicLimits(int ageMonths) {
        if (ageMonths <= 1) return new int[]{70, 75}; // [RED, ORANGE]
        if (ageMonths <= 12) return new int[]{70, 80};
        if (ageMonths <= 60) return new int[]{75, 85};
        return new int[]{90, 100};
    }
}