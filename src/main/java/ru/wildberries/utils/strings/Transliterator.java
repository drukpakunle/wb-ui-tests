package ru.wildberries.utils.strings;

import ru.wildberries.enums.localization.TransliterationDirection;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

public final class Transliterator {

    private static final Map<String, String> letters = new HashMap<>();

    static {
        letters.put("a", "а");
        letters.put("b", "б");
        letters.put("v", "в");
        letters.put("g", "г");
        letters.put("d", "д");
        letters.put("e", "е");
        letters.put("yo", "ё");
        letters.put("zh", "ж");
        letters.put("z", "з");
        letters.put("i", "и");
        letters.put("j", "й");
        letters.put("k", "к");
        letters.put("l", "л");
        letters.put("m", "м");
        letters.put("n", "н");
        letters.put("o", "о");
        letters.put("p", "п");
        letters.put("r", "р");
        letters.put("s", "с");
        letters.put("t", "т");
        letters.put("u", "у");
        letters.put("f", "ф");
        letters.put("h", "х");
        letters.put("ts", "ц");
        letters.put("ch", "ч");
        letters.put("sh", "ш");
        letters.put("`", "ъ");
        letters.put("y", "иы");
        letters.put("'", "ь");
        letters.put("yu", "ю");
        letters.put("ya", "я");
        letters.put("x", "кс");
        letters.put("w", "в");
        letters.put("q", "ку");
        letters.put("iy", "ий");
    }

    public static String transliterate(String string) {
        TransliterationDirection direction = detectTranslitDirection(string);
        return transliterate(string, direction);
    }

    public static String transliterate(String string, TransliterationDirection direction) {
        String text = string.toLowerCase();
        Map<String, String> dictionary = getDictionary(direction);
        StringBuilder sb = new StringBuilder(text.length());

        ListIterator<String> iterator = text.chars()
                .mapToObj(key -> String.valueOf((char) key))
                .collect(Collectors.toList()).listIterator();

        while (iterator.hasNext()) {
            String oneCharKey = iterator.next();
            String twoCharsKey = iterator.hasNext() ? oneCharKey + iterator.next() : null;

            sb.append(dictionary.getOrDefault(twoCharsKey, dictionary.getOrDefault(oneCharKey, oneCharKey)));

            if (twoCharsKey != null && !dictionary.containsKey(twoCharsKey)) {
                iterator.previous();
            }
        }

        return sb.toString();
    }

    private static Map<String, String> getDictionary(TransliterationDirection direction) {
        Map<String, String> dictionary = new HashMap<>(letters);

        if (direction.equals(TransliterationDirection.CYRILLIC_TO_LATIN)) {
            dictionary.remove("w");
            dictionary = dictionary.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        }

        return dictionary;
    }

    private static TransliterationDirection detectTranslitDirection(String string) {
        String oneCharKey = string.substring(0, 1);
        String twoCharsKey = string.substring(0, 2);

        return letters.containsKey(oneCharKey) || letters.containsKey(twoCharsKey)
                ? TransliterationDirection.LATIN_TO_CYRILLIC
                : TransliterationDirection.CYRILLIC_TO_LATIN;
    }
}
