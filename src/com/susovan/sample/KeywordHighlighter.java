package com.susovan.sample;

public class KeywordHighlighter {
    public String highlightKeyword(String text, String keyword) {
        String regex = "(?i)" + keyword; // (?i) makes the regex case-insensitive
        String replacement = "<b>" + keyword + "</b>";
        return text.replaceAll(regex, replacement);
    }

    public static void main(String[] args) {
        KeywordHighlighter keywordHighlighter = new KeywordHighlighter();
        String text = "This is a long string that contains the keyword multiple times.";
        String keyword = "KEY";
        String highlightedText = keywordHighlighter.highlightKeyword(text, keyword);
        System.out.println(highlightedText);
    }
}