package ru.wildberries.utils.strings;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.RandomStringUtils;
import ru.wildberries.enums.system.Bom;
import ru.wildberries.enums.localization.Locale;
import ru.wildberries.enums.localization.TransliterationDirection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public final class StringUtils {

    public static final String EMPTY = "";
    private static final String RU_CHARS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЭЮЯ";
    private static final String BY_CHARS = "абвгддждзеёжзійклмнопрстуўфхцчшыьэюяАБВГДДжДзЕЁЖЗІЙКЛМНОПРСТУЎФХЦЧШЫЬЭЮЯ";
    private static final String AM_CHARS = "աբգդեզէըթժիլխծկհձղճմյնշոչպջռսվտրցւփքեւօֆԱԲԳԴԵԶԷԸԹԺԻԼԽԾԿՀՁՂՃՄՅՆՇՈՉՊՋՌՍՎՏՐՑՒՓՔԵվևՕՖ";
    private static final String KG_CHARS = "абвгдеёжзийклмнңоөпрстуүфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНҢОӨПРСТУҮФХЦЧШЩЪЫЬЭЮЯ";
    private static final String KZ_CHARS = "аәбвгғдеёжзийкқлмнңоөпрстуұүфхһцчшщъыіьэюяАӘБВГҒДЕЁЖЗИЙКҚЛМНҢОӨПРСТУҰҮФХҺЦЧШЩЪЫІЬЭЮЯ";

    public static String[] splitByLast(String string, char delimiter) {
        String regex = String.format("\\%c(?=[^\\%c]*$)", delimiter, delimiter);
        return string.split(regex);
    }

    public static String randomAlphabetic(final int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

    public static String randomAlphabetic(final int count, final Locale locale) {
        String chars;
        switch (locale) {
            case RU:
                chars = RU_CHARS;
                break;
            case AM:
                chars = AM_CHARS;
                break;
            case BY:
                chars = BY_CHARS;
                break;
            case KG:
                chars = KG_CHARS;
                break;
            case KZ:
                chars = KZ_CHARS;
                break;
            default:
                throw new NotImplementedException("Chars for locale '" + locale.name() + "' not defined");
        }
        return RandomStringUtils.random(count, chars);
    }

    public static String getAlphabetic(String string) {
        return string.replaceAll("[^\\p{L}]+", "");
    }

    public static String getAlphanumeric(String string) {
        return string.replaceAll("[^\\p{L}\\d]+", "");
    }

    public static String getAlphanumericSpace(String string) {
        return string.replaceAll("[^\\p{L}\\d\\s]+", "");
    }

    public static boolean isAlphabeticEquals(String stringOne, String stringTwo) {
        return getAlphabetic(stringOne).equals(getAlphabetic(stringTwo));
    }

    public static boolean isAlphabeticEqualsIgnoreCase(String stringOne, String stringTwo) {
        return getAlphabetic(stringOne).equalsIgnoreCase(getAlphabetic(stringTwo));
    }

    public static boolean isAlphanumericEquals(String stringOne, String stringTwo) {
        return getAlphanumeric(stringOne).equals(getAlphanumeric(stringTwo));
    }

    public static boolean isAlphanumericEqualsIgnoreCase(String stringOne, String stringTwo) {
        return getAlphanumeric(stringOne).equalsIgnoreCase(getAlphanumeric(stringTwo));
    }

    public static boolean isAlphanumericSpaceEquals(String stringOne, String stringTwo) {
        return getAlphanumericSpace(stringOne).equals(getAlphanumericSpace(stringTwo));
    }

    public static boolean isAlphanumericSpaceEqualsIgnoreCase(String stringOne, String stringTwo) {
        return getAlphanumericSpace(stringOne).equalsIgnoreCase(getAlphanumericSpace(stringTwo));
    }

    public static boolean isNumeric(CharSequence charSequence) {
        return org.apache.commons.lang3.StringUtils.isNumeric(charSequence);
    }

    /**
     * Clear BOM from text
     *
     * @return Text without BOM
     */
    public static String clearBom(final String text) {
        String withoutBom = text;
        for (Bom bom : Bom.values()) {
            withoutBom = text.replaceAll("^" + bom.getHeader(), EMPTY);
        }
        return withoutBom;
    }

    public static String transliterate(String string) {
        return Transliterator.transliterate(string);
    }

    /**
     * Transliteration of the cyrillic string into latin and the latin into cyrillic
     *
     * @param string    text for transliteration
     * @param direction CYRILLIC_TO_LATIN or LATIN_TO_CYRILLIC
     * @return transliterated String
     */
    public static String transliterate(String string, TransliterationDirection direction) {
        return Transliterator.transliterate(string, direction);
    }

    /**
     * Fuzzy compare two strings
     * The same words are removed from the Strings.
     * After which the Levenshtein distance is calculated over the remaining words.
     * Comparison is made of each word with each with an acceptable distance = 2.
     * If the distance is less than or equal to the allowable, such words are also removed from the Strings.
     * After which the Levenshtein distance is calculated over the remaining Strings.
     *
     * @param stringOne         first text
     * @param stringTwo         second text
     * @param allowableDistance the Levenshtein distance (int) at which Strings will still be considered the same (inclusive)
     * @return - true if two strings equals fuzzy
     */
    public static boolean isStringsEqualsFuzzy(String stringOne, String stringTwo, int allowableDistance) {
        int allowableOneWordDistance = 2;
        List<String> wordsOne = getWordsFromString(stringOne.toLowerCase());
        List<String> wordsTwo = getWordsFromString(stringTwo.toLowerCase());

        List<String> uniqueWordsOne = wordsOne.stream()
                .filter(word -> !wordsTwo.contains(word))
                .collect(Collectors.toList());

        List<String> uniqueWordsTwo = wordsTwo.stream()
                .filter(word -> !wordsOne.contains(word))
                .collect(Collectors.toList());

        log.debug("String 1: '{}'; Words: [{}]; Unique words 1: '{}'", stringOne, wordsOne, uniqueWordsOne);
        log.debug("String 2: '{}'; Words: [{}]; Unique words 2: '{}'", stringTwo, wordsTwo, uniqueWordsOne);

        new HashSet<>(uniqueWordsOne).forEach(wordOne ->
                new HashSet<>(uniqueWordsTwo).forEach(wordTwo -> {
                            int distance = getLevenshteinDistance(wordOne, wordTwo);
                            if (distance <= allowableOneWordDistance) {
                                uniqueWordsOne.remove(wordOne);
                                uniqueWordsOne.remove(wordTwo);
                                uniqueWordsTwo.remove(wordOne);
                                uniqueWordsTwo.remove(wordTwo);
                            }
                        }
                ));

        String uniqueStringOne = String.join(" ", uniqueWordsOne);
        String uniqueStringTwo = String.join(" ", uniqueWordsTwo);
        int distance = getLevenshteinDistance(uniqueStringOne, uniqueStringTwo);

        log.debug("After compare each to each words in both collections");
        log.debug("String 1: '{}'; Words: [{}]; UniqueString 1: '{}'", stringOne, uniqueWordsOne, uniqueStringOne);
        log.debug("String 2: '{}'; Words: [{}]; UniqueString 2: '{}'", stringTwo, uniqueWordsTwo, uniqueStringTwo);
        log.debug("Levenshtein Distance: {}", distance);

        return distance <= allowableDistance;
    }

    /**
     * Calculating the Levenshtein distance for two Strings
     *
     * @param stringOne text
     * @param stringTwo text to calculate distance
     * @return - distance as int
     */
    private static int getLevenshteinDistance(String stringOne, String stringTwo) {
        int stringOneLength = stringOne.length();
        int stringTwoLength = stringTwo.length();

        int[][] deltaMatrix = new int[stringOneLength + 1][stringTwoLength + 1];

        IntStream.range(1, stringOneLength + 1).forEach(i -> deltaMatrix[i][0] = i);
        IntStream.range(1, stringTwoLength + 1).forEach(j -> deltaMatrix[0][j] = j);

        IntStream.range(1, stringTwoLength + 1).forEach(j ->
                IntStream.range(1, stringOneLength + 1).forEach(i ->
                        deltaMatrix[i][j] = stringOne.charAt(i - 1) == stringTwo.charAt(j - 1)
                                ? deltaMatrix[i][j] = deltaMatrix[i - 1][j - 1]
                                : Math.min(deltaMatrix[i - 1][j] + 1, Math.min(deltaMatrix[i][j - 1] + 1, deltaMatrix[i - 1][j - 1] + 1)
                        )));

        return deltaMatrix[stringOneLength][stringTwoLength];
    }

    /**
     * Converts text to collection of words
     *
     * @param string text
     * @return - List<String> words
     */
    private static List<String> getWordsFromString(String string) {
        return Arrays.asList(getAlphanumericSpace(string)
                .replaceAll("\\s++", " ")
                .split("\\s"));
    }

}
