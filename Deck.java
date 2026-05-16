import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<SceneCard> cards;
    private int currentIndex; // Points to the next card to be dealt

    public Deck() {
        this.cards = new ArrayList<>();
        this.currentIndex = 0;
    }

    public Deck(ArrayList<SceneCard> cards) {
        this.cards = new ArrayList<>(cards);
        this.currentIndex = 0;
    }

    public void setCards(ArrayList<SceneCard> cards) {
        this.cards = cards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
        currentIndex = 0;
    }

    public SceneCard dealCard() {
        if (isEmpty()) {
            return null;
        }
        SceneCard card = cards.get(currentIndex);
        currentIndex++;
        return card;
    }

    public SceneCard peekTop() {
        if (isEmpty()) {
            return null;
        }
        return cards.get(currentIndex);
    }

    public boolean isEmpty() {
        return currentIndex >= cards.size();
    }

    public int cardsRemaining() {
        return cards.size() - currentIndex;
    }

    public void reset() {
        currentIndex = 0;
    }

    public ArrayList<SceneCard> getAllCards() {
        return new ArrayList<>(cards);
    }
}
