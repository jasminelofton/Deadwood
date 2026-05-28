import java.util.ArrayList;
import java.util.Collections;

// Manages the draw pile of SceneCards for a game of Deadwood.
// At the start of each day the deck is shuffled and one card is dealt
// face-down to every ActingSet on the board.
public class Deck {
    private ArrayList<SceneCard> cards;
    private int currentIndex;  // index of the next card to be dealt

    public Deck() {
        this.cards = new ArrayList<>();
        this.currentIndex = 0;
    }

    // Constructs a deck pre-loaded with the given cards (defensive copy).
    public Deck(ArrayList<SceneCard> cards) {
        this.cards = new ArrayList<>(cards);
        this.currentIndex = 0;
    }

    // Replaces the full card list (used after XML parsing to load all cards).
    public void setCards(ArrayList<SceneCard> cards) {
        this.cards = cards;
    }

    // Randomises card order and resets the deal index to the beginning.
    public void shuffle() {
        Collections.shuffle(cards);
        currentIndex = 0;
    }

    // Removes and returns the top card, or null if the deck is empty.
    // Note: also advances currentIndex even though the card is removed;
    // isEmpty() and cardsRemaining() still work correctly because they
    // compare currentIndex against cards.size().
    public SceneCard dealCard() {
        if (isEmpty()) {
            return null;
        }
        SceneCard card = cards.get(currentIndex);
        cards.remove(card);
        currentIndex++;
        return card;
    }

    // Returns the top card without removing it, or null if the deck is empty.
    public SceneCard peekTop() {
        if (isEmpty()) {
            return null;
        }
        return cards.get(currentIndex);
    }

    public boolean isEmpty() {
        return currentIndex >= cards.size();
    }

    // Returns how many cards remain to be dealt.
    public int cardsRemaining() {
        return cards.size() - currentIndex;
    }

    // Resets the deal index so all cards can be dealt again without reshuffling.
    public void reset() {
        currentIndex = 0;
    }

    // Returns a defensive copy of the full card list (including already-dealt cards).
    public ArrayList<SceneCard> getAllCards() {
        return new ArrayList<>(cards);
    }
}
