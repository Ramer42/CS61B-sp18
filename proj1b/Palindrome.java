public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> dq = new ArrayDeque<Character>();
        int len = word.length();
        for (int i = 0; i < len; i++){
            dq.addLast(word.charAt(i));
        }
        return dq;
    }

    public boolean isPalindrome(String word){
        Deque<Character> dq = wordToDeque(word);
        if (dq.size() < 2) {
            return true;
        }
        int half_size = dq.size() / 2;
        for (int i = 0; i < half_size; i++) {
            if (dq.get(i) != dq.get(dq.size() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> dq = wordToDeque(word);
        if (dq.size() < 2) {
            return true;
        }
        int half_size = dq.size() / 2;
        for (int i = 0; i < half_size; i++) {
            if (!cc.equalChars(dq.get(i), dq.get(dq.size() - i - 1))) {
                return false;
            }
        }
        return true;
    }
}
